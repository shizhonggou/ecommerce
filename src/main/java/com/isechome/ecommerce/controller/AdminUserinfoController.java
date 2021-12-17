/*
 * @Author: lina
 * @Date: 2021-05-27 09:20:12
 * @LastEditTime: 2021-06-15 10:42:24
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\controller\AdminUserinfoController.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.controller;

import com.alibaba.fastjson.JSONObject;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.HttpUtils;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.Sysmobilecheckcode;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.service.SysAdminuserInfoService;
import com.isechome.ecommerce.service.SysmobilecheckcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: ecommerce
 * @description: 公司管理
 * @author: Mr.shizg
 * @create: 2021-05-26 08:49
 **/

@Controller

public class AdminUserinfoController {
    @Autowired
    private SysmobilecheckcodeService sysmobilecheckcodeService;
    @Autowired
    private SysAdminuserInfoService sysAdminuserInfoService;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;
    @Value("${smslogin.url}")
    public String url2;
    @Value("${smslogin.param}")
    public String param2;
//    @Autowired
//    UserSecurityService uss;
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
        SysAdminuser adminusr = sysAdminuserInfoService.getadminuserinfobymobile(tel);
        //System.out.println(adminusr+"11111111111111111151111");
          if(adminusr!=null)
          {
            String returnmessage="";
            String url = url2;
            String param=param2+tel;
            returnmessage= HttpUtils.sendGet(url, param);
            JSONObject userJson = JSONObject.parseObject(returnmessage);
            returnmessage=userJson.toString();
            return returnmessage;
          }
          else
          { 
            JSONObject userJson=new JSONObject();
            userJson.put("Success","0");
            userJson.put("Message", "手机号不存在,请先注册");
            return userJson.toString();
          }



    }
    
    @RequestMapping(value = "/smslogin") 
    @ResponseBody
    public String smslogin(HttpServletRequest request)
    {
       String tel=request.getParameter("tel");
       String mobileVcode=request.getParameter("mobileVcode");
       
       JSONObject userJson=new JSONObject();
       Sysmobilecheckcode smsinfo= sysmobilecheckcodeService.selectSysmobilecheckcodeByMobileNumber(tel);
       String errostr="";
       boolean telyz=true;
       Date now = Common.getNowDate();
       Date usedate= smsinfo.getUseddate();
      // System.out.println(smsinfo.getCheckcode().toString() +"+51111"); 
        if(tel.isEmpty())
        {
            errostr = "请输入验证码！";
            telyz = false;
        }
        else if(smsinfo == null)
        {
            errostr = "请获取验证码！";
            telyz = false;
        }
        else if(!mobileVcode.equals(smsinfo.getCheckcode()))
        {
            errostr = "验证码填写不正确，请重新输入！";
            telyz = false;
        }
        else if(now.after(usedate))
        {
            errostr = "验证码已过期，请重新获取！";
            telyz = false;
        }
        if(telyz)
        {
            
          SysAdminuser adminusr = sysAdminuserInfoService.getadminuserinfobymobile(tel);
          //System.out.println(adminusr+"11111111111111111151111");
            if(adminusr!=null)
            {
                //UserSecurityService uss=new UserSecurityService();
               // UserDetails ud= uss.loadUserByUsername(adminusr.getUser_name());
                errostr ="登录成功";
                userJson.put("username", adminusr.getAusername());
                userJson.put("password", adminusr.getApasswd());
            }
            else
            {   
                errostr = "账号不存在！";
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
    @RequestMapping(value = "/smslogin2") 
    @ResponseBody
    public String smslogin2(HttpServletRequest request)
    {
       String tel=request.getParameter("tel");
       String mobileVcode=request.getParameter("mobileVcode");
       String newpassword=MD5Util.encode(request.getParameter("newpassword")).toLowerCase();
       JSONObject userJson=new JSONObject();
       Sysmobilecheckcode smsinfo= sysmobilecheckcodeService.selectSysmobilecheckcodeByMobileNumber(tel);
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
            
          SysAdminuser adminusr = sysAdminuserInfoService.getadminuserinfobymobile(tel);
          //System.out.println(adminusr+"11111111111111111151111");
            if(adminusr!=null)
            {
                sysAdminuserMapper.pwdupd(adminusr.getId(),newpassword);
               // SysAdminuser adminusr2 = sysAdminuserInfoService.getadminuserinfobymobile(tel);
                //UserSecurityService uss=new UserSecurityService();
               // UserDetails ud= uss.loadUserByUsername(adminusr.getUser_name());
                errostr ="修改成功";
                userJson.put("username", adminusr.getAusername());
                userJson.put("password", newpassword);
            }
            else
            {   
                errostr = "账号不存在！";
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
        SysAdminuser adminusr = sysAdminuserInfoService.getadminuserinfobymobile(tel);
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
        SysAdminuser adminusr = sysAdminuserInfoService.getUserInfoByUsername2(username);        JSONObject userJson=new JSONObject();
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
