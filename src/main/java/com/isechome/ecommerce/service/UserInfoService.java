package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.FinancialMapper;
import com.isechome.ecommerce.mapper.isechome.*;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.mapper.ecommerce.UserInfoMapper;
import com.isechome.ecommerce.util.FileUtil;
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
public class UserInfoService {

    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;
    @Autowired
    Province2AreaMapper province2AreaMapper;
    @Autowired
    SmownmemberMapper smownmemberMapper;

    // 查询个人信息
    public SysAdminuser userInfoDetai(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getId();
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.selectUserInfoDetail(id);
        return userDetai;
    }
    //    更新个人信息
    public int updateUserInfo(HttpServletRequest request) {
        String pwd = "";
        String pay_pwd = "";
        if (request.getParameter("password_old").equals(request.getParameter("pwd_input"))) {
            pwd = request.getParameter("password_old");
        } else {
            pwd = MD5Util.encode(request.getParameter("pwd_input"));
        }
        if (request.getParameter("pay_password").equals(request.getParameter("pay_password_old"))) {
            pay_pwd = request.getParameter("pay_password_old");
        } else {
            pay_pwd = MD5Util.encode(request.getParameter("pay_password"));
        }
        int res = sysAdminuserMapper.updateUserInfo(Integer.valueOf(request.getParameter("id")), request.getParameter("real_name"), request.getParameter("user_name"),pwd, pay_pwd,request.getParameter("mobile").replace(" ",""), request.getParameter("idcard"),request.getParameter("email"), request.getParameter("job"),Byte.valueOf(request.getParameter("sex")));
        if (res != 0) {
            // 修改交易密码之后 修改一下缓存的交易密码
            SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
            securitySysUser.getSysAdminUserInfo().setTradepwd(pay_pwd);
        }
        return res;
    }
    // 查询公司信息
    public SysCompany companyInfoDetai() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        SysCompany sysCompany = new SysCompany();
        sysCompany = sysCompanyMapper.companyInfoDetai(id);
//        Province2Area province2Area = province2AreaMapper.selectByProviceId(Integer.valueOf(sysCompany.getState()));
//        sysCompany.setState(province2Area.getProvicename());
        return sysCompany;
    }
    // 获取省份
    public List<Province2Area> selectAllProvince() {
        List<Province2Area> list = province2AreaMapper.selectAll();
        return list;
    }
    // 获取 信用额度
    public Smownmember selectByPmidAndMid() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Smownmember smownmember = smownmemberMapper.selectByPmidAndMid(SecurityUserUtil.getPmid(),session.getSysAdminUserInfo().getComid());
        return smownmember;
    }
        // 查询公司信息
        public SysCompany companyInfoDetai2(HttpServletRequest request) {
            Integer id= Integer.valueOf(request.getParameter("comid"));
            SysCompany sysCompany = new SysCompany();
            sysCompany = sysCompanyMapper.companyInfoDetai(id);
            Province2Area province2Area = province2AreaMapper.selectByProviceId(Integer.valueOf(sysCompany.getState()));
            sysCompany.setState(province2Area.getProvicename());
            return sysCompany;
        }

    // 修改公司信息
    public int updateCompanyInfo(HttpServletRequest request ,@RequestParam(value = "file") MultipartFile file) {
        String strPath = "";
        if (file.isEmpty()){
            // 没选择文件
        } else {
            // 处理上传的文件
            try{
//                byte[] filebytes = file.getBytes();
//                String str = System.getProperty("user.dir")+"/src/main/resources/upload/";
//                strPath =  System.getProperty("user.dir")+"/src/main/resources/upload/"+ file.getOriginalFilename();
//                Path path = Paths.get(str + file.getOriginalFilename());
//                Files.write(path,filebytes);
                strPath = FileUtil.executeUpload(file);
            }catch(Exception e){
                e.printStackTrace();
                //  "上传失败";
            }
        }
        int res = sysCompanyMapper.updateCompanyInfoDetail(Integer.valueOf(request.getParameter("id")), request.getParameter("com_name"),request.getParameter("com_name_short"),request.getParameter("com_desc"), request.getParameter("address_state"),request.getParameter("address_city"),request.getParameter("address"),strPath);
        return res;
    }
    // 账号管理  查询所有账号
    public List<SysAdminuser> selectAllUser() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        List<SysAdminuser> userList = sysAdminuserMapper.selectAllUser(id);
        return  userList;
    }
    // 账号管理 修改
    public SysAdminuser accountDetai(HttpServletRequest request) {
        Integer id= Integer.valueOf(request.getParameter("id"));
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.selectUserInfoDetail(id);
        return userDetai;
    }
    // 账号修改
    public int accountUpdate(HttpServletRequest request) {
        Integer qx = 0;
        //获取选中的权限 价格管理、销售管理、采购管理、库存管理、财务管理
        String[] args=request.getParameterValues("quanxian");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                qx += Integer.valueOf(args[i]);
            }
        }
        String pwd = "";
        String pay_pwd = "";
        if (request.getParameter("password_old").equals(request.getParameter("pwd_input"))) {
            pwd = request.getParameter("password_old");
        } else {
            pwd = MD5Util.encode(request.getParameter("pwd_input"));
        }
        if (request.getParameter("pay_password").equals(request.getParameter("pay_password_old"))) {
            pay_pwd = request.getParameter("pay_password_old");
        } else {
            pay_pwd = MD5Util.encode(request.getParameter("pay_password"));
        }
        int res = sysAdminuserMapper.accountUpdate(Integer.valueOf(request.getParameter("id")), request.getParameter("real_name"), request.getParameter("user_name"),pwd, pay_pwd,request.getParameter("mobile").replace(" ",""), request.getParameter("idcard"),request.getParameter("email"), request.getParameter("job"),Integer.valueOf(request.getParameter("sex")), qx);
        return res;
    }
    // 修改设置主联系人
    public void updateCompanyInfoZhuLXR(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id = session.getSysAdminUserInfo().getId();
        sysAdminuserMapper.updateZhuLianXiRen(id,0);
        sysAdminuserMapper.updateZhuLianXiRen(Integer.valueOf(request.getParameter("link_user_id")),1);
    }
    // 账号删除
    public int accountDelete(HttpServletRequest request) {
        int res = sysAdminuserMapper.accountDelete(Integer.valueOf(request.getParameter("id")));
        return res;
    }
    // 新增账号
    public int accountAddAction(HttpServletRequest request) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        SysAdminuser sysAdminuser = new SysAdminuser();
        sysAdminuser.setArealname(request.getParameter("real_name"));
        sysAdminuser.setAnickname("nick_name");
        sysAdminuser.setAusername(request.getParameter("user_name"));
        sysAdminuser.setApasswd(MD5Util.encode(request.getParameter("pwd_input")));
        sysAdminuser.setTradepwd(MD5Util.encode(request.getParameter("pay_password")));
        sysAdminuser.setMobile(request.getParameter("mobile").replace(" ",""));
        sysAdminuser.setIdnum(request.getParameter("idcard").replace(" ",""));
        sysAdminuser.setEmail(request.getParameter("email"));
        sysAdminuser.setJob(request.getParameter("job"));
        sysAdminuser.setSex(Byte.valueOf(request.getParameter("sex")));
        sysAdminuser.setAddress("address_user");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());
        sysAdminuser.setCreatedate(df.parse(dateStr));
        // 权限设置
        Integer qx = 0;
        String[] args=request.getParameterValues("quanxian");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                qx += Integer.valueOf(args[i]);
            }
        }
        sysAdminuser.setRights(qx.longValue());
        sysAdminuser.setComid(session.getSysAdminUserInfo().getComid());
        sysAdminuser.setCreateuser(session.getSysAdminUserInfo().getArealname());
        sysAdminuser.setDepartid(Integer.valueOf("0"));
        sysAdminuser.setApasswdupdate(df.parse(dateStr));
        sysAdminuser.setAquerypasswd("0");
        sysAdminuser.setAdesc("0");
        sysAdminuser.setApowerroles("0");
        sysAdminuser.setAwhs("0");
        sysAdminuser.setIsallwh(Boolean.valueOf("0"));
        sysAdminuser.setLoginiptype(Boolean.valueOf("0"));
        sysAdminuser.setCxtnum1(0);
        sysAdminuser.setCxtnum2(0);
        sysAdminuser.setCxtnum3(0);
        sysAdminuser.setStatus(Integer.valueOf("1"));
        sysAdminuser.setLoginlockip("0");
        sysAdminuser.setUpdatedate(df.parse(dateStr));
        sysAdminuser.setUpdateuser(session.getSysAdminUserInfo().getArealname());
        sysAdminuser.setLastdate(df.parse(dateStr));
        sysAdminuser.setLastip("0");
        sysAdminuser.setStype(Byte.valueOf("0"));
        sysAdminuser.setIslimit(Boolean.valueOf("0"));
        sysAdminuser.setIssteel(Byte.valueOf("0"));
        sysAdminuser.setPowerlimit(Byte.valueOf("0"));
        sysAdminuser.setFirstsign(Byte.valueOf("0"));
        sysAdminuser.setChildroot("0");
        sysAdminuser.setMobilecheck("0");
        sysAdminuser.setProvince("0");
        sysAdminuser.setQqnum("0");
        sysAdminuser.setFax("0");
        sysAdminuser.setPostcode("0");
        sysAdminuser.setYqTelnumber("0");
        sysAdminuser.setYqRegistered(Byte.valueOf("0"));
        sysAdminuser.setYqIssealmember(Byte.valueOf("0"));
        sysAdminuser.setYqIsyqsealmember(Byte.valueOf("0"));
        sysAdminuser.setYqChangeduserinfo(Byte.valueOf("0"));
        sysAdminuser.setTelephone("0");
        sysAdminuser.setIsmain(Byte.valueOf("0"));
        sysAdminuser.setCity("0");
        int res = sysAdminuserMapper.accountAddAction(sysAdminuser);
        return res;
    }
    // 根据ID 查询账号信息
    public SysAdminuser selectUserInfoByID(Integer id) {
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.selectUserInfoDetail(id);
        return userDetai;
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




}
