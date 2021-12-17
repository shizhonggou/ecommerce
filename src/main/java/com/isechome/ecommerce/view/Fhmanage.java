package com.isechome.ecommerce.view;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.Scfahuolibs;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.mapper.ecommerce.ScSettingRangeMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.FhmanageService;
import com.isechome.ecommerce.service.InvoiceManagementService;

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
@RequestMapping(value = "fhmanage")
public class Fhmanage {

    @Autowired 
    FhmanageService fhmanageService;
    @Autowired
    ScSettingRangeMapper scSettingRangeMapper;
    @Autowired
    InvoiceManagementService invoiceService;

    // 发票
    @RequestMapping (value = "/fhList")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH') ")
    public ModelAndView fhList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List <Scfahuolibs> scfahuolibs = fhmanageService.selectAllfh();
        modelAndView.addObject("fhList",scfahuolibs);
      //  modelAndView.addObject("fhList",fhmanageService.selectAllfh());
       PageInfo<Scfahuolibs> pageInfo = new PageInfo<>(scfahuolibs, CommonConstant.NAVIGATE_PAGES);
       modelAndView.addObject("pageInfo", pageInfo);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginID",isMain);
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("COMID",session.getSysAdminUserInfo().getComid());
        modelAndView.setViewName("fhmanage/fhlist");
        return modelAndView;
    }
    // 查询信息
    @RequestMapping (value = "/fhDetail")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH') ")
    public ModelAndView fhDetail (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Scfahuolibs fhetail=fhmanageService.fhDetail(request);
        modelAndView.addObject("fhdetail",fhetail);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("COMID",session.getSysAdminUserInfo().getComid());
        modelAndView.addObject("userID",session.getSysAdminUserInfo().getId());
        Byte kind = 5;
        modelAndView.addObject("cangkunamearr",scSettingRangeMapper.getckname(SecurityUserUtil.getPmid(),kind));
        modelAndView.addObject("tmpcompay", invoiceService.getSearchAll()) ;
        modelAndView.setViewName("fhmanage/fhUpdateView");
        return modelAndView;
    }
    // 修改
    @RequestMapping (value = "/fhUpdate")
    public void fhUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fhmanageService.fhUpdate(request);
        this.alert("修改成功", response);
        this.goURL("/fhmanage/fhDetail?id="+request.getParameter("id"), response);
    }
    // 新增
    @RequestMapping (value = "/fhAdd")
    @PreAuthorize("(hasAuthority('ROLE_GYLQX_PTF') and (hasAuthority('ROLE_GYLQX_MASTER') or hasAuthority('ROLE_GYLQX_MASTERXS'))) or hasAuthority('ROLE_KH') ")
    public ModelAndView accountAdd (HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pingTaiID",SecurityUserUtil.getPmid());
        modelAndView.addObject("COMID",session.getSysAdminUserInfo().getComid());
        Byte kind = 5;
        modelAndView.addObject("cangkunamearr",scSettingRangeMapper.getckname(SecurityUserUtil.getPmid(),kind));
        modelAndView.setViewName("fhmanage/fhAddView");
        modelAndView.addObject("tmpcompay", invoiceService.getSearchAll()) ;
        return modelAndView;
    }
    // 新增 保存
    @RequestMapping (value = "/fhAddAction")
    public void fhAddAction (HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        fhmanageService.fhAddAction(request);
        this.alert("保存成功", response);
        this.goURL("/fhmanage/fhList", response);
    }
    // 删除
    @RequestMapping (value = "/fhDelete")
    public void fhDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fhmanageService.fhDelete(request);
        this.alert("删除成功", response);
        this.goURL("/fhmanage/fhList", response);
    }
    // 检查用户名是否重复
    @RequestMapping (value = "/checkUName")
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        String username=request.getParameter("user_name");
        SysAdminuser adminusr = fhmanageService.checkUserName(username);
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
        SysAdminuser adminusr = fhmanageService.checkMobile(mobile);
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
        companyDetai = fhmanageService.companyInfoDetai();
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
