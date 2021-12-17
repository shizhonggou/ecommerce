package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
//import com.isechome.ecommerce.security.entity.AdminUserInfo;
//import com.isechome.ecommerce.security.entity.CompanyInfo;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.entity.InvoiceInformationManagement;
import com.isechome.ecommerce.mapper.ecommerce.InvoiceInformationManagementMapper;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class FpmanageService {
    @Autowired
    InvoiceInformationManagementMapper invoiceInformationManagementMapper;
    @Autowired
    CompanyInfoDetailMapper companyInfoMapper;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;

    // 查询个人信息
    public SysAdminuser userInfoDetai(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getId();
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.selectUserInfoDetail(id);
        return userDetai;
    }
    // 查询公司信息
    public SysCompany companyInfoDetai() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        SysCompany sysCompany = new SysCompany();
        sysCompany = sysCompanyMapper.companyInfoDetai(id);
        return sysCompany;
    }
    // 发票管理  查询所有发票
    public List<InvoiceInformationManagement> selectAllfp() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        List<InvoiceInformationManagement> fpList = invoiceInformationManagementMapper.selectAll(id);
        return  fpList;
    }
    // 修改
    public void fpUpdate(HttpServletRequest request) {
        Integer df = Integer.valueOf(request.getParameter("isdefault"));
        if(df == 1){
            SecuritySysUser session = SecurityUserUtil.getCurrentUser();
            Integer comid= session.getSysAdminUserInfo().getComid();
            Integer pmid = SecurityUserUtil.getPmid();
            Integer dfm = 0; 
            invoiceInformationManagementMapper.updatefpinfodf(comid,pmid,dfm);
        }
        invoiceInformationManagementMapper.updatefpinfo(Integer.valueOf(request.getParameter("id")),request.getParameter("rise"),request.getParameter("taxnum"),request.getParameter("addressMail"),request.getParameter("addressCompany"),request.getParameter("openBank"),request.getParameter("account"),request.getParameter("phone"),Integer.valueOf(request.getParameter("isdefault")),request.getParameter("invoiceCode"),request.getParameter("remark"));
    }

    // 删除
    public void fpDelete(HttpServletRequest request) {
        invoiceInformationManagementMapper.deleteByPrimaryKey(Integer.valueOf(request.getParameter("id")));
    }
    // 新增
    public void fpAddAction(HttpServletRequest request) throws ParseException {
        Integer df = Integer.valueOf(request.getParameter("isdefault"));
        if(df == 1){
            SecuritySysUser session = SecurityUserUtil.getCurrentUser();
            Integer comid= session.getSysAdminUserInfo().getComid();
            Integer pmid = SecurityUserUtil.getPmid();
            Integer dfm = 0; 
            invoiceInformationManagementMapper.updatefpinfodf(comid,pmid,dfm);
        }
        invoiceInformationManagementMapper.insertfpinfo(Integer.valueOf(request.getParameter("mid")),request.getParameter("rise"),request.getParameter("taxnum"),request.getParameter("addressMail"),request.getParameter("addressCompany"),request.getParameter("openBank"),request.getParameter("account"),request.getParameter("phone"),Integer.valueOf(request.getParameter("isdefault")),request.getParameter("invoiceCode"),request.getParameter("remark"),Integer.valueOf(request.getParameter("pmid")));
    }
    // 检查用户名
    public SysAdminuser checkUserName(String userName) {
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.checkUserName(userName);
        return userDetai;
    }
    // 检查手机号码
    public SysAdminuser checkMobile(String mobile) {
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.checkMobile(mobile);
        return userDetai;
    }
    public InvoiceInformationManagement fpDetail(HttpServletRequest request) {
        String id=request.getParameter("id");
        Integer id2= Integer.valueOf(id).intValue();
        InvoiceInformationManagement fpDetail = new InvoiceInformationManagement();
        fpDetail = invoiceInformationManagementMapper.selectByPrimaryKey(id2);
        return fpDetail;
    }



}
