package com.isechome.ecommerce.controller;


import com.alibaba.excel.ExcelReader;
import com.alibaba.fastjson.JSONObject;
import com.isechome.ecommerce.service.ShipmentsImportService;
import com.isechome.ecommerce.util.ExcelUtil;
import com.isechome.ecommerce.entity.LogisticsPurchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Log4j2
@RequestMapping("shipmentsimport")
public class ShipmentsImportController {
    @Autowired
    private ShipmentsImportService shipmentsImportService;

    @RequestMapping("importExcel")
    @ResponseBody
    public String importExcel(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;
        int ret = 0;
        String s = "";
        try {
            in = multipartFile.getInputStream();
            ExcelUtil<LogisticsPurchase> util = new ExcelUtil<LogisticsPurchase>(LogisticsPurchase.class);
            List<LogisticsPurchase> shipments = util.importEasyExcel(in);

            //插入 更新仓库资源
            s = shipmentsImportService.importExcel(shipments);
            System.out.println("s:" + s);
            if(s.equals("-|-|-")){
                ret = 1;
            }
        } catch (Exception e) {
            log.error("import excel to db fail", e);
        } finally {
            in.close();
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        JSONObject userJson=new JSONObject();
        if(ret==1){
            userJson.put("Success","1");
            userJson.put("Message", "导入成功");
        } else {
            userJson.put("Success","0");
            if(s.equals("-")){
                userJson.put("Message", "导入失败");
            } else {
                String[] s_arr =  s.split("\\|");
                String msg = "";
                /*if(!s_arr[0].isEmpty() && !s_arr[1].isEmpty()){
                    msg = "提单号："+ s_arr[0].substring(0, s_arr[0].length()-1) + "未找到，提单号："+s_arr[1].substring(0, s_arr[1].length()-1)+"已提货，未导入，其他的数据导入成功";
                } else if(!s_arr[0].isEmpty() && s_arr[1].isEmpty()) {
                    msg = "提单号："+ s_arr[0].substring(0, s_arr[0].length()-1) + "未找到，未导入，其他的数据导入成功";
                } else if(s_arr[0].isEmpty() && !s_arr[1].isEmpty()) {
                    msg = "提单号："+s_arr[1].substring(0, s_arr[1].length()-1)+"已提货，未导入，其他的数据导入成功";
                } else {
                    msg = "导入成功";
                }*/
                if(!s_arr[0].equals("-")){
                    msg = msg + "提单号："+ s_arr[0].substring(0, s_arr[0].length()-1) + "未找到，";
                }
                if(!s_arr[1].equals("-")){
                    msg = msg + "提单号："+s_arr[1].substring(0, s_arr[1].length()-1)+"已提货，";
                }
                if(!s_arr[2].equals("-")){
                    msg = msg + "提单号："+s_arr[2].substring(0, s_arr[2].length()-1)+"提货数量错误，";
                }
                if(msg==""){
                    msg = "导入成功";
                } else {
                    msg = msg + "其他的数据导入成功";
                }

                userJson.put("Message", msg);
            }
        }
        return userJson.toString();
    }
}
