package com.isechome.ecommerce.view;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.FinancialService;
import com.isechome.ecommerce.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.List;

@Controller
@RequestMapping(value = "userInfo")
public class UserInfo {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    FinancialService financialService;
    // 查询个人信息
    @RequestMapping (value = "/userInfoDetail")
    public ModelAndView userInfo (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = userInfoService.userInfoDetai(request);
        modelAndView.addObject("userDetail", userDetai);
        modelAndView.setViewName("userInfo/UserInfo");
        return modelAndView;
    }
    // 修改个人信息
    @RequestMapping (value = "/updateUserInfo", method = RequestMethod.POST)
    public void updateUserInfo (HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = userInfoService.updateUserInfo(request);
        if (res == 0) {
            this.alert("修改失败", response);
        } else {
            this.alert("修改成功", response);
        }
        this.goBack(response);
        this.reload(response);
    }
    // 查询公司信息
    @RequestMapping (value = "/companyDetail")
    public ModelAndView companyInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        SysCompany sysCompany = userInfoService.companyInfoDetai();
        modelAndView.addObject("companyDetail", sysCompany);
        if (sysCompany.getMchNo() != null && !sysCompany.getMchNo().equals("") && sysCompany.getMchNo().length() > 10) {
            modelAndView.addObject("companyUrl", true);
        } else {
            modelAndView.addObject("companyUrl", false);
        }
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        SysAdminuser sysAdminuser = userInfoService.selectUserInfoByID(session.getSysAdminUserInfo().getId());
        modelAndView.addObject("userLoginIsMain",sysAdminuser.getIsmain());
        BalanceInfo balanceInfo = financialService.selectCompaneyBalanceByCommID(session.getSysAdminUserInfo().getComid());
        Double balance = 0.0;
        if (balanceInfo != null) {
            balance = balanceInfo.getBalance();
        }
        modelAndView.addObject("balance",balance);
        Smownmember smownmember = userInfoService.selectByPmidAndMid();
        if (smownmember != null) {
            if (smownmember.getMember_type().equals(2)) {
                // 先货后款  有信用额度
                modelAndView.addObject("credit", smownmember.getCredit_amount());
                modelAndView.addObject("creditIsShow", 1);
            } else {
                modelAndView.addObject("creditIsShow", 0);
            }
        }
        // 注释掉  现在省份 城市 存汉字了
//        List<Province2Area> province2AreaList = userInfoService.selectAllProvince();
//        modelAndView.addObject("province2AreaList",province2AreaList);
        modelAndView.setViewName("userInfo/CompanyInfoDetail");
        return modelAndView;
    }

    @RequestMapping (value = "/companyDetail2")
    public ModelAndView companyInfo2(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companyDetail", userInfoService.companyInfoDetai2(request));
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginIsMain",isMain);
        BalanceInfo balanceInfo = financialService.selectCompaneyBalanceByCommID(session.getSysAdminUserInfo().getComid());
        Double balance = 0.0;
        if (balanceInfo != null) {
            balance = balanceInfo.getBalance();
        }
        modelAndView.addObject("balance",balance);
        modelAndView.setViewName("userInfo/CompanyInfoDetail2");
        return modelAndView;
    }
    // 修改公司信息
    @RequestMapping (value = "/updateCompanyInfo", method = RequestMethod.POST)
    public void updateCompanyInfo (HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file") MultipartFile file) throws IOException {
        int res = userInfoService.updateCompanyInfo(request, file);
        if (res == 0) {
            this.alert("修改失败", response);
        } else {
            this.alert("修改成功", response);
        }
        this.goBack(response);
        this.reload(response);
    }
    // 账号管理  查询所有账号
    @RequestMapping (value = "/accountList")
    public ModelAndView accountList(HttpServletRequest request) {
        Integer page = Integer.valueOf(request.getParameter("page"));
        if (page < 1) {
            page = 1;
        }
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        ModelAndView modelAndView = new ModelAndView();
        List<SysAdminuser> accountList = userInfoService.selectAllUser();
        PageInfo<SysAdminuser> pageInfo = new PageInfo<>(accountList, CommonConstant.NAVIGATE_PAGES);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("accountList",accountList);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        SysAdminuser sysAdminuser = userInfoService.selectUserInfoByID(session.getSysAdminUserInfo().getId());
        modelAndView.addObject("userLoginID",sysAdminuser.getIsmain());
        modelAndView.setViewName("userInfo/AccountManageListView");
        return modelAndView;
    }
    // 查询账号信息
    @RequestMapping (value = "/accountDetail")
    public ModelAndView accountDetail (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = userInfoService.accountDetai(request);
        modelAndView.addObject("userDetail",userDetai);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer ptmain = 0;// 平台主账号 = 1
        if (session.getSysAdminUserInfo().getComid().equals(SecurityUserUtil.getPmid()) && Integer.valueOf(session.getSysAdminUserInfo().getIsmain()).equals(1)) {
            ptmain = 1;
        } else {
            ptmain = 0;
        }
        modelAndView.addObject("userCommID",ptmain);
        if (ptmain.equals(1)) {
            // 平台方账号     权限  价格管理、销售管理、采购管理、库存管理、财务管理、采购审核
            String rights4 = "";
            String rights8 = "";
            String rights16 = "";
            String rights32 = "";
            String rights64 = "";
            String rights128 = "";
            if ((userDetai.getRights()&4) == 4) {
                rights4 = "4";
            }
            if ((userDetai.getRights()&8) == 8) {
                rights8 = "8";
            }
            if ((userDetai.getRights()&16) == 16) {
                rights16 = "16";
            }
            if ((userDetai.getRights()&32) == 32) {
                rights32 = "32";
            }
            if ((userDetai.getRights()&64) == 64) {
                rights64 = "64";
            }
            if ((userDetai.getRights()&128) == 128) {
                rights128 = "128";
            }
            modelAndView.addObject("rights4",rights4);
            modelAndView.addObject("rights8",rights8);
            modelAndView.addObject("rights16",rights16);
            modelAndView.addObject("rights32",rights32);
            modelAndView.addObject("rights64",rights64);
            modelAndView.addObject("rights128",rights128);
        }
        modelAndView.setViewName("userInfo/AccountUpdateView");
        return modelAndView;
    }
    // 账号修改
    @RequestMapping (value = "/accountUpdate")
    public void accountUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = userInfoService.accountUpdate(request);
        if (res == 0) {
            this.alert("修改失败", response);
        } else {
            this.alert("修改成功", response);
        }
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
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        Integer ptmain = 0;// 平台主账号 = 1
        if (session.getSysAdminUserInfo().getComid().equals(SecurityUserUtil.getPmid()) && Integer.valueOf(session.getSysAdminUserInfo().getIsmain()).equals(1)) {
            ptmain = 1;
        } else {
            ptmain = 0;
        }
        modelAndView.addObject("userCommID",ptmain);
        modelAndView.setViewName("userInfo/AccountAddView");
        return modelAndView;
    }
    // 账号 新增 保存
    @RequestMapping (value = "/accountAddAction")
    public void accountAddAction (HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        int res = userInfoService.accountAddAction(request);
        if (res == 0) {
            this.alert("保存失败", response);
            this.goBack(response);
        } else {
            this.alert("保存成功", response);
            this.goURL("/userInfo/accountList?page=1", response);
        }

    }
    // 删除账号
    @RequestMapping (value = "/accountDelete")
    public void accountDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = userInfoService.accountDelete(request);
        if (res == 0) {
            this.alert("删除失败", response);
        } else {
            this.alert("删除成功", response);
        }
        this.goURL("/userInfo/accountList?page=1", response);
    }
    // 检查用户名是否重复
    @RequestMapping (value = "/checkUName")
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        String username=request.getParameter("user_name");
        SysAdminuser adminusr = userInfoService.checkUserName(username);
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
        SysAdminuser adminusr = userInfoService.checkMobile(mobile);
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
        companyDetai = userInfoService.companyInfoDetai();
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
