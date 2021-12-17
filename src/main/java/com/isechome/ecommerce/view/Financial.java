package com.isechome.ecommerce.view;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.BalanceInfo;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.FinancialService;
import com.isechome.ecommerce.service.InvoiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "financial")
@PreAuthorize("hasRole('ROLE_GYLQX_PTF') and (hasRole('ROLE_GYLQX_MASTERCW') or hasRole('ROLE_GYLQX_MASTER'))")
public class Financial {
    @Autowired
    FinancialService financialService;
    @Autowired
    InvoiceManagementService invoiceManagementService;

    // 财务录入信息页面
    @RequestMapping (value = "/financialInput")
    public ModelAndView financialInput (HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tmpcompay",invoiceManagementService.getSearchAll());
        modelAndView.setViewName("userInfo/financialInput");
        return modelAndView;
    }
    // 财务录入信息页面 保存
    @RequestMapping (value = "/financialInputSave")
    public void financialInputSave (HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        int res = financialService.financialInputSave(request);
        if (res == 0 || res == 3  || res == 4) {
            if (res == 4) {
                this.alert("请选择正确的公司名称",response);
            } else if (res == 3) {
                this.alert("退款失败，余额不足",response);
            } else {
                this.alert("录入失败",response);
            }
            this.goBack(response);
            this.reload(response);
        } else {
            this.alert("录入成功",response);
            this.goURL("/financial/financialAccount?page=1", response);
        }
    }
    // 财务出入账 列表
    @RequestMapping (value = "/financialAccount")
    public ModelAndView financialAccountList(HttpServletRequest request) {
        Integer page = Integer.valueOf(request.getParameter("page"));
        if (page < 1) {
            page = 1;
        }
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        ModelAndView modelAndView = new ModelAndView();
        List<FinancialInfo> accountList = financialService.financialAccountList(request);
        PageInfo<FinancialInfo> pageInfo = new PageInfo<>(accountList, CommonConstant.NAVIGATE_PAGES);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("accountList",accountList);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginID",isMain);
        modelAndView.setViewName("userInfo/financialAccountList");
        return modelAndView;
    }
    // 根据ID 删除 录入信息
    @RequestMapping (value = "deleteFinancialForID")
    public void deleteFinancialInfoForID(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int res = financialService.deleteFinancialForID(request);
        if (res == 0) {
            this.alert("删除失败",response);
        } else {
            this.alert("删除成功",response);
        }
        this.goBack(response);
        this.reload(response);
    }
    // 根据ID 查询 录入的财务信息
    @RequestMapping (value = "selecteFinancialInfo")
    public ModelAndView selecteFinancialInfoForID(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        FinancialInfo financialInfo = financialService.selecteFinancialInfoForID(request);
        modelAndView.addObject("financialInfo", financialInfo);
        modelAndView.setViewName("userInfo/financialAccountUpdate");
        return modelAndView;
    }
    // 根据ID 查询 录入的财务信息  更新保存
    @RequestMapping (value = "updateFinancialInfo")
    public void updateFinancialInfoForID(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        int res = financialService.updateFinancialInfoForID(request);
        if (res == 0) {
            this.alert("保存失败", response);
            this.goBack(response);
            this.reload(response);
        } else {
            this.alert("保存成功", response);
            this.goURL("/financial/financialAccount?page=1", response);
        }
    }

    //========================== 余额管理 列表 ===============
    @RequestMapping (value = "/balanceAccount")
    public ModelAndView selectBalanceAllList(HttpServletRequest request) {
        Integer page = Integer.valueOf(request.getParameter("page"));
        if (page < 1) {
            page = 1;
        }
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        ModelAndView modelAndView = new ModelAndView();
        List<BalanceInfo> accountList = financialService.selectBalanceAllList(request);
        PageInfo<BalanceInfo> pageInfo = new PageInfo<>(accountList, CommonConstant.NAVIGATE_PAGES);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("accountList",accountList);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Byte isMain= session.getSysAdminUserInfo().getIsmain();
        modelAndView.addObject("userLoginID",isMain);
        modelAndView.setViewName("userInfo/balanceAccountList");
        return modelAndView;
    }
    // 根据ID 删除 余额信息
    @RequestMapping (value = "deleteBalanceForID")
    public void deleteBalanceInfoForID(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int res = financialService.deleteBalanceForID(request);
        if (res == 0) {
            this.alert("删除失败",response);
        } else {
            this.alert("删除成功",response);
        }
        this.goBack(response);
        this.reload(response);
    }
    // 根据ID 查询 录入的余额信息
    @RequestMapping (value = "selecteBalanceInfo")
    public ModelAndView selecteBalanceInfoForID(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        BalanceInfo balanceInfo = financialService.selecteBalanceInfoForID(request);
        modelAndView.addObject("balanceInfo", balanceInfo);
        modelAndView.setViewName("userInfo/balanceAccountUpdate");
        return modelAndView;
    }
    // 根据ID 查询 录入的余额信息  更新保存
    @RequestMapping (value = "updateBalanceInfo")
    public void updateBalanceInfoForID(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        int res = financialService.updateBalanceInfoForID(request);
        if (res == 0) {
            this.alert("保存失败",response);
            this.goBack(response);
            this.reload(response);
        } else {
            this.alert("保存成功",response);
            this.goURL("/financial/balanceAccount?page=1", response);
        }
    }
    // 外部接口 根据公司company_id  平台方pmid 查询公司余额
    @RequestMapping (value = "selectCompaneyBalance")
    public Double selectCompaneyBalance(HttpServletRequest request) {
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo = financialService.selectCompaneyBalance(request);
        return balanceInfo.getBalance();
    }
    // 外部接口 根据公司company_id  平台方pmid    更新公司余额
    @RequestMapping (value = "updateCompaneyBalance")
    public String updateCompaneyBalance(HttpServletRequest request) throws ParseException {
        String str = financialService.updateCompaneyBalance(request);
        return str;
    }

    // java后台向前台返回弹窗
    public void alert(String str,HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        String alt = "<script>alert('"+str+"');</script>";
        out.print(alt);
    }
    // 页面刷新
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
