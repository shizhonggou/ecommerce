/*
 * @Author: shitaodi
 * @Date: 2021-08-26 18:20:25
 * @LastEditTime: 2021-10-14 14:14:55
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\ScOrderListController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;


import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.service.ScOrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import com.isechome.ecommerce.view.ScOrderList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("scorderlist")
public class ScOrderListController {
    @Autowired
    private ScOrderListService scOrderListService;
   
   
    //订单修改获取详情信息
    @RequestMapping("/getDetailByid")
    public ScOrderListDetail getDetailByid( HttpServletRequest request, HttpServletResponse response) throws IOException {
        return scOrderListService.getDetailByid( request ,response);
    }

    //订单修改
    @RequestMapping("/updateDetailById")
    public int updateDetailById( HttpServletRequest request, HttpServletResponse response) throws ParseException {
        return scOrderListService.updateDetailById( request,response);
    }
   
    //订单取消
    @RequestMapping("/updateOrderStatus")
    public int updateOrderStatus( HttpServletRequest request ){
        return  scOrderListService.updateOrderStatus(request);
    }

    //订单确认
    @RequestMapping("/updateOrderQren")
    public Integer updateOrderQren( HttpServletRequest request ) throws ParseException{
        return  scOrderListService.updateOrderQren(request);
    }
    
    //入账订单付款
    @RequestMapping("/orderMoneyMach")
    public void orderMoneyMach( HttpServletRequest request ) throws ParseException{
        Integer pmid=Integer.parseInt(request.getParameter("pmid"));
        Integer company_id=Integer.parseInt(request.getParameter("company_id"));
        Double money=Double.parseDouble(request.getParameter("money"));
        scOrderListService.orderMoneyMach(pmid,company_id,money);
    }

    //出账匹配采购金
    @RequestMapping("/purchaseMoneyMach")
    public void purchaseMoneyMach( HttpServletRequest request ) throws ParseException{
        Integer pmid=Integer.parseInt(request.getParameter("pmid"));
        Integer supplier_mid=Integer.parseInt(request.getParameter("supplier_mid"));
        Double money=Double.parseDouble(request.getParameter("money"));
        scOrderListService.purchaseMoneyMach(pmid,supplier_mid,money);
    }
    
    //提货单列表
    @RequestMapping("/getDetaiLogisticsByid")
    public ScOrderListDetail getDetaiLogisticsByid( HttpServletRequest request, HttpServletResponse response) throws IOException {
        return scOrderListService.getDetaiLogisticsByid( request ,response);
    }
   
   
    @RequestMapping("/addDetailPurchase")
    public int addDetailPurchase(HttpServletRequest request) throws ParseException{
        return  scOrderListService.addDetailPurchase(request);
    }

     //提货单拆分
    @RequestMapping("/setPurchaseList")
    public int setPurchaseList(HttpServletRequest request) throws ParseException{
        return  scOrderListService.setPurchaseList(request);
    }
    

    //采购单审核
    @RequestMapping("/shenheOrderPurchase")
    public int shenheOrderPurchase(HttpServletRequest request) throws ParseException{
        return  scOrderListService.shenheOrderPurchase(request);
    }

    //采购单审核
    @RequestMapping("/rejectOrderPurchase")
    public int rejectOrderPurchase(HttpServletRequest request) throws ParseException{
        return  scOrderListService.rejectOrderPurchase(request);
    }


    //打印提货单pdf
    @RequestMapping("/printPdf")
    public ModelAndView printPdf(Integer id) throws Exception{
        Map<String, Object> model=new HashMap<>();
        model=scOrderListService.getLogisticsById(id);
        model.put("type", 1);
        return new ModelAndView(new ScOrderList(), model);
    }


    //打印合同
    @RequestMapping("/printHt")
    public ModelAndView printHt(Integer id) throws Exception{
        Map<String, Object> model=new HashMap<>();
        model=scOrderListService.getOrderHtById(id);
        model.put("type", 2);
        return new ModelAndView(new ScOrderList(), model);
    }

    //超量资源管理取消
    @RequestMapping("/delete_order_resource")
    public  Map<String,Object> delete_order_resource(HttpServletRequest request) throws ParseException{
        return  scOrderListService.delete_order_resource(request);
    }

    //超量资源管理取消
    @RequestMapping("/getOrderThd")
    public  Map<String, LogisticsPurchase> getOrderThd(HttpServletRequest request) throws ParseException{
        return  scOrderListService.getOrderThd(request);
    }
    

    

}
