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
import com.isechome.ecommerce.entity.Scfahuolibs;
import com.isechome.ecommerce.mapper.ecommerce.ScfahuolibsMapper;
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
public class FhmanageService {
    @Autowired
    ScfahuolibsMapper scfahuolibsMapper;
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
    public List<Scfahuolibs> selectAllfh() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        List<Scfahuolibs> fhList = scfahuolibsMapper.selectAll(id);
        return  fhList;
    }
    // 修改
    public void fhUpdate(HttpServletRequest request) {
      
        SysCompany sysCompany = sysCompanyMapper.companyInfoDetai(Integer.valueOf(request.getParameter("companyname")));

       /* financialInfo.setCompany_id(sysCompany.getId());
        financialInfo.setCompany_name(sysCompany.getComname());*/
        scfahuolibsMapper.updatefhinfo(Integer.valueOf(request.getParameter("id")),Integer.valueOf(request.getParameter("companyname")),sysCompany.getComname(),request.getParameter("cangkuname"),request.getParameter("yunfei"),request.getParameter("remark"),request.getParameter("daozhanCangkuName"),request.getParameter("daozhanma"));
    }

    // 删除
    public void fhDelete(HttpServletRequest request) {
        scfahuolibsMapper.deleteByPrimaryKey(Integer.valueOf(request.getParameter("id")));
    }
    // 新增
    public void fhAddAction(HttpServletRequest request) throws ParseException {
        String daozhan = String.valueOf((int)((Math.random()*9+1)*100000));
        SysCompany sysCompany = sysCompanyMapper.companyInfoDetai(Integer.valueOf(request.getParameter("company_name")));
        scfahuolibsMapper.insertfhinfo(Integer.valueOf(request.getParameter("company_name")),sysCompany.getComname(),request.getParameter("cangkuname"),request.getParameter("yunfei"),request.getParameter("remark"),request.getParameter("daozhanCangkuName"),daozhan,Integer.valueOf(request.getParameter("pmid")));
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
    public Scfahuolibs fhDetail(HttpServletRequest request) {
        String id=request.getParameter("id");
        Integer id2= Integer.valueOf(id).intValue();
        Scfahuolibs fhDetail = new Scfahuolibs();
        fhDetail = scfahuolibsMapper.selectByPrimaryKey(id2);
        return fhDetail;
    }



}
