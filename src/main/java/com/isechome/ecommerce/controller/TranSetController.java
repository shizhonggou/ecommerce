/*
 * @Author: lina
 * @Date: 2021-06-07 16:31:56
 * @LastEditTime: 2021-11-05 14:24:15
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\TranSetController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.ReturnObj;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.OrderStatusjobService;
import com.isechome.ecommerce.service.StopOpenPlateService;
import com.isechome.ecommerce.service.TranSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
@RequestMapping("transet")
public class TranSetController {
    @Autowired
    private TranSetService tranSetService;
    @Autowired
    private StopOpenPlateService stopOpenPlateService;
    @Autowired
    private OrderStatusjobService orderStatusjobService;

    //保存交易时间设置
    @RequestMapping("/saveworktime")
    public void saveworktime( HttpServletRequest request, HttpServletResponse res) throws IOException {
        tranSetService.saveworktime( request );
        Common common=new Common();
        PrintWriter out=new PrintWriter(res.getOutputStream());	
        res.setContentType("text/html;charset=UTF-8");
        common.alert("保存成功", out);
        common.goURL("/transet/worktime" ,out);
    }

    //保存调价录入
    @RequestMapping("/savemodifyprice")
    public void savemodifyprice( HttpServletRequest request, HttpServletResponse res) throws IOException {
        tranSetService.savemodifyprice( request );
        Common common=new Common();
        PrintWriter out=new PrintWriter(res.getOutputStream());	
        res.setContentType("text/html;charset=UTF-8");
        common.alert("保存成功", out);
        common.goURL("/transet/modifyprice" ,out);
    }

    //手动停开盘
    @RequestMapping("/modifystatus")
    public ReturnObj modifystatus( HttpServletRequest request){
        Byte status = Byte.parseByte(request.getParameter("status"));
        ReturnObj returnObj = new ReturnObj();
        Integer pmid = SecurityUserUtil.getPmid();
        SecuritySysUser sessionuser = SecurityUserUtil.getCurrentUser();  
        Integer userid = sessionuser.getId();
        String real_name = sessionuser.getSysAdminUserInfo().getArealname();
        Byte istd = 0;
        returnObj = stopOpenPlateService.modifystatus(status, pmid, userid, real_name, istd);
        return returnObj;
    }

    // 测试停开盘定时
    @RequestMapping("/testOpenStopPlateJob")
    public void testOpenStopPlateJob(){
        Integer pmid = 191233;
        stopOpenPlateService.OpenStopPlateJob("1" ,pmid);
    }

    // 测试停开盘定时
    @RequestMapping("/testOverSaleStop")
    public void testOverSaleStop(){
        Integer pmid = 191233;
        stopOpenPlateService.overSaleStop(pmid);
    }

    // 测试调价定时
    @RequestMapping("/testsaveComTask")
    public void testsaveComTask(){
        Integer pmid = 0;
        String orderid = "DD16304078047918439";
        orderStatusjobService.saveComTask(orderid, pmid);
    }

}
