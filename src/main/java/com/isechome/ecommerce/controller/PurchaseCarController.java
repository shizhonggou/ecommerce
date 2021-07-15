package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.entity.ResourceSales;
import com.isechome.ecommerce.service.ResourceSalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@Slf4j
/**
 * @Description:前台资源显示
 * @Author: shizg
 * @Date: 2021/5/31 11:28
 * @param null:
 * @return: null
 **/
@RequestMapping("/purchase_car")
public class PurchaseCarController {
    @Autowired
    private ResourceSalesService resourceSalesService;

    /**
     * @Description:加入购物车
     * @Author: shizg
     * @Date: 2021/6/8 15:30
     * @param request:
     * @return: java.util.List<com.isechome.ecommerce.entity.ResourceSales>
     **/
    @RequestMapping("add_purchase_car")
    @ResponseBody
    public List<ResourceSales> add_purchase_car(HttpServletRequest request ) {
        List<ResourceSales> gwc = resourceSalesService.add_purchase_car( request );
        return gwc;
    }

    /**
     * @Description: 清空购物车
     * @Author: shizg
     * @Date: 2021/6/8 17:14
     * @param request:
     * @return: void
     **/
    @RequestMapping("clear_purchase_car")
    @ResponseBody
    public void clear_purchase_car( HttpServletRequest request ) {
        HttpSession session = request.getSession();
        session.setAttribute("purchase_car",null );
    }

    /**
     * @Description: 从购物车中删除信息
     * @Author: shizg
     * @Date: 2021/6/8 17:29
     * @param request:
     * @return: void
     **/
    @RequestMapping("del_purchase_car")
    @ResponseBody
    public Integer del_purchase_car( HttpServletRequest request ) {
       return resourceSalesService.del_purchase_car( request );
    }

    /**
     * @Description:更新购物车件数数量
     * @Author: shizg
     * @Date: 2021/6/9 17:06
     * @param request:
     * @return: void
     **/
    @RequestMapping("update_purchase_car_num")
    @ResponseBody
    public void update_purchase_car_num( HttpServletRequest request ) {
        resourceSalesService.update_purchase_car_num( request );
    }


}
