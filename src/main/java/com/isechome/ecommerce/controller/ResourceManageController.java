/*
 * @Author: lina
 * @Date: 2021-05-31 10:30:20
 * @LastEditTime: 2021-06-26 10:19:36
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\ResourceManageController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isechome.ecommerce.common.DownloadUtil;
import com.isechome.ecommerce.common.ImportExcel;
import com.isechome.ecommerce.service.AdminUserInfoService;
import com.isechome.ecommerce.service.ResourceSalesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("resourcemanage")
public class ResourceManageController {

    @Autowired
    private ResourceSalesService resourceSalesService;
    @Autowired
    private AdminUserInfoService adminUserInfoService;

    // 资源导入
    @RequestMapping(value = "/doupexcel")
    // public void doupexcel(HttpServletRequest request,@RequestParam(value = "file")MultipartFile file,HttpServletResponse res) throws IOException {
    public String doupexcel(MultipartFile file, HttpServletResponse res) throws IOException {
        String errinfo="0";
        // common common=new common();
        // PrintWriter out=new PrintWriter(res.getOutputStream());	
        Boolean bool = ImportExcel.checkFile(file);
        if(!bool){
           errinfo = "-1";
        }
        // 工具类在下面
        HashMap<String, ArrayList<String[]>> hashMap = ImportExcel.analysisFile(file);
        ArrayList<String[]> arrayList = hashMap.get("OK");
        if(arrayList == null){
            Set<String> strings = hashMap.keySet();
            String next = strings.iterator().next();
            errinfo = next;
        }

        Integer insertMark = resourceSalesService.insertExcelList(arrayList);
        if (insertMark > 0) {
            errinfo = String.valueOf(insertMark);
        } 
        return errinfo;
        // res.setContentType("text/html;charset=UTF-8");
        // if(errinfo != ""){
        //     common.alert(errinfo, out);
        //     common.goBack(out);
        // } else {
        //     common.goURL("/resourcemanage/index?act=sh" ,out);
        // }
    }

    // 资源更新
    @RequestMapping(value = "/resourceupdate")
    public String resourceupdate(HttpServletRequest request, HttpServletResponse res) throws IOException{
        resourceSalesService.resourceupdate( request, res );
        return "1";
    }

    // 获取规格件重
    @RequestMapping(value = "/getWeight")
    public Float getWeight(HttpServletRequest request){
        Float weight = resourceSalesService.getWeight( request );
        return weight;
    }

    // 改变资源状态
    @RequestMapping(value = "/updateStatus")
    public String updateStatus(HttpServletRequest request) {
        resourceSalesService.updateStatus( request );
        return "1";
    }

    // 验交易密码
    @RequestMapping(value = "/checkPayWord")
    public String checkPayWord(HttpServletRequest request) {
        String inputPayWord = request.getParameter("inputPayWord");
        return adminUserInfoService.checkPayWord( inputPayWord );
    }

    // 下载资源模板（已作废，页面直接a链接）
    @RequestMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String path = DownloadUtil.getJarRootPath() + "\\download\\" + "zymb.xlsx";
        String fileName = "zymb.xlsx";
        DownloadUtil.downloadFile(path, fileName, response, request);
        System.out.println(path);
    }

}