/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-06-25 16:29:16
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\OrderInformationService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;


import com.isechome.ecommerce.entity.ResourceSales;
import com.isechome.ecommerce.mapper.isechome.ResourceSalesMapper;
import com.isechome.ecommerce.security.SecuritySysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.AdminUserInfo;
import com.isechome.ecommerce.entity.CompanyInfo;
import com.isechome.ecommerce.entity.LogisticsInformation;
import com.isechome.ecommerce.entity.OrderInformation;
import com.isechome.ecommerce.mapper.isechome.AdminUserInfoMapper;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoMapper;
import com.isechome.ecommerce.mapper.isechome.LogisticsInformationMapper;
import com.isechome.ecommerce.mapper.isechome.OrderInformationMapper;

@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class OrderInformationService {
    @Autowired
    OrderInformationMapper orderInformationMapper;
	@Autowired
    CompanyInfoMapper companyInfoMapper;
    @Autowired
    AdminUserInfoMapper adminUserInfoMapper;
    @Autowired
    LogisticsInformationMapper logisticsInformationMapper;
    @Autowired
    ResourceSalesMapper resourceSalesMapper;

    private PageInfo<OrderInformation> pageInfo;

    // 订单首页
    public void index(HttpServletRequest request, Model model) {
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

        String status = request.getParameter("status");
        String varietyName = request.getParameter("varietyName");
        String Material = request.getParameter("Material");
        String Spec = request.getParameter("Spec");
        String origin_code = request.getParameter("origin_code");
        String stime = request.getParameter("stime");
        String etime = request.getParameter("etime");
        if(status == null){
            status = "1";
        }
        Byte status1 = (byte)Integer.parseInt(status);
        if(varietyName == null){
            varietyName = "";
        }
        if(Material == null){
            Material = "";
        }
        if(Spec == null){
            Spec = "";
        }
        if(origin_code == null){
            origin_code = "";
        }
        if(stime == null){
            stime = "";
        }
        if(etime == null){
            etime = "";
        }
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        Integer mid = loginMessage.getCompanyInfo().getId();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",status1);
        map.put("varietyName",varietyName);
        map.put("Material",Material);
        map.put("Spec",Spec);
        map.put("origin_code",origin_code);
        map.put("stime",stime);
        map.put("etime",etime);
        map.put("mid",mid);
        List<OrderInformation> orderInformation = new ArrayList<>();
        orderInformation = orderInformationMapper.getOrderList(map);
        pageInfo = new PageInfo<OrderInformation>(orderInformation);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("orderlist", orderInformation);
        model.addAttribute("status", status);
        model.addAttribute("varietyName", varietyName);
        model.addAttribute("Material", Material);
        model.addAttribute("Spec", Spec);
        model.addAttribute("origin_code", origin_code);
        model.addAttribute("stime", stime);
        model.addAttribute("etime", etime);
        model.addAttribute("mark", "order");
    }

    //订单状态改变
    public void updateStatus(Integer id,  Byte status){
        orderInformationMapper.updateStatus(id, status);
    }

    //订单详情界面
    public void detail(HttpServletRequest request,  Model model){
        Integer id = Integer.parseInt(request.getParameter("id"));
        OrderInformation orderInformation = new OrderInformation();
        orderInformation = orderInformationMapper.getOrderByid(id);
        //采购方、供应方
        Integer sales_mid = orderInformation.getResourceSales().getSalesMid();
        Integer purchase_mid = orderInformation.getPurchaseMid();
        CompanyInfo salesCompany = new CompanyInfo();
        CompanyInfo purchaseCompany = new CompanyInfo();
        salesCompany = companyInfoMapper.selectByPrimaryKey(sales_mid);
        purchaseCompany = companyInfoMapper.selectByPrimaryKey(purchase_mid);
        //收货人
        Integer link_user_id = purchaseCompany.getLinkUserId();
        AdminUserInfo linkUser = new AdminUserInfo();
        linkUser = adminUserInfoMapper.selectByPrimaryKey(link_user_id);
        // 物流信息
        List<LogisticsInformation> logisticsInformation = new ArrayList<>();
        logisticsInformation = logisticsInformationMapper.selectByOrderId(id);
        // 提货人
        List<AdminUserInfo> shipper = new ArrayList<>();
        shipper = adminUserInfoMapper.selectByMid(purchase_mid);
        Byte status = orderInformation.getStatus();
        // 审核权限
        Integer isshenghe = 0;
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        Integer mid = session.getCompanyInfo().getId();
        if (status == 1 && mid == sales_mid) {
            isshenghe = 1;
        }
        // 已完成权限
        Integer iswancheng = 0;
        if (status == 3 && mid == sales_mid) {
            iswancheng = 1;
        }
        // 物流添加权限
        Integer iswuliu = 0;
        if (status != 5 && mid == sales_mid) {
            iswuliu = 1;
        }
        model.addAttribute("orderInfo", orderInformation);
        model.addAttribute("salesCompany", salesCompany);
        model.addAttribute("purchaseCompany", purchaseCompany);
        model.addAttribute("linkUser", linkUser);
        model.addAttribute("logisticsInfo", logisticsInformation);
        model.addAttribute("shipper", shipper);
        model.addAttribute("isshenghe", isshenghe);
        model.addAttribute("iswancheng", iswancheng);
        model.addAttribute("iswuliu", iswuliu);
        model.addAttribute("mark", "order");
    }

    //物流添加
    public void addLogistics(HttpServletRequest request){
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        Integer userid = session.getId();
        Date datenow = new Date();
        Integer orderid = Integer.valueOf(request.getParameter("orderid"));
        String addcar_number = request.getParameter("car_number");
        Double addpiece = Double.parseDouble(request.getParameter("piece"));
        Double addnum = Double.parseDouble(request.getParameter("num"));
        Integer addshipper_id = Integer.valueOf(request.getParameter("shipper_id"));       

        OrderInformation orderInformation = new OrderInformation();
        orderInformation = orderInformationMapper.selectByPrimaryKey(orderid);

        Byte status = orderInformation.getStatus();
        Double piece = orderInformation.getPiece();
        Double actualPiece = orderInformation.getActualPiece();
        Double actualNum = orderInformation.getActualNum();
        Double pieceDif = Math.abs(piece - actualPiece - addpiece);
        if (pieceDif < 0.00000000001) {
            Byte chstatus = 4;  //发货完成
            updateStatus(orderid,  chstatus);
        } else if (status == 2) {
            Byte chstatus = 3;
            updateStatus(orderid,  chstatus); //发货中
        } 

        Double actual_piece= actualPiece + addpiece;
        Double actual_num= actualNum + addnum;
        orderInformationMapper.updateActual(orderid, actual_piece, actual_num);

        LogisticsInformation logisticsInformation = new LogisticsInformation();
        logisticsInformation.setCarNumber(addcar_number);
        logisticsInformation.setOrderId(orderid);
        logisticsInformation.setPiece(addpiece);
        logisticsInformation.setNum(addnum);
        logisticsInformation.setShipperId(addshipper_id);
        logisticsInformation.setCreatTime(datenow);
        logisticsInformation.setCreateUserId(userid);
        logisticsInformationMapper.insert(logisticsInformation);
    }

    // 删除物流信息
    public void delLogistics(Integer id){
        LogisticsInformation logisticsInformation = new LogisticsInformation();
        logisticsInformation = logisticsInformationMapper.selectByPrimaryKey(id);

        Integer orderid = logisticsInformation.getOrderId();
        OrderInformation orderInformation = new OrderInformation();
        orderInformation = orderInformationMapper.selectByPrimaryKey(orderid);

        Byte status = orderInformation.getStatus();
        Double addpiece = logisticsInformation.getPiece();
        Double addnum = logisticsInformation.getNum();
        Double actual_piece = orderInformation.getActualPiece() - addpiece;
        Double actual_num = orderInformation.getActualNum() - addnum; 
        if (actual_piece  < 0.00000000001) {
            Byte chstatus = 2;  //待发货
            updateStatus(orderid,  chstatus);
        } else if (status == 4) {
            Byte chstatus = 3;
            updateStatus(orderid,  chstatus); //发货中
        }
        orderInformationMapper.updateActual(orderid, actual_piece, actual_num);

        logisticsInformationMapper.deleteByPrimaryKey(id);
    }

    //审核订单
    public String orderShenhe(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        Integer userid = session.getId();
        Date datenow = new Date();
        Integer orderid = Integer.parseInt(request.getParameter("id"));
        OrderInformation orderInformation = new OrderInformation();
        orderInformation = orderInformationMapper.selectByPrimaryKey(orderid);
        ResourceSales resourceSales = new ResourceSales();
        Integer rs_id = orderInformation.getRsId();
        resourceSales = resourceSalesMapper.getSalesResourceById(rs_id);
        Double order_num = orderInformation.getNum();
        Double salses_num = resourceSales.getNum();
        Double sold_num = resourceSales.getSoldNum();
        Double num_dif = salses_num - sold_num - order_num;
        if (num_dif  < 0) {            
       
            return "2";
        } else {
            Double SoldNum = sold_num + order_num;
            resourceSalesMapper.updateSoldNumById(rs_id, SoldNum);
            orderInformationMapper.orderShenhe(orderid, userid, datenow);
            return "1";
        }
    }


    /**
     * @Description: 从购物车生成订单
     * @Author: shizg
     * @Date: 2021/6/10 8:53
     * @param request:
     * @return: void
     **/
    public String generate_order(HttpServletRequest request) {
        String errorMessage = "1";
        String purchase_resource_ids = request.getParameter("purchase_resource_ids");
        String[] resourceIdArray  = StringUtils.split(purchase_resource_ids, ",");
        String purchase_resource_piece = request.getParameter("purchase_resource_piece");
        String[] pieceArray  = StringUtils.split(purchase_resource_piece, ",");
        String purchase_resource_num = request.getParameter("purchase_resource_num");
        String[] numArray  = StringUtils.split(purchase_resource_num, ",");

        Integer[] resourceIds = new Integer[resourceIdArray.length];
        Integer[] resourceIdsEffective = new Integer[resourceIdArray.length];
        Double[] pieces = new Double[resourceIdArray.length];
        Double[] numS = new Double[resourceIdArray.length];
        for ( int i=0; i< resourceIdArray.length; i++ ) {
            resourceIds[i] = Integer.parseInt( resourceIdArray[i] );
            pieces[i] = Double.parseDouble(pieceArray[i]);
            if ( numArray[i] !="" && numArray[i]!=null ) {
                numS[i] = Double.parseDouble(numArray[i]);
            }else {
                numS[i] = 0.0;
            }
        }

        //根据资源id一条一条生成订单
        for ( int i2 = 0; i2 < resourceIds.length; i2++ ) {
            ResourceSales resourceSales = resourceSalesMapper.getSalesResourceById(resourceIds[i2]);
            //检查数量
            String checkMessage = check_purchase_num(resourceSales,numS[i2]);
            if ( checkMessage == "1" ) {
                resourceIdsEffective[i2] = resourceIds[i2];
                //执行生成每条资源的单子
                do_generate_order( request, resourceSales, pieces[i2], numS[i2] );
            }else {
                if (errorMessage.equals("1")) {
                    errorMessage = "";
                }
                resourceIdsEffective[i2] = 0;
                errorMessage += checkMessage+"\n";
            }
        }
        //生成订单后从session中去除掉购物车的数据
        generate_order_change_session( request, resourceIdsEffective );
        return errorMessage;
    }

    /**
     * @Description:生成订单后从session中去除掉购物车的数据
     * @Author: shizg
     * @Date: 2021/6/10 10:46
     * @param request:
     * @param resourceIds:
     * @return: void
     **/
    public void generate_order_change_session( HttpServletRequest request, Integer[] resourceIds ){
        HttpSession session = request.getSession();
        List<ResourceSales> resourceSalesList = (List<ResourceSales>)session.getAttribute("purchase_car");
        for ( int i = 0; i < resourceIds.length; i++ ) {
            if (resourceSalesList != null) {
                Iterator<ResourceSales> it = resourceSalesList.iterator();
                while (it.hasNext()) {
                    ResourceSales resourceSales = it.next();
                    if (resourceSales.getId() == resourceIds[i]) {
                        it.remove();
                    }
                }
                session.setAttribute("purchase_car", resourceSalesList);
            }
        }
    }

    /**
     * @Description:检查下单数量
     * @Author: shizg
     * @Date: 2021/6/10 11:36
     * @param resourceSales:
     * @param num:
     * @return: java.lang.String
     **/
    public String check_purchase_num(ResourceSales resourceSales, Double num ) {
        String returnStr = "";
        Double availableNum = resourceSales.getNum() -resourceSales.getSoldNum();
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        availableNum = Double.parseDouble( df.format(availableNum) ) ;
        if ( num >0 && num <= availableNum ){
            returnStr = "1";
        }else if (num > availableNum ){
            returnStr = resourceSales.getVarietyname()+" "+resourceSales.getMaterial()+" "+resourceSales.getSpec()+" "+resourceSales.getCangku()+" 的资源下单数量超过可购买量"+availableNum;
        }else {
            returnStr = resourceSales.getVarietyname()+" "+resourceSales.getMaterial()+" "+resourceSales.getSpec()+" "+resourceSales.getCangku()+" 的资源下单数量检查不通过";
        }
        return returnStr;
    }

    /**
     * @Description:执行生成每条资源的单子
     * @Author: shizg
     * @Date: 2021/6/10 11:43
     * @param request:
     * @param resourceSales:
     * @param piece:
     * @param num:
     * @return: void
     **/
    public void do_generate_order( HttpServletRequest request, ResourceSales resourceSales, Double piece, Double num ) {
        HttpSession session = request.getSession();
        OrderInformation orderInformation = new OrderInformation();
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        Integer purchaseMid = loginMessage.getCompanyInfo().getId();
        Integer userId =loginMessage.getAdminUserInfo().getId();
        Byte orderType = CommonConstant.LUOWEN_VID;
        Byte status = 1;
        Byte whereFrom = 1;
        Date now = new Date();
        if (resourceSales.getVid() == CommonConstant.LUOWEN_VID ) {
            orderType = CommonConstant.LUOWEN_VID;
        }else {
            orderType = CommonConstant.PANLUO_VID;
        }
        //生成订单id
        String orderId = get_order_ddid(resourceSales.getVid(), userId );
        orderInformation.setOrderId(orderId);
        orderInformation.setOrderType(orderType);
        orderInformation.setRsId(resourceSales.getId());
        orderInformation.setPurchaseMid(purchaseMid);
        orderInformation.setPiece(piece);
        orderInformation.setNum(num);
        orderInformation.setPrice(resourceSales.getPrice());
        orderInformation.setActualNum(0.0);
        orderInformation.setActualPiece(0.0);
        orderInformation.setOrderExpirationTime(resourceSales.getSalesEndTime());
        orderInformation.setStatus( status );
        orderInformation.setWhereFrom( whereFrom );
        orderInformation.setCreateUserId( userId );
        orderInformation.setCreatTime( now );
        orderInformation.setConfirmUserId(0);
        orderInformation.setConfirmTime(now);
        orderInformationMapper.insert(orderInformation);
    }

    /**
     * @Description:生成新的订单ID
     * @Author: shizg
     * @Date: 2021/6/10 11:47
     * @param vid:
     * @param userId:
     * @return: java.lang.String
     **/
    private String get_order_ddid(Byte vid, Integer userId ) {
        String orderId = "DD";
        if ( vid == CommonConstant.LUOWEN_VID ) {
            orderId += "LW";
        }else if ( vid == CommonConstant.GAOXIAN_VID ){
            orderId += "GX";
        }else if ( vid == CommonConstant.PANLUO_VID ){
            orderId += "Pl";
        }
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmssSSS");
        orderId += ft.format(now)+userId;
        return orderId;
    }


}
