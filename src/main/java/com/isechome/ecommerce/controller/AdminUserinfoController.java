/*
 * @Author: lina
 * @Date: 2021-05-27 09:20:12
 * @LastEditTime: 2021-06-15 10:42:24
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\AdminUserinfoController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.HttpUtils;
import com.isechome.ecommerce.entity.AdminUserInfo;
import com.isechome.ecommerce.service.AdminUserInfoService;
import com.isechome.ecommerce.service.SmsCheckCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import org.springframework.security.core.userdetails.UserDetails;
import com.isechome.ecommerce.entity.SmsCheckCode;
import com.isechome.ecommerce.security.UserSecurityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: ecommerce
 * @description: 公司管理
 * @author: Mr.shizg
 * @create: 2021-05-26 08:49
 **/

@Controller

public class AdminUserinfoController {
    @Autowired
    private SmsCheckCodeService smsCheckCodeService;
    @Autowired
    private AdminUserInfoService adminuserService;
    @Autowired
    UserSecurityService uss;
    /***
     * 获取公司信息列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getsmscode") 
    @ResponseBody
    public String getsmscode(HttpServletRequest request)
    { 
        String tel=request.getParameter("tel");
        String returnmessage="";
        String url = "http://mgdev.isechome.com/mg_user.php";
        String param="action=ajaxsendcheckcode3&mobile="+tel;
        returnmessage= HttpUtils.sendGet(url, param);
        JSONObject userJson = JSONObject.parseObject(returnmessage);
        returnmessage=userJson.toString();
        return returnmessage;

    }
    
    @RequestMapping(value = "/smslogin") 
    @ResponseBody
    public String smslogin(HttpServletRequest request)
    {
       String tel=request.getParameter("tel");
       String mobileVcode=request.getParameter("mobileVcode");
       
       JSONObject userJson=new JSONObject();
       SmsCheckCode smsinfo= smsCheckCodeService.selectSmsCheckCodeByMobileNumber(tel);
       String errostr="";
       boolean telyz=true;
      // System.out.println(smsinfo.getCheckcode().toString() +"+51111"); 
        if(tel.isEmpty())
        {
            errostr = "请输入验证码！";
            telyz = false;
        }
        else if(smsinfo==null)
        {
            errostr = "请获取验证码！";
            telyz = false;
        }
        else if(!mobileVcode.equals(smsinfo.getCheckcode()))
        {
            errostr = "验证码填写不正确，请重新输入！";
            telyz = false;
        }

        if(telyz)
        {
            
          AdminUserInfo adminusr=adminuserService.getadminuserinfobymobile(tel);
          //System.out.println(adminusr+"11111111111111111151111");
            if(adminusr!=null)
            {
                //UserSecurityService uss=new UserSecurityService();
               // UserDetails ud= uss.loadUserByUsername(adminusr.getUser_name());
                errostr ="登录成功";
                userJson.put("username", adminusr.getUser_name());
                userJson.put("password", adminusr.getPassword());
            }
            else
            {   
                errostr = "验证码填写不正确，请重新输入2！";
                telyz = false;
            }
            //select username from user_union where mobile='".$tel."'";
        }
//System.out.println( mobileVcode +"11111111111111111151111");

        userJson.put("Success", telyz?"1":"0");
        userJson.put("Message", errostr);

        System.out.println(userJson.toString());
        return userJson.toString();


    }
    @RequestMapping(value = "/checktel") 
    @ResponseBody
    public String checktel(HttpServletRequest request)
    { 
        String tel=request.getParameter("tel");
        AdminUserInfo adminusr=adminuserService.getadminuserinfobymobile(tel);
        JSONObject userJson=new JSONObject();
        String errostr="";
        if(adminusr!=null)
        {
            errostr ="手机号已存在";
            userJson.put("Message", errostr);
            userJson.put("iscz", 1);
        }
        else{
            userJson.put("iscz", 0);  
        }

        System.out.println(userJson.toString());
        return userJson.toString();
    }
    @RequestMapping(value = "/checkusername") 
    @ResponseBody
    public String checkusername(HttpServletRequest request)
    { 
        String username=request.getParameter("username");
        AdminUserInfo adminusr=adminuserService.getUserInfoByUsername2(username);
        JSONObject userJson=new JSONObject();
        String errostr="";
        if(adminusr!=null)
        {
            errostr ="用户名已存在";
            userJson.put("Message", errostr);
            userJson.put("iscz", 1);
           
        }
        else{
            errostr ="用户名可用";
            userJson.put("Message", errostr);
            userJson.put("iscz", 0);

        }
        System.out.println(userJson.toString());
        return userJson.toString();
    }


}
