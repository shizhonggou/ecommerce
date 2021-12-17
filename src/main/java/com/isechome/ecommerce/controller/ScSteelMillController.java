/*
 * @Author: lina
 * @Date: 2021-06-07 16:31:56
 * @LastEditTime: 2021-09-02 10:49:53
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\ScSteelMillController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.entity.ReturnObj;
import com.isechome.ecommerce.service.ScSteelMillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
@RequestMapping("scsteelmill")
public class ScSteelMillController {
    @Autowired
    private ScSteelMillService scSteelMillService;

    //保存修改钢厂信息
    @RequestMapping("/doedit")
    public void doedit( HttpServletRequest request, HttpServletResponse res) throws IOException {
        scSteelMillService.doedit( request );
        Common common=new Common();
        PrintWriter out=new PrintWriter(res.getOutputStream());	
        res.setContentType("text/html;charset=UTF-8");
        common.alert("保存成功", out);
        common.goURL("/scsteelmill/index" ,out);
    }

    //删除钢厂信息
    @RequestMapping("/del")
    public ReturnObj del( HttpServletRequest request) {
        ReturnObj returnObj = new ReturnObj();
        returnObj = scSteelMillService.del( request );
        return returnObj;
    }

}
