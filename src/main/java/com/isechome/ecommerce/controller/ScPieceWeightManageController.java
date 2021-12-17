/*
 * @Author: lina
 * @Date: 2021-06-07 16:31:56
 * @LastEditTime: 2021-08-23 09:26:43
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\ScPieceWeightManageController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.service.ScPieceWeightManageService;
import com.isechome.ecommerce.service.ScSteelMillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
@RequestMapping("scpieceweightmanage")
public class ScPieceWeightManageController {
    @Autowired
    private ScSteelMillService scSteelMillService;
    @Autowired
    private ScPieceWeightManageService scPieceWeightManageService;

    //保存修改件重信息
    @RequestMapping("/doedit")
    public void doedit( HttpServletRequest request, HttpServletResponse res) throws IOException {
        scPieceWeightManageService.doedit( request );
        Common common=new Common();
        PrintWriter out=new PrintWriter(res.getOutputStream());	
        res.setContentType("text/html;charset=UTF-8");
        common.alert("保存成功", out);
        common.goURL("/scpieceweightmanage/index" ,out);
    }

    //删除钢厂件重信息
    @RequestMapping("/del")
    public String del( HttpServletRequest request) {
        scPieceWeightManageService.del( request );
        return "1";
    }

}
