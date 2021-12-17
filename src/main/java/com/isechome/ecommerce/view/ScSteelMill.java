/*
 * @Author: lina
 * @Date: 2021-05-29 09:32:06
 * @LastEditTime: 2021-09-10 18:25:00
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\view\ScSteelMill.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.view;

import javax.servlet.http.HttpServletRequest;

import com.isechome.ecommerce.service.ScSteelMillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "scsteelmill")
public class ScSteelMill {
    
    @Autowired
    private ScSteelMillService scSteelMillService;
    
    //钢厂首页
    @RequestMapping(value = "/index") 
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
	public String index(HttpServletRequest request, Model model){    
        scSteelMillService.index(request, model);
        return "scsteelmill/index";
    }

    //钢厂编辑
    @RequestMapping(value = "/edit") 
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
	public String edit(HttpServletRequest request, Model model){    
        scSteelMillService.edit(request, model);
        return "scsteelmill/edit";
    }

}