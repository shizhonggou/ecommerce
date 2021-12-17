package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.entity.ScShoppingCartResoueces;
import com.isechome.ecommerce.service.ResourceSalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    private ModelAndView modelAndView;

    /**
     * @Description:加入购物车
     * @Author: shizg
     * @Date: 2021/6/8 15:30
     * @param request:
     * @return: java.util.List<com.isechome.ecommerce.entity.ResourceSales>
     **/
    @RequestMapping("add_purchase_car")
    //@PreAuthorize("hasRole('ROLE_GYLQX_YSH')")
    @ResponseBody
    public AjaxResult add_purchase_car(HttpServletRequest request ) {
        AjaxResult aResult = new AjaxResult();
        if(request.getParameter("is_pljr") != null && request.getParameter("is_pljr").equals("1")){
            if(!request.getParameter("resids").equals("")){
                String[] resid_arr = request.getParameter("resids").split(",");
                for (String resid : resid_arr) {
                    aResult = resourceSalesService.add_purchase_car( resid );
                }
            }
        }else{
            aResult = resourceSalesService.add_purchase_car( request.getParameter("resids") );
        }
        return aResult;
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
    public AjaxResult clear_purchase_car( HttpServletRequest request ) {
        resourceSalesService.del_purchase_car( request );
        return AjaxResult.success();
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
    public AjaxResult del_purchase_car( HttpServletRequest request ) {
       resourceSalesService.del_purchase_car( request );
       return AjaxResult.success();
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
    public AjaxResult update_purchase_car_num( HttpServletRequest request ) {
        resourceSalesService.update_purchase_car_num( request );
        return AjaxResult.success();
    }

    @RequestMapping(value = "mycart")
    public ModelAndView mycart(HttpServletRequest request) {
        modelAndView = resourceSalesService.getShoppingCartList(request);
        modelAndView.setViewName("purchase_car/mycart");
        return modelAndView;
    }

    @RequestMapping(value = "clean_mycart")
    @ResponseBody
    public AjaxResult clean_mycart(HttpServletRequest request) {
        resourceSalesService.del_purchase_car(request);
        return AjaxResult.success();
    }

    @RequestMapping(value = "create_order")
    @ResponseBody
    public AjaxResult create_order(HttpServletRequest request) {
        return resourceSalesService.create_order(request);
    }
}
