package com.isechome.ecommerce.controller;

import java.util.Map;

import com.isechome.ecommerce.service.InvoiceManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.isechome.ecommerce.view.InvoiceManagement;

@Controller
@RequestMapping("invoicemanagement")
public class InvoiceManagementController  {
    @Autowired
    InvoiceManagementService invoiceService;
    
    @RequestMapping("OrderDetailPdf")
    public ModelAndView printPdf(Integer id,String Orderids) throws Exception{
        Map<String, Object> model=invoiceService.getPdfinfo(id,Orderids);
        System.out.println("!!!!!!!!!!!");
        InvoiceManagement invoiceManagement=new InvoiceManagement();
        return new ModelAndView(invoiceManagement, model);
    }
}
