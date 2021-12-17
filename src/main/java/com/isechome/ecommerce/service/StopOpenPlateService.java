/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-11-05 14:31:07
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\StopOpenPlateService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;

import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.ReturnObj;
import com.isechome.ecommerce.entity.ScOrderListBase;
import com.isechome.ecommerce.entity.ScSysStatus;
import com.isechome.ecommerce.entity.ScSysStatusLog;
import com.isechome.ecommerce.entity.ScWorkTime;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListBaseMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScShoppingCartMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSysStatusLogMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSysStatusMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScWorkTimeMapper;
import com.isechome.ecommerce.mapper.ecommerce.TaskConfigEntityMapper;
import com.isechome.ecommerce.security.SecuritySysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.date.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class StopOpenPlateService {
    @Autowired
    ScWorkTimeMapper scWorkTimeMapper;
    @Autowired
    ScSysStatusMapper scSysStatusMapper;
    @Autowired
    ScSysStatusLogMapper scSysStatusLogMapper;
    @Autowired
    TaskConfigEntityMapper taskConfigEntityMapper;
    @Autowired
    ScShoppingCartMapper scShoppingCartMapper;
    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper; 
    @Autowired
    ScOrderListService scOrderListService;
    
    // 停开盘接口 status=1:开盘 status=2:停盘   
    public ReturnObj modifystatus(Byte status, Integer pmid, Integer userid, String real_name, Byte istd) {
        ReturnObj returnObj = new ReturnObj();
        returnObj.setSuccess("1");
        returnObj.setMessage("操作成功！");
        getTodayScSysStatus(pmid); //若sc_sys_status今天未录入状态，则插入停盘
        Date dateNow = new Date();

        if (status == 1) {  //开盘不在交易时间外
            ScWorkTime scWorkTime = new ScWorkTime();
            scWorkTime = scWorkTimeMapper.selectByPmid(pmid);
            Date start_time = scWorkTime.getStartTime();
            Date end_time = scWorkTime.getEndTime();
            String stand = "H:m:s";
            Common common=new Common();
            Date dateNow2 = common.strToDate(common.getHour(dateNow)+":"+common.getMinute(dateNow)+":0", stand);
            Date start_time2 = common.strToDate(common.getHour(start_time)+":"+common.getMinute(start_time)+":0", stand);
            Date end_time2 = common.strToDate(common.getHour(end_time)+":"+common.getMinute(end_time)+":0", stand);
            if(dateNow2.before(start_time2) || end_time2.before(dateNow2)) {
                returnObj.setSuccess("0");
                returnObj.setMessage("不在交易时间内，开盘失败！");
            }
        }
        if (status == 2 && istd == 1) {
            scOrderListService.checkOrderTop(pmid);  // 收盘时调用生成超量资源生成提单接口  (手动停盘不用)
        }

        ScSysStatus scSysStatusOld = new ScSysStatus();
        scSysStatusOld = scSysStatusMapper.selTodyStatus(pmid, dateNow);
        if (returnObj.getSuccess() == "1" && scSysStatusOld.getTrantype() != status) {
            if (status == 2) {
                cleanOrder(pmid);
            }
            // 开盘停盘保存
            ScSysStatus scSysStatus = new ScSysStatus();
            scSysStatus.setPmid(pmid);
            scSysStatus.setDate(dateNow);
            scSysStatus.setTrantype(status);
            scSysStatus.setExcessiveStop(scSysStatusOld.getExcessiveStop());
            scSysStatus.setUpdateTime(dateNow);
            scSysStatus.setUpdateUserId(userid);
            scSysStatusMapper.updateByPmid(scSysStatus);
            // 日志保存
            scSysStatusLogSave(pmid, userid, real_name);
        }
 
        return returnObj;
    }

    /**
     * @Author: lina
     * @Description: 插入系统交易设置
     * @Date: 2021-09-18 10:11:46
     * @param {Integer} pmid
     * @param {Integer} userid
     * @param {String} real_name
     */    
    public void scSysStatusLogSave(Integer pmid, Integer userid, String real_name) {
        Date dateNow = new Date();
        ScSysStatus scSysStatus = new ScSysStatus();
        scSysStatus = scSysStatusMapper.selTodyStatus(pmid, dateNow);

        ScSysStatusLog scSysStatusLog = new ScSysStatusLog();
        scSysStatusLog.setPmid(pmid);
        scSysStatusLog.setDate(dateNow);
        scSysStatusLog.setType(scSysStatus.getTrantype());
        scSysStatusLog.setExcessiveStop(scSysStatus.getExcessiveStop());
        scSysStatusLog.setCreatTime(dateNow);
        scSysStatusLog.setCreateUserId(userid);
        scSysStatusLog.setCreateUserName(real_name);
        scSysStatusLogMapper.insert(scSysStatusLog);
    }

    // 清空购物车与资源 sc_shopping_cart 删除今天  sc_order_list_base 删除订单 status=2、3变为0
    public void cleanOrder (Integer pmid) {
        Date date = new Date();
        //一天的开始
        Date beginOfDay = DateUtil.beginOfDay(date);
        //一天的结束
        Date endOfDay = DateUtil.endOfDay(date);
        // 清空购物车
        scShoppingCartMapper.delByDatePmid(pmid, beginOfDay, endOfDay);
        // 删除订单
        scOrderListBaseMapper.updatecleanOrder(pmid, beginOfDay, endOfDay);
    }

    public ScSysStatus getTodayScSysStatus (Integer pmid) {
        Date dateNow = new Date();
        ScSysStatus scSysStatus = new ScSysStatus();
        scSysStatus = scSysStatusMapper.selTodyStatus(pmid, dateNow);
        if (scSysStatus == null) {
            // 获取昨天超量停盘的数量
            Date yesterday = DateUtil.offsetDay(dateNow, -1);
            ScSysStatus yseScSysStatus = new ScSysStatus();
            yseScSysStatus = scSysStatusMapper.selTodyStatus(pmid, yesterday);
            Double excessive_stop = 0.0;
            if (yseScSysStatus != null) {
                excessive_stop = yseScSysStatus.getExcessiveStop();
            }
            
            ScSysStatus scSysStatusInsert = new ScSysStatus();
            scSysStatusInsert.setPmid(pmid);
            scSysStatusInsert.setDate(dateNow);
            Byte trantype = 2;
            scSysStatusInsert.setTrantype(trantype);
            scSysStatusInsert.setExcessiveStop(excessive_stop);
            scSysStatusInsert.setCreatTime(dateNow);
            scSysStatusInsert.setCreateUserId(0);
            scSysStatusMapper.insert(scSysStatusInsert);
            scSysStatus = scSysStatusMapper.selTodyStatus(pmid, dateNow);
        }
        return scSysStatus;
    }
    
    /**
     * 获取开停盘状态 
     * @return Boolean
     */
    public Boolean getIsOpen() {
        Integer pmid = SecurityUserUtil.getPmid();
        ScSysStatus scSysStatus = new ScSysStatus();
        scSysStatus = getTodayScSysStatus(pmid);
        Byte trantype = scSysStatus.getTrantype();
        Boolean isopen = true;
        if (trantype == 1) {
            isopen = true;
        } else if (trantype == 2) {
            isopen = false;
        }
        return isopen;
    }

    /**
     * 开停盘定时程序
     * @return 
     */
    public void OpenStopPlateJob(String isOpen, Integer pmid) {
        Byte status = 2;
        if (isOpen == "1") {
            status = 1;
        } else {
            status = 2;
        }
 
        Integer userid = 0;
        String real_name = "系统";
        Byte istd = 1;
        modifystatus(status, pmid, userid, real_name, istd);
        
    }

    /**
     * @Author: lina
     * @Description: 超量停盘接口
     * @Date: 2021-09-18 11:33:54
     * @param {Integer} pmid
     */    
    public void overSaleStop(Integer pmid) {
        ScSysStatus scSysStatus = new ScSysStatus();
        scSysStatus = getTodayScSysStatus(pmid);
        Double excessive_stop = scSysStatus.getExcessiveStop();

        // 获取今日现货销量
        Date dateNow = new Date();
        //一天的开始
        Date beginOfDay = DateUtil.beginOfDay(dateNow);
        //一天的结束
        Date endOfDay = DateUtil.endOfDay(dateNow);
        ScOrderListBase scOrderListBase = new ScOrderListBase();
        scOrderListBase = scOrderListBaseMapper.getSumNum(pmid, beginOfDay, endOfDay);
        if (scOrderListBase != null) {
            Double saleNum = scOrderListBase.getNum();
            if (excessive_stop > 0) {
                if (saleNum.compareTo(excessive_stop) > 0 || saleNum.compareTo(excessive_stop) == 0) { 
                    Integer userid = 0;
                    String real_name = "系统";
                    Byte status = 2;
                    Byte istd = 1;
                    modifystatus(status, pmid, userid, real_name, istd);
                }
            }
            // System.out.println("---------------------------------------------------------------------");
            // System.out.println(excessive_stop);
        }
    }

}
