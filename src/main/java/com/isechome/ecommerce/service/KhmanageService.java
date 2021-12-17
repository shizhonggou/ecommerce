package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.util.FileUtil;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.entity.Smownmember;
import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;
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
public class KhmanageService {
    @Autowired
    SmownmemberMapper SmownmemberMapper;
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
    public List<Smownmember> selectAllkh() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        List<Smownmember> khList = SmownmemberMapper.selectAll2(id);
        return  khList;
    }
    public List<Smownmember> selectcomkh(Integer comid) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        List<Smownmember> khList = SmownmemberMapper.selectbycom(id,comid);
        return  khList;
    }
    // 修改
    public void khUpdate(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file) {
        String strPath = "";
        String filePath = "";
        if (file.isEmpty()){
            // 没选择文件
        } else {
            // 处理上传的文件
            try{
                byte[] filebytes = file.getBytes();
                String str = System.getProperty("user.dir")+"/src/main/resources/upload/";
                strPath =  System.getProperty("user.dir")+"/src/main/resources/upload/"+ file.getOriginalFilename();
                Path path = Paths.get(str + file.getOriginalFilename());
                Files.write(path,filebytes);
                 filePath = FileUtil.executeUpload(file);
                System.out.println(path);
            }catch(Exception e){
                e.printStackTrace();
                //  "上传失败";
            }
        }
        String credit_amount = request.getParameter("credit_amount");
        Integer member_type =Integer.valueOf(request.getParameter("member_type"));
        
        if(credit_amount == null || credit_amount.isEmpty() || member_type != 2 ){
            credit_amount = "0";
        }
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer reviewuid= session.getSysAdminUserInfo().getId();
        Integer reviewmid= session.getSysAdminUserInfo().getComid();
        SmownmemberMapper.updatekhinfo(Integer.valueOf(request.getParameter("id")),reviewuid,reviewmid,Integer.valueOf(request.getParameter("status")),request.getParameter("preferentialAmount"),Integer.valueOf(request.getParameter("member_type")),credit_amount,filePath);
    }

    // 删除
    public void khDelete(HttpServletRequest request) {
        SmownmemberMapper.deleteByPrimaryKey(Integer.valueOf(request.getParameter("id")));
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
    public Smownmember khDetail(HttpServletRequest request) {
        String id=request.getParameter("id");
        Integer id2= Integer.valueOf(id).intValue();
        Smownmember khDetail = new Smownmember();
        khDetail = SmownmemberMapper.selectByPrimaryKey(id2);
        return khDetail;
    }



}
