package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.service.ScSettingRangeService;
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
@RequestMapping("/settings")
@Slf4j
/**
 * @Description: 极差维护管理
 * @Author: shizg
 * @Date: 2021/8/21 16:29
 * @param null:
 * @return: null
 **/
public class ScSettingRangeController {
    @Autowired
    private ScSettingRangeService scSettingRangeService;

    /**
     * @Description: 极差设置页面
     * @Author: shizg
     * @Date: 2021/8/21 16:33
     * @param request:
     * @param model:
     * @return: java.lang.String
     **/
    @RequestMapping("range")
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERJG') )")
    public String showSettingsRange( HttpServletRequest request, Model model ) {
        scSettingRangeService.showSettingsRange( request, model );
        return "settings/range";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "coming!!!";
    }

    /**
     * @Description:执行审核或保存
     * @Author: shizg
     * @Date: 2021/8/21 17:57
     * @return: java.lang.String
     **/
    @RequestMapping("saverange")
    @ResponseBody
    public int saveRange( HttpServletRequest request ){
        return scSettingRangeService.saveRange( request );
    }


}
