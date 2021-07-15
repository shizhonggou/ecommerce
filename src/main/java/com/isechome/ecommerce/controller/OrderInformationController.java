/*
 * @Author: lina
 * @Date: 2021-06-07 16:31:56
 * @LastEditTime: 2021-06-24 10:48:17
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\OrderInformationController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.service.OrderInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("orderinformation")
public class OrderInformationController {
    @Autowired
    private OrderInformationService orderInformationService;

    @RequestMapping("/updateStatus")
    public String updateStatus( HttpServletRequest request ){
        Integer id = Integer.parseInt(request.getParameter("id"));
        Byte status = (byte)Integer.parseInt(request.getParameter("status"));
        // System.out.println("------------------------------------------------");
        // System.out.println(status);
        orderInformationService.updateStatus(id, status);
        return "1";
    }

    // 添加物流信息
    @RequestMapping("/addLogistics")
    public String addLogistics( HttpServletRequest request ){
        orderInformationService.addLogistics( request );
        return "1";
    }

    // 删除物流信息
    @RequestMapping("/delLogistics")
    public String delLogistics( HttpServletRequest request ){
        Integer id = Integer.parseInt(request.getParameter("id"));
        orderInformationService.delLogistics(id);
        return "1";
    }

    // 审核订单
    @RequestMapping("/orderShenhe")
    public String orderShenhe( HttpServletRequest request ){
        return orderInformationService.orderShenhe( request );
    }

    /**
     * @Description: 生成订单
     * @Author: shizg
     * @Date: 2021/6/10 8:49
     * @param request:
     * @return: java.lang.String
     **/
    @RequestMapping("/generate_order")
    public String generate_order ( HttpServletRequest request ) {
        return orderInformationService.generate_order( request );
    }

}
