package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ecommerce
 * @description: 公司管理
 * @author: Mr.shizg
 * @create: 2021-05-26 08:49
 **/

@RestController
@RequestMapping("company")
public class CompanyInfoController {
    @Autowired
    private CompanyInfoService companyInfoService;

    /***
     * 获取公司信息列表
     * @param
     * @return
     */
    // @RequestMapping("list")
    // public List<CompanyInfo> getCompanyInfo( ){
    //     //model.addAttribute("companyInfoList", companyInfoService.getCompanyInfo() );
    //     return companyInfoService.getCompanyInfo();
    //     //return "company/list";
    // }

    // @RequestMapping("/test")
    // public String test(){
    //     return "coming!!!";
    // }

    // @RequestMapping("add")
    // public String insertCompany(HttpServletRequest request ) {
    //     companyInfoService.saveCompanyInfo( request );
    //     return "";
    // }

}
