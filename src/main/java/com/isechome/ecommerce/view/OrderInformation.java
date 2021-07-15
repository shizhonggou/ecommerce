/*
 * @Author: lina
 * @Date: 2021-05-29 09:32:06
 * @LastEditTime: 2021-06-08 09:57:51
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\view\OrderInformation.java
 * @Description: 订单
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.view;

import javax.servlet.http.HttpServletRequest;

import com.isechome.ecommerce.service.OrderInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "orderinformation")
public class OrderInformation {

    @Autowired
    private OrderInformationService orderInformationService;

    // private PageInfo<ResourceSales> pageInfo;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model){    
        orderInformationService.index(request, model);
        return "orderInformation/index";
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, Model model){
        orderInformationService.detail(request, model);
        return "orderInformation/detail";
    }
}