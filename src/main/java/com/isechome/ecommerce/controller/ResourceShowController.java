package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.entity.ResourceSales;
import com.isechome.ecommerce.service.ResourceSalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/index")
public class ResourceShowController {
    @Autowired
    private ResourceSalesService resourceSalesService;

    /**
     * @Description:资源显示列表
     * @Author: shizg
     * @Date: 2021/5/31 11:29
     * @param request:
     * @param model:
     * @return: java.lang.String
     **/
    @RequestMapping({"/",""})
    public String resourceShow( HttpServletRequest request, Model model ){
        resourceSalesService.showResourceList( request, model );
        return "resource/show";
    }

}
