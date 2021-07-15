/*
 * @Author: lina
 * @Date: 2021-05-29 09:32:06
 * @LastEditTime: 2021-06-17 14:29:30
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\view\ResourceManage.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.entity.ResourceSales;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.ResourceSalesService;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "resourcemanage")
public class ResourceManage {

    @Autowired
    private ResourceSalesService resourceSalesService;

    private PageInfo<ResourceSales> pageInfo;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,@RequestParam(value = "page", required = false) Integer page, Model model){  
        // System.out.println("当前时间为:"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        int pageNum = 1;
        if("0".equals(String.valueOf(page)) || "null".equals(String.valueOf(page)) || page <= 0){
            //pageNum = 3;
        }else{
            pageNum = page;
        }
        int limit = CommonConstant.PAGE_SIZE;
        PageHelper.startPage(pageNum, limit);

        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        Integer mid = loginMessage.getCompanyInfo().getId();
        String act = request.getParameter("act");
        String varietyName = request.getParameter("varietyName");
        String Material = request.getParameter("Material");
        String Spec = request.getParameter("Spec");
        String origin_code = request.getParameter("origin_code");
        if(varietyName == null){
            varietyName = "";
        }
        if(Material == null){
            Material = "";
        }
        if(Spec == null){
            Spec = "";
        }
        if(origin_code == null){
            origin_code = "";
        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("varietyName",varietyName);
        map.put("Material",Material);
        map.put("Spec",Spec);
        map.put("origin_code",origin_code);
        if (act.equals("sh")) {
            map.put("status",1);
        } else if (act.equals("sj")) {
            map.put("status",2);
        } else if (act.equals("xj")) {
            map.put("status",3);
            resourceSalesService.updateStatusByEndTime();
        }
        map.put("mid",mid);
        List<ResourceSales> resourceSales = new ArrayList<>();
        resourceSales = resourceSalesService.selectAll(map);
        pageInfo = new PageInfo<ResourceSales>(resourceSales);
        model.addAttribute("varietyName", varietyName);
        model.addAttribute("Material", Material);
        model.addAttribute("Spec", Spec);
        model.addAttribute("origin_code", origin_code);
        model.addAttribute("resourceSales", resourceSales);
        model.addAttribute("act", act);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("page", page);
        return "resourceManage/index";
    }

    //资源发布
    @RequestMapping(value = "/upexcel") 
	public String  upexcel(Model model,HttpServletRequest request,HttpServletResponse res)throws IOException  {
        return "resourceManage/upexcel";
    }

}