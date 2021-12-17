/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-11-02 08:55:41
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\TranSetService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.ScModifyPrice;
import com.isechome.ecommerce.entity.ScOrderListBase;
import com.isechome.ecommerce.entity.ScSysStatus;
import com.isechome.ecommerce.entity.ScSysStatusLog;
import com.isechome.ecommerce.entity.ScWorkTime;
import com.isechome.ecommerce.entity.TaskConfigEntity;
import com.isechome.ecommerce.mapper.ecommerce.ScModifyPriceMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListBaseMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSysStatusLogMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSysStatusMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScWorkTimeMapper;
import com.isechome.ecommerce.mapper.ecommerce.TaskConfigEntityMapper;
import com.isechome.ecommerce.security.SecuritySysUser;

import org.quartz.SchedulerConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import cn.hutool.core.date.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class TranSetService {
    @Autowired
    ScWorkTimeMapper scWorkTimeMapper;
    @Autowired
    ScModifyPriceMapper scModifyPriceMapper;
    @Autowired
    ScSysStatusMapper scSysStatusMapper;
    @Autowired
    ScSysStatusLogMapper scSysStatusLogMapper;
    @Autowired
    TaskConfigEntityMapper taskConfigEntityMapper;
    @Autowired
    StopOpenPlateService stopOpenPlateService;
    @Autowired
    ScheduleJobService scheduleJobService;
    @Autowired
    CalcPriceService calcPriceService;
    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper;

    private PageInfo<ScSysStatusLog> pageInfo;

    // 交易时间设置
    public void worktime(HttpServletRequest request, Model model) {
        Integer pmid = SecurityUserUtil.getPmid();
        ScWorkTime scWorkTime = new ScWorkTime();
        scWorkTime = scWorkTimeMapper.selectByPmid(pmid);
        if (scWorkTime == null) {
            scWorkTime = new ScWorkTime();
        }
        model.addAttribute("scWorkTime", scWorkTime);
        model.addAttribute("mark", "scworktime");

        // 开停盘
        String pageNumStr = request.getParameter("page");
        Integer page = 0;
        if (pageNumStr == null || pageNumStr.equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumStr);
        }
        if (page < 1) {
            page = 1;
        }
        int limit = CommonConstant.PAGE_SIZE;
        PageHelper.startPage(page, limit);

        List<ScSysStatusLog> scSysStatusLogList = new ArrayList<>();
        scSysStatusLogList = scSysStatusLogMapper.getStatusLogList(pmid);
        pageInfo = new PageInfo<ScSysStatusLog>(scSysStatusLogList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("scSysStatusLogList", scSysStatusLogList);

        //今日已售
        // 获取今日现货销量
        Date dateNow = new Date();
        //一天的开始
        Date beginOfDay = DateUtil.beginOfDay(dateNow);
        //一天的结束
        Date endOfDay = DateUtil.endOfDay(dateNow);
        ScOrderListBase scOrderListBase = new ScOrderListBase();
        scOrderListBase = scOrderListBaseMapper.getSumNum(pmid, beginOfDay, endOfDay);
        Double saleNum = 0.0;
        if (scOrderListBase != null) {
            saleNum = scOrderListBase.getNum();
        }
        model.addAttribute("saleNum", saleNum);
        
        ScSysStatus scSysStatus = new ScSysStatus();
        scSysStatus = stopOpenPlateService.getTodayScSysStatus(pmid);
        model.addAttribute("trantype", scSysStatus.getTrantype());
        model.addAttribute("excessive_stop", scSysStatus.getExcessiveStop());
    }

    // 保存交易时间设置
    public void saveworktime(HttpServletRequest request) {
        Common common=new Common();
        Integer pmid = SecurityUserUtil.getPmid();
        SecuritySysUser sessionuser = SecurityUserUtil.getCurrentUser();  
        Integer userid = sessionuser.getId();
        Date start_time = common.strToDate(request.getParameter("start_time"), "HH:mm");
        Date end_time = common.strToDate(request.getParameter("end_time"), "HH:mm");
        Date delay_time = common.strToDate(request.getParameter("delay_time"), "HH:mm");
        Double excessive_stop = Double.valueOf(request.getParameter("excessive_stop"));
        Date dateNow = new Date();
        String idstr = request.getParameter("id");
        Integer id = 0;
        if(idstr != null && !idstr.equals("") ){
            id = Integer.parseInt(idstr);
        }
        
        ScWorkTime scWorkTime = new ScWorkTime();
        scWorkTime.setPmid(pmid);
        scWorkTime.setStartTime(start_time);
        scWorkTime.setEndTime(end_time);
        scWorkTime.setDelayTime(delay_time);
        if (id > 0) {
            ScWorkTime scWorkTimeOld = new ScWorkTime();
            scWorkTimeOld = scWorkTimeMapper.selectByPrimaryKey(id);
            scWorkTime.setId(id);
            scWorkTime.setCreatTime(scWorkTimeOld.getCreatTime());
            scWorkTime.setCreateUserId(scWorkTimeOld.getCreateUserId());
            scWorkTime.setUpdateTime(dateNow);
            scWorkTime.setUpdateUserId(userid);
            scWorkTimeMapper.updateByPrimaryKey(scWorkTime);
        } else {
            scWorkTime.setCreatTime(dateNow);
            scWorkTime.setCreateUserId(userid);
            scWorkTimeMapper.insert(scWorkTime);
        }
        // 保存停开盘定时任务
        saveTask(pmid, start_time, end_time);

        //保存现货销售封库
        saveExcessive_stop(pmid, excessive_stop);
    }  

    /**
     * @Author: lina
     * @Description: //保存现货销售封库
     * @Date: 2021-09-18 10:26:28
     * @param {Integer} pmid
     * @param {Double} excessive_stop
     */    
    public void saveExcessive_stop(Integer pmid, Double excessive_stop){
        Date dateNow = new Date();
        ScSysStatus scSysStatusOld = new ScSysStatus();
        scSysStatusOld = scSysStatusMapper.selTodyStatus(pmid, dateNow);
        // System.out.println("---------------------------------------------------------------------");
        // System.out.println(Double.toString(excessive_stop));
        if (!Double.toString(excessive_stop).equals(Double.toString(scSysStatusOld.getExcessiveStop()))) {
            SecuritySysUser sessionuser = SecurityUserUtil.getCurrentUser();  
            Integer userid = sessionuser.getId();
            String real_name = sessionuser.getSysAdminUserInfo().getArealname();
            ScSysStatus scSysStatus = new ScSysStatus();
            scSysStatus.setPmid(pmid);
            scSysStatus.setDate(dateNow);
            scSysStatus.setTrantype(scSysStatusOld.getTrantype());
            scSysStatus.setExcessiveStop(excessive_stop);
            scSysStatus.setUpdateTime(dateNow);
            scSysStatus.setUpdateUserId(userid);
            scSysStatusMapper.updateByPmid(scSysStatus);
            // 日志保存
            stopOpenPlateService.scSysStatusLogSave(pmid, userid, real_name);
        }
    }
    
    // 保存停开盘定时任务
    public void saveTask(Integer pmid, Date start_time, Date end_time) {
        String open_time_cron = getCron(start_time);
        String stop_time_cron = getCron(end_time);
        String open_class_name = "com.isechome.ecommerce.Job.OpenPlateJob";
        String stop_class_name = "com.isechome.ecommerce.Job.StopPlateJob";
        Byte status  = 1;
        Byte is_deleted  = 0;
        TaskConfigEntity opemTask = new TaskConfigEntity();
        opemTask.setCron(open_time_cron);
        opemTask.setClassName(open_class_name);
        opemTask.setDescription("开盘定时");
        opemTask.setStatus(status);
        opemTask.setIsDeleted(is_deleted);
        opemTask.setPmid(pmid);
        TaskConfigEntity stopTask = new TaskConfigEntity();
        stopTask.setCron(stop_time_cron);
        stopTask.setClassName(stop_class_name);
        stopTask.setDescription("停盘定时");
        stopTask.setStatus(status);
        stopTask.setIsDeleted(is_deleted);
        stopTask.setPmid(pmid);
        insertTask(opemTask);
        insertTask(stopTask);
    }

    public void insertTask(TaskConfigEntity taskConfigEntity) {
        TaskConfigEntity getTask = new TaskConfigEntity();
        getTask = taskConfigEntityMapper.getTask(taskConfigEntity);
        String task_id_head = "";
        if (taskConfigEntity.getDescription() == "开盘定时") {
            task_id_head = "KP";
        } else if (taskConfigEntity.getDescription() == "停盘定时") {
            task_id_head = "TP";
        }
        if (getTask == null) {
            String class_name = taskConfigEntity.getClassName();
            String max_task_id = taskConfigEntityMapper.getMaxTaskId(class_name);
            if (max_task_id == null) {
                max_task_id = "000000";
            } else {
                max_task_id = max_task_id.replace("TP", "");
                max_task_id = max_task_id.replace("KP", "");
            }
            Integer taskid = Integer.parseInt(max_task_id) + 1;
            String task_id = task_id_head + String.format("%06d", taskid);
            
            taskConfigEntity.setTaskId(task_id);
            try {
                scheduleJobService.loadJob(taskConfigEntity);
            } catch (SchedulerConfigException e) {
                e.printStackTrace();
            }
        } else {
            taskConfigEntityMapper.updateTask(taskConfigEntity);
            try {
                scheduleJobService.reload(getTask.getTaskId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 获取停开盘时间的cron
    public String getCron(Date time) {
        Common common=new Common();
        String cron = "0 "+common.getMinute(time)+" "+common.getHour(time)+" * * ?";
        return cron;
    }

    // 调价录入界面
    public void modifyprice(HttpServletRequest request, Model model) {
        Integer pmid = SecurityUserUtil.getPmid();
        Date dateNow = new Date();
        List<ScModifyPrice> scModifyPriceList = new ArrayList<>();
        scModifyPriceList = scModifyPriceMapper.selectListByPmid(pmid, dateNow);
        String isnull = "0";
        if (scModifyPriceList == null || scModifyPriceList.size() ==0 ) {
            isnull = "1";
        }
        model.addAttribute("scModifyPriceList", scModifyPriceList);
        model.addAttribute("isnull", isnull);
        model.addAttribute("mark", "modifyprice");
    }

    // 保存调价录入
    public void savemodifyprice(HttpServletRequest request) {
        Integer pmid = SecurityUserUtil.getPmid();
        SecuritySysUser sessionuser = SecurityUserUtil.getCurrentUser();  
        Integer userid = sessionuser.getId();
        String luowenstr = request.getParameter("luowen");
        String gaoxianstr = request.getParameter("gaoxian");
        String panluostr = request.getParameter("panluo");
        Date dateNow = new Date();
        String isnull = request.getParameter("isnull");
        if (isnull != "1") {
            scModifyPriceMapper.statusDel(pmid, dateNow);
        }
        Double luowen = 0.0;
        Double gaoxian = 0.0;
        Double panluo = 0.0;
        if(luowenstr != null && !luowenstr.equals("") ){
            luowen = Double.parseDouble(luowenstr);
        }
        if(gaoxianstr != null && !gaoxianstr.equals("") ){
            gaoxian = Double.parseDouble(gaoxianstr);
        }
        if(panluostr != null && !panluostr.equals("") ){
            panluo = Double.parseDouble(panluostr);
        }
        
        int[] typearr = new int[]{1,2,3};
        for(int i=0;i<typearr.length;i++){
            ScModifyPrice scModifyPrice = new ScModifyPrice();
            scModifyPrice.setPmid(pmid);
            scModifyPrice.setDate(dateNow);
            Byte status = 1;
            scModifyPrice.setStatus(status);
            int type = typearr[i];
            scModifyPrice.setType(type);
            if (type == 1) {
                scModifyPrice.setPrice(luowen);
            } else if (type == 2) {
                scModifyPrice.setPrice(gaoxian);
            } else if (type == 3) {
                scModifyPrice.setPrice(panluo);
            }
            scModifyPrice.setCreatTime(dateNow);
            scModifyPrice.setCreateUserId(userid);
            scModifyPriceMapper.insert(scModifyPrice);
        }

        stopOpenPlateService.cleanOrder(pmid);
        calcPriceService.CalcTodayPrice(pmid);
    }

    // 手动开盘停盘界面
    public void sysstatus(HttpServletRequest request, Model model) {
        Integer pmid = SecurityUserUtil.getPmid();
        String pageNumStr = request.getParameter("page");
        Integer page = 0;
        if (pageNumStr == null || pageNumStr.equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumStr);
        }
        if (page < 1) {
            page = 1;
        }
        int limit = CommonConstant.PAGE_SIZE;
        PageHelper.startPage(page, limit);

        List<ScSysStatusLog> scSysStatusLogList = new ArrayList<>();
        scSysStatusLogList = scSysStatusLogMapper.getStatusLogList(pmid);
        pageInfo = new PageInfo<ScSysStatusLog>(scSysStatusLogList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("scSysStatusLogList", scSysStatusLogList);
        
        Byte trantype = stopOpenPlateService.getTodayScSysStatus(pmid).getTrantype();
        model.addAttribute("trantype", trantype);
        model.addAttribute("mark", "sysstatus");
    }

}
