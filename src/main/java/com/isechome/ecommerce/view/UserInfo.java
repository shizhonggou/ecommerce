package com.isechome.ecommerce.view;

import com.alibaba.fastjson.JSONObject;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.security.entity.CompanyInfo;
import com.isechome.ecommerce.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

@Controller
@RequestMapping(value = "userInfo")
public class UserInfo {

    @Autowired
    UserInfoService userInfoService;

    // 查询个人信息
    @RequestMapping (value = "/userInfoDetail")
    public ModelAndView userInfo (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        AdminUserInfo userDetai = new AdminUserInfo();
        userDetai = userInfoService.userInfoDetai(request);
        modelAndView.addObject("userDetail", userDetai);
        String rights0 = "";
        String rights8 = "";
        String rights16 = "";
        if (userDetai.getRights() == 0) {
            rights0 = "0";
        }
        if ((userDetai.getRights() & 0x8) == 8) {
            rights8 = "8";
        }
        if ((userDetai.getRights() & 0x10) == 16) {
            rights16 = "16";
        }
        modelAndView.addObject("rights0",rights0);
        modelAndView.addObject("rights8",rights8);
        modelAndView.addObject("rights16",rights16);
        modelAndView.setViewName("userInfo/UserInfo");
        return modelAndView;
    }
    // 修改个人信息
    @RequestMapping (value = "/updateUserInfo", method = RequestMethod.POST)
    public void updateUserInfo (HttpServletRequest request, HttpServletResponse response) throws IOException {
        userInfoService.updateUserInfo(request);
        this.alert("修改成功", response);
        this.goBack(response);
        this.reload(response);
    }
    // 查询公司信息
    @RequestMapping (value = "/companyDetail")
    public ModelAndView companyInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companyDetail", userInfoService.companyInfoDetai());
        modelAndView.addObject("userLoginID",this.userLoginID());
        modelAndView.setViewName("userInfo/CompanyInfoDetail");
        return modelAndView;
    }
    // 修改公司信息
    @RequestMapping (value = "/updateCompanyInfo", method = RequestMethod.POST)
    public void updateCompanyInfo (HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file") MultipartFile file) throws IOException {
        userInfoService.updateCompanyInfo(request, file);
        this.alert("修改成功", response);
        this.goBack(response);
        this.reload(response);
    }
    // 账号管理  查询所有账号
    @RequestMapping (value = "/accountList")
    public ModelAndView accountList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("accountList",userInfoService.selectAllUser());
        modelAndView.addObject("zhuLianXiRenID",this.companyZhuLXRID());
        modelAndView.addObject("userLoginID",this.userLoginID());
        modelAndView.setViewName("userInfo/AccountManageListView");
        return modelAndView;
    }
    // 查询账号信息
    @RequestMapping (value = "/accountDetail")
    public ModelAndView accountDetail (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        AdminUserInfo userDetai = new AdminUserInfo();
        userDetai = userInfoService.accountDetai(request);
        modelAndView.addObject("userDetail",userDetai);
        String rights0 = "";
        String rights8 = "";
        String rights16 = "";
        if (userDetai.getRights() == 0) {
            rights0 = "0";
        }
        if ((userDetai.getRights() & 0x8) == 8) {
            rights8 = "8";
        }
        if ((userDetai.getRights() & 0x10) == 16) {
            rights16 = "16";
        }
        modelAndView.addObject("rights0",rights0);
        modelAndView.addObject("rights8",rights8);
        modelAndView.addObject("rights16",rights16);
        modelAndView.setViewName("userInfo/AccountUpdateView");
        return modelAndView;
    }
    // 账号修改
    @RequestMapping (value = "/accountUpdate")
    public void accountUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userInfoService.accountUpdate(request);
        this.alert("修改成功", response);
        this.goURL("/userInfo/accountDetail?id="+request.getParameter("id"), response);
    }
    // 账号修改  设置主联系人
    @RequestMapping (value = "/accountUpdateZhuLXR")
    public void updateCompanyInfoZhuLXR (HttpServletRequest request,HttpServletResponse response) throws IOException {
        userInfoService.updateCompanyInfoZhuLXR(request);
    }
    // 账号新增
    @RequestMapping (value = "/accountAdd")
    public ModelAndView accountAdd (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo/AccountAddView");
        return modelAndView;
    }
    // 账号 新增 保存
    @RequestMapping (value = "/accountAddAction")
    public void accountAddAction (HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        userInfoService.accountAddAction(request);
        this.alert("保存成功", response);
        this.goURL("/userInfo/accountList", response);
    }
    // 删除账号
    @RequestMapping (value = "/accountDelete")
    public void accountDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userInfoService.accountDelete(request);
        this.alert("删除成功", response);
        this.goURL("/userInfo/accountList", response);
    }
    // 检查用户名是否重复
    @RequestMapping (value = "/checkUName")
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        String username=request.getParameter("user_name");
        AdminUserInfo adminusr = userInfoService.checkUserName(username);
        String str="1";
        if(adminusr!=null) {
            //"用户名已存在";
            str = "0";
        } else {
            //"用户名可用";
            str = "1";
        }
        return str;
    }
    // 检查手机号 是否重复
    @RequestMapping (value = "/checkMobile")
    @ResponseBody
    public String checkMobile(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String mobile=request.getParameter("mobile");
        AdminUserInfo adminusr = userInfoService.checkMobile(mobile);
        String str="1";
        if(adminusr!=null) {
            //"手机号已存在";
            str = "0";
        } else {
            //"手机号可用";
            str = "1";
        }
        return str;
    }
    // 获取当前登录人 所在公司的 主联系人
    public Integer companyZhuLXRID () {
        CompanyInfo companyDetai = new CompanyInfo();
        companyDetai = userInfoService.companyInfoDetai();
        return companyDetai.getLink_user_id();
    }
    // 获取当前登录人的用户ID
    public Integer userLoginID () {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getAdminUserInfo().getId();
        return id;
    }

    // java后台向前台返回弹窗
    public void alert(String str,HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        String alt = "<script>alert('"+str+"');</script>";
        out.print(alt);
    }
    // //页面刷新
    public void reload(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html += "window.location.reload();";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }
    //后退 刷新
    public void goBack(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html+= "history.back();";
        //html+= "window.location.reload();";
        html += "</script>";
        out.print(html);
//        out.flush();
//        out.close();
    }
    //页面跳转
    public void goURL( String url,HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html += "window.location.href='"+ url +"'";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }



}
