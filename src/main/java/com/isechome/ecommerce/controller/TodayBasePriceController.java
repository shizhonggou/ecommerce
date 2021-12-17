package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.mapper.ecommerce.ScSteelMillMapper;
import com.isechome.ecommerce.service.TodayBasePriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/baseprice")
@Slf4j
/**
 * @Description: 基价维护管理
 * @Author: shizg
 * @Date: 2021/8/19 11:47
 * @param null:
 * @return: null
 **/
public class TodayBasePriceController {
    @Autowired
    private TodayBasePriceService todayBasePriceService;

    /**
     * @Description: 基价设置页面
     * @Author: shizg
     * @Date: 2021/8/19 11:57
     * @param request:
     * @param model:
     * @return: java.lang.String
     **/
    @RequestMapping("settings")
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERJG') )")
    public String showBasePrice( HttpServletRequest request, Model model ) {
        todayBasePriceService.showBasePrice( request, model );
        return "baseprice/settings";
    }

    /**
     * @Description:执行审核或保存
     * @Author: shizg
     * @Date: 2021/8/20 17:57
     * @return: java.lang.String
     **/
    @RequestMapping("saveprice")
    @ResponseBody
    public int savePrice( HttpServletRequest request ){
        return todayBasePriceService.savePrice( request );
    }

    /**
     * @Description: 更新今日资源价格
     * @Author: shizg
     * @Date: 2021/9/7 16:58
     * @param request:
     * @return: int
     **/
    @RequestMapping("updateprice")
    @ResponseBody
    public int updateResourcePrice( HttpServletRequest request ){
        return todayBasePriceService.updateResourcePrice( request );
    }
}
