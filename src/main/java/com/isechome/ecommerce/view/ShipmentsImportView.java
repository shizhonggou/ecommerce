package com.isechome.ecommerce.view;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.isechome.ecommerce.service.ShipmentsImportService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("shipmentsimport")
public class ShipmentsImportView {
    @Autowired
    ShipmentsImportService shipmentsImportService;

    @PreAuthorize("hasRole('ROLE_GYLQX_MASTERKC') or (hasRole('ROLE_GYLQX_MASTER') and hasRole('ROLE_GYLQX_PTF'))")
    @RequestMapping("index")
    public String index(HttpServletRequest request,Model model) {
        model.addAttribute("mark", "shipmentsimport");
        return "shipmentsimport/index";
    }
}
