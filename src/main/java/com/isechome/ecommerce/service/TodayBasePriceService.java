package com.isechome.ecommerce.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.ScTodayBasePriceInfo;
import com.isechome.ecommerce.mapper.ecommerce.ScTodayBasePriceInfoMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 基价设置service
 * @Author: shizg
 * @Date: 2021/8/20 10:35
 * @param
 * @return: null
 **/

@Service
@Slf4j
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class TodayBasePriceService {
    @Autowired
    ScTodayBasePriceInfoMapper scTodayBasePriceInfoMapper;

    @Autowired
    CalcPriceService calcPriceService;
    /**
     * @Description: 显示基价设置页面
     * @Author: shizg
     * @Date: 2021/8/19 18:06
     * @param request:
     * @param model:
     * @return: void
     **/
    public void showBasePrice(HttpServletRequest request, Model model) {
        model.addAttribute("mark", "baseprice");
        model.addAttribute("title","基价设置");
        Date today = Common.getNowDateShort();
        int pmid = SecurityUserUtil.getPmid();
        List<ScTodayBasePriceInfo> listBasePrice = scTodayBasePriceInfoMapper.getTodayBasePrice( today, pmid );
        Double[] basePriceInfo = {0.0,0.0,0.0};
        boolean reviewFlag = false; //是否审核标识
        boolean pricePower = true;  //是否有基价审核权限
        for (int i = 0; i < listBasePrice.size(); i++) {
            int j = listBasePrice.get(i).getType() - 1;
            if ( listBasePrice.get(i).getReviewPrice() > 0 ){
                basePriceInfo[j] = listBasePrice.get(i).getReviewPrice();
            }else {
                basePriceInfo[j] = listBasePrice.get(i).getPrice();
            }
            if ( listBasePrice.get(i).getReviewStatus() == 1 ){
                reviewFlag = true;
            }
        }
        //log.info(Arrays.toString(basePriceInfo));
        Date nowTime = Common.getNowDate();
        //log.info( "日期："+String.valueOf(today)+"时间："+String.valueOf(nowTime) );
        model.addAttribute("basePriceInfo", basePriceInfo );
        model.addAttribute("reviewFlag", reviewFlag );
        model.addAttribute("pricePower", pricePower );

        //历史操作日志
        String pageNumStr = request.getParameter("page");
        Integer page = 0;
        if (pageNumStr == null || pageNumStr == "") {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumStr);
        }
        if (page < 1) {
            page = 1;
        }
        int limit = CommonConstant.PAGE_SIZE;
        PageHelper.startPage(page, limit);
        List<ScTodayBasePriceInfo> scTodayBasePriceInfoList = new ArrayList<>();
        scTodayBasePriceInfoList = scTodayBasePriceInfoMapper.getTodayBasePriceLog(pmid);
        PageInfo<ScTodayBasePriceInfo> pageInfo = new PageInfo<ScTodayBasePriceInfo>(scTodayBasePriceInfoList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("scTodayBasePriceInfoList", scTodayBasePriceInfoList);
    }

    /**
     * @Description: 执行审核或保存
     * @Author: shizg
     * @Date: 2021/8/20 17:43
     * @param request:
     * @return: int
     **/
    public int savePrice(HttpServletRequest request) {
        //前台提交的数据
        float lwPrice = Float.parseFloat( request.getParameter("lwprice") ) ;
        float gxPrice = Float.parseFloat( request.getParameter("gxprice") ) ;
        float plPrice = Float.parseFloat( request.getParameter("plprice") ) ;
        String passWd = request.getParameter("tpwd");
        boolean powerFlag = Boolean.parseBoolean( request.getParameter("power") );
        int checkStatus = checkPriceData( request );
        if ( checkStatus == 0 ) {
            if ( powerFlag ) {
                //执行审核操作
                doReviewPrice( request );
            } else {
                //执行保存操作
                doSavePrice( request );
            }
        }
        return checkStatus;
    }

    /**
     * @Description: 保存基价设置
     * @Author: shizg
     * @Date: 2021/8/21 11:44
     * @param request:
     * @return: void
     **/
    private void doSavePrice(HttpServletRequest request) {
        //从session中获取登录用户信息
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        String userName = loginMessage.getSysAdminUserInfo().getArealname();
        Integer userId =loginMessage.getSysAdminUserInfo().getId();
        int pmid = SecurityUserUtil.getPmid();
        //前台提交的数据
        Double lwPrice = Double.parseDouble( request.getParameter("lwprice") ) ;
        Double gxPrice = Double.parseDouble( request.getParameter("gxprice") ) ;
        Double plPrice = Double.parseDouble( request.getParameter("plprice") ) ;
        Double[] priceArr = {lwPrice, gxPrice, plPrice};
        //执行之前先把数据库中今天的保存数据和已审核数据全部置为无效
        Date today = Common.getNowDateShort() ;
        Date nowTime = Common.getNowDate() ;
        scTodayBasePriceInfoMapper.setTodayBasePriceInvalid( today, pmid , -1);
        //审核今天的数据
        List<ScTodayBasePriceInfo> scTodayBasePriceInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ScTodayBasePriceInfo scTodayBasePriceInfo = new ScTodayBasePriceInfo();
            scTodayBasePriceInfo.setPrice(priceArr[i]);
            scTodayBasePriceInfo.setStatus( (byte)1 );
            scTodayBasePriceInfo.setAdminid(userId);
            scTodayBasePriceInfo.setDate(today);
            scTodayBasePriceInfo.setCreateDate(nowTime);
            scTodayBasePriceInfo.setReviewStatus((byte)-1);
            scTodayBasePriceInfo.setPmid(pmid);
            scTodayBasePriceInfo.setType( (byte)(i+1) );
            scTodayBasePriceInfo.setReviewPrice((Double)0.0);
            scTodayBasePriceInfo.setReviewDate(nowTime);
            scTodayBasePriceInfo.setReviewId(0);
            scTodayBasePriceInfo.setAdminName(userName);
            scTodayBasePriceInfo.setReviewName("");
            scTodayBasePriceInfoList.add(scTodayBasePriceInfo);
        }
        scTodayBasePriceInfoMapper.insertListData( scTodayBasePriceInfoList );
    }

    /**
     * @Description: 执行审核操作
     * @Author: shizg
     * @Date: 2021/8/21 11:34
     * @param request:
     * @return: void
     **/
    private void doReviewPrice(HttpServletRequest request) {
        //从session中获取登录用户信息
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        String userName = loginMessage.getSysAdminUserInfo().getArealname();
        Integer userId =loginMessage.getSysAdminUserInfo().getId();
        int pmid = SecurityUserUtil.getPmid();
        //前台提交的数据
        Double lwPrice = Double.parseDouble( request.getParameter("lwprice") ) ;
        Double gxPrice = Double.parseDouble( request.getParameter("gxprice") ) ;
        Double plPrice = Double.parseDouble( request.getParameter("plprice") ) ;
        Double[] priceArr = {lwPrice, gxPrice, plPrice};
        //执行之前先把数据库中今天的保存数据和已审核数据全部置为无效
        Date today = Common.getNowDateShort() ;
        Date nowTime = Common.getNowDate() ;
        scTodayBasePriceInfoMapper.setTodayBasePriceInvalid( today, pmid, 0 );
        //保存今天的数据
        List<ScTodayBasePriceInfo> scTodayBasePriceInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ScTodayBasePriceInfo scTodayBasePriceInfo = new ScTodayBasePriceInfo();
            scTodayBasePriceInfo.setReviewPrice(priceArr[i]);
            scTodayBasePriceInfo.setStatus( (byte)1 );
            scTodayBasePriceInfo.setReviewId(userId);
            scTodayBasePriceInfo.setDate(today);
            scTodayBasePriceInfo.setReviewDate(nowTime);
            scTodayBasePriceInfo.setReviewStatus((byte)1);
            scTodayBasePriceInfo.setPmid(pmid);
            scTodayBasePriceInfo.setType( (byte)(i+1) );
            scTodayBasePriceInfo.setPrice((Double) 0.0);
            scTodayBasePriceInfo.setAdminid(0);
            scTodayBasePriceInfo.setCreateDate(nowTime);
            scTodayBasePriceInfo.setAdminName("");
            scTodayBasePriceInfo.setReviewName(userName);
            scTodayBasePriceInfoList.add(scTodayBasePriceInfo);
        }
        scTodayBasePriceInfoMapper.insertListData( scTodayBasePriceInfoList );
        //执行计算资源价格
        calcPriceService.CalcTodayPrice(pmid);
    }

    /**
     * @Description: 检查前台提交的基价数据
     * @Author: shizg
     * @Date: 2021/8/21 9:35
     * @param request:
     * @return: int
     **/
    private int checkPriceData(HttpServletRequest request) {
        int returnStatus = 0;
        float lwPrice = Float.parseFloat( request.getParameter("lwprice") ) ;
        float gxPrice = Float.parseFloat( request.getParameter("gxprice") ) ;
        float plPrice = Float.parseFloat( request.getParameter("plprice") ) ;
        String passWd = request.getParameter("tpwd");
        //基价信息检查
        if ( lwPrice > 1.0 && gxPrice > 1.0 && plPrice > 1.0 ){
            //交易密码检查
            if ( !Common.cheakTredePassWD(passWd) ) {
                returnStatus = -1;
            }
        }else{
            returnStatus = 2; //价格数据无效
        }
        return returnStatus;
    }

    /**
     * @Description: 修改极差计算今日资源价格
     * @Author: shizg
     * @Date: 2021/9/7 17:00
     * @param request:
     * @return: int
     **/
    public int updateResourcePrice(HttpServletRequest request) {
        int pmid = SecurityUserUtil.getPmid();
        //执行计算资源价格
        calcPriceService.CalcTodayPrice(pmid);
        return 1;
    }

}