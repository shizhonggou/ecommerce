package com.isechome.ecommerce.view;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.constant.CommonConstant;
import java.util.List;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.InvoiceInformationManagement;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.mapper.ecommerce.ScSettingRangeMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.FpmanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "fpmanage")
public class Fpmanage {

    @Autowired
    FpmanageService fpmanageService;


    // 发票
    @RequestMapping (value = "/fpList")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH')")
    public ModelAndView fpList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<InvoiceInformationManagement> fpList = fpmanageService.selectAllfp();
        PageInfo<InvoiceInformationManagement> pageInfo = new PageInfo<>(fpList, CommonConstant.PAGE_SIZE);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("fpList",fpList);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginID",isMain);
        modelAndView.setViewName("fpmanage/fplistview");
        return modelAndView;
    }
    // 查询信息
    @RequestMapping (value = "/fpDetail")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH')")
    public ModelAndView fpDetail (HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        InvoiceInformationManagement fpetail=fpmanageService.fpDetail(request);
        modelAndView.addObject("fpdetail",fpetail);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("userID",session.getSysAdminUserInfo().getId());
        modelAndView.setViewName("fpmanage/fpUpdateView");
        return modelAndView;
    }
    // 修改
    @RequestMapping (value = "/fpUpdate")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH')")
    public void fpUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fpmanageService.fpUpdate(request);
        this.alert("修改成功", response);
        this.goURL("/fpmanage/fpDetail?id="+request.getParameter("id"), response);
    }
    // 新增
    @RequestMapping (value = "/fpAdd")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH')")
    public ModelAndView accountAdd (HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("COMID",session.getSysAdminUserInfo().getComid());
        modelAndView.setViewName("fpmanage/fpAddView");
        return modelAndView;
    }
    // 新增 保存
    @RequestMapping (value = "/fpAddAction")
    public void fpAddAction (HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        fpmanageService.fpAddAction(request);
        this.alert("保存成功", response);
        this.goURL("/fpmanage/fpList", response);
    }
    // 删除
    @RequestMapping (value = "/fpDelete")
    public void fpDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fpmanageService.fpDelete(request);
        this.alert("删除成功", response);
        this.goURL("/fpmanage/fpList", response);
    }
    // 检查用户名是否重复
    @RequestMapping (value = "/checkUName")
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        String username=request.getParameter("user_name");
        SysAdminuser adminusr = fpmanageService.checkUserName(username);
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
        SysAdminuser adminusr = fpmanageService.checkMobile(mobile);
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
        companyDetai = fpmanageService.companyInfoDetai();
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
