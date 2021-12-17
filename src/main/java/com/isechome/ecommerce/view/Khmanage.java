package com.isechome.ecommerce.view;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.constant.CommonConstant;
import java.util.List;
import com.isechome.ecommerce.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.entity.Smownmember;
import com.isechome.ecommerce.service.InvoiceManagementService;
import com.isechome.ecommerce.service.KhmanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

@Controller
@RequestMapping(value = "khmanage")
public class Khmanage {

    @Autowired
    KhmanageService khmanageService;
    @Autowired
    InvoiceManagementService invoiceService;
   
    @RequestMapping (value = "/khList")
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
    public ModelAndView khList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<Smownmember> khList = khmanageService.selectAllkh();
        modelAndView.addObject("khList",khList);
        PageInfo<Smownmember> pageInfo = new PageInfo<>(khList, CommonConstant.NAVIGATE_PAGES);
        modelAndView.addObject("pageInfo", pageInfo);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer pmid = SecurityUserUtil.getPmid();
        modelAndView.addObject("pmid",pmid);
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginID",isMain);
        modelAndView.setViewName("khmanage/khlist");
        modelAndView.addObject("tmpcompay", invoiceService.getSearchAll()) ;
        return modelAndView;
    }
    @RequestMapping (value = "/khList2")
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
    public ModelAndView khList2 (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String khname=request.getParameter("comname");
        List<Smownmember> khList = khmanageService.selectcomkh(Integer.valueOf(request.getParameter("comname")));
        modelAndView.addObject("khList",khList);
        PageInfo<Smownmember> pageInfo = new PageInfo<>(khList, CommonConstant.NAVIGATE_PAGES);
        modelAndView.addObject("pageInfo", pageInfo);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer pmid = SecurityUserUtil.getPmid();
        modelAndView.addObject("pmid",pmid);
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginID",isMain);
        modelAndView.setViewName("khmanage/khlist");
        modelAndView.addObject("tmpcompay", invoiceService.getSearchAll()) ;
        return modelAndView;
    }



    // 查询信息
    @RequestMapping (value = "/khDetail")
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
    public ModelAndView khDetail (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Smownmember khetail=khmanageService.khDetail(request);
        modelAndView.addObject("khdetail",khetail);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("userID",session.getSysAdminUserInfo().getId());
        modelAndView.setViewName("khmanage/khUpdateView");
        return modelAndView;
    }
    // 修改
    @RequestMapping (value = "/khUpdate")
    public void khUpdate(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file") MultipartFile file) throws IOException {
        khmanageService.khUpdate(request,file);
        this.alert("修改成功", response);
        this.goURL("/khmanage/khDetail?id="+request.getParameter("id"), response);
    }
    // 新增
    @RequestMapping (value = "/khAdd")
    @PreAuthorize("hasRole('ROLE_GYLQX_PTF') and ( hasRole('ROLE_GYLQX_MASTER') or hasRole('ROLE_GYLQX_MASTERXS') )")
    public ModelAndView accountAdd (HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("COMID",session.getSysAdminUserInfo().getComid());
        modelAndView.setViewName("khmanage/khAddView");
        return modelAndView;
    }
    // 检查用户名是否重复
    @RequestMapping (value = "/checkUName")
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        String username=request.getParameter("user_name");
        SysAdminuser adminusr = khmanageService.checkUserName(username);
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
        SysAdminuser adminusr = khmanageService.checkMobile(mobile);
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
        SysCompany companyDetai = new SysCompany();
        companyDetai = khmanageService.companyInfoDetai();
        return companyDetai.getMemberid();
    }
    // 获取当前登录人的用户ID
    public Integer userLoginID () {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getId();
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
