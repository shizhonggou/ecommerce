/*
 * @Author: lina
 * @Date: 2021-05-29 09:32:06
 * @LastEditTime: 2021-09-10 18:24:08
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\view\TranSet.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.view;

import javax.servlet.http.HttpServletRequest;

import com.isechome.ecommerce.service.TranSetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "transet")
public class TranSet {

    @Autowired
    private TranSetService tranSetService;

    //交易时间设置
    @RequestMapping(value = "/worktime") 
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
	public String worktime(HttpServletRequest request, Model model){    
        tranSetService.worktime(request, model);
        return "transet/worktime";
    }

    //调价录入界面
    @RequestMapping(value = "/modifyprice") 
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERJG') )")
	public String modifyprice(HttpServletRequest request, Model model){    
        tranSetService.modifyprice(request, model);
        return "transet/modifyprice";
    }

    //手动开盘停盘界面
    @RequestMapping(value = "/sysstatus") 
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
	public String sysstatus(HttpServletRequest request, Model model){    
        tranSetService.sysstatus(request, model);
        return "transet/sysstatus";
    }

}