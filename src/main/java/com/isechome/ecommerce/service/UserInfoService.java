package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.security.entity.CompanyInfo;
import com.isechome.ecommerce.mapper.isechome.UserInfoMapper;
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
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    CompanyInfoDetailMapper companyInfoMapper;

    // 查询个人信息
    public AdminUserInfo userInfoDetai(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getAdminUserInfo().getId();
        AdminUserInfo userDetai = new AdminUserInfo();
        userDetai = userInfoMapper.selectUserInfoDetail(id);
        return userDetai;
    }
    //    更新个人信息
    public void updateUserInfo(HttpServletRequest request) {
        /*//获取选中的权限
        Integer qx = 0;
        String[] args=request.getParameterValues("quanxian");
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                qx += Integer.valueOf(args[i]);
            }
        }*/
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
        userInfoMapper.updateUserInfo(Integer.valueOf(request.getParameter("id")), request.getParameter("real_name"),request.getParameter("nick_name"), request.getParameter("user_name"),pwd, pay_pwd,request.getParameter("mobile"), request.getParameter("idcard"),request.getParameter("email"), request.getParameter("job"),Integer.valueOf(request.getParameter("sex")), request.getParameter("address_user"));
    }
    // 查询公司信息
    public CompanyInfo companyInfoDetai() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getCompanyInfo().getId();
        CompanyInfo companyDetai = new CompanyInfo();
        companyDetai = companyInfoMapper.selectCompanyInfo(id);
        return companyDetai;
    }
    // 修改公司信息
    public void updateCompanyInfo(HttpServletRequest request ,@RequestParam(value = "file") MultipartFile file) {
        String strPath = "";
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
//                System.out.println(path);
            }catch(Exception e){
                e.printStackTrace();
                //  "上传失败";
            }
        }
        companyInfoMapper.updateCompanyInfoDetail(Integer.valueOf(request.getParameter("id")), request.getParameter("com_name"),request.getParameter("com_name_short"),request.getParameter("com_desc"), request.getParameter("address_state"),request.getParameter("address_city"),request.getParameter("address"),strPath);
    }
    // 账号管理  查询所有账号
    public List<AdminUserInfo> selectAllUser() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getCompanyInfo().getId();
        List<AdminUserInfo> userList = userInfoMapper.selectAllUser(id);
        return  userList;
    }
    // 账号管理 修改
    public AdminUserInfo accountDetai(HttpServletRequest request) {
        Integer id= Integer.valueOf(request.getParameter("id"));
        AdminUserInfo userDetai = new AdminUserInfo();
        userDetai = userInfoMapper.selectUserInfoDetail(id);
        return userDetai;
    }
    // 账号修改
    public void accountUpdate(HttpServletRequest request) {
        Integer qx = 0;
        //获取选中的权限
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
        userInfoMapper.accountUpdate(Integer.valueOf(request.getParameter("id")), request.getParameter("real_name"),request.getParameter("nick_name"), request.getParameter("user_name"),pwd, pay_pwd,request.getParameter("mobile"), request.getParameter("idcard"),request.getParameter("email"), request.getParameter("job"),Integer.valueOf(request.getParameter("sex")), request.getParameter("address_user"), qx);
    }
    // 修改设置主联系人
    public void updateCompanyInfoZhuLXR(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id = session.getCompanyInfo().getId();
        companyInfoMapper.updateCompanyInfoZhuLXR(id,Integer.valueOf(request.getParameter("link_user_id")));

    }
    // 账号删除
    public void accountDelete(HttpServletRequest request) {
        userInfoMapper.accountDelete(Integer.valueOf(request.getParameter("id")));
    }
    // 新增账号
    public void accountAddAction(HttpServletRequest request) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getAdminUserInfo().getId();
        Integer mid = session.getCompanyInfo().getId();
        AdminUserInfo userInfo = new AdminUserInfo();
        userInfo.setMid(mid);
        userInfo.setReal_name(request.getParameter("real_name"));
        userInfo.setNick_name(request.getParameter("nick_name"));
        userInfo.setUser_name(request.getParameter("user_name"));
        // 密码 MD5 加密
        userInfo.setPassword(MD5Util.encode(request.getParameter("pwd_input")));
        userInfo.setPay_password(MD5Util.encode(request.getParameter("pay_password")));
        userInfo.setMobile(request.getParameter("mobile"));
        userInfo.setIdcard(request.getParameter("idcard"));
        userInfo.setEmail(request.getParameter("email"));
        userInfo.setJob(request.getParameter("job"));
        userInfo.setSex(request.getParameter("sex"));
        userInfo.setAddress_user(request.getParameter("address_user"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());
        userInfo.setCreate_date(df.parse(dateStr));
        // 权限设置
        Integer qx = 0;
        String[] args=request.getParameterValues("quanxian");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                qx += Integer.valueOf(args[i]);
            }
        }
        userInfo.setRights(qx.longValue());
        userInfo.setStatus(Short.valueOf("1"));
        userInfo.setCreate_user_id(id);
        userInfoMapper.accountAddAction(userInfo);
    }
    // 检查用户名
    public AdminUserInfo checkUserName(String userName) {
        AdminUserInfo userDetai = new AdminUserInfo();
        userDetai = userInfoMapper.checkUserName(userName);
        return userDetai;
    }
    // 检查手机号码
    public AdminUserInfo checkMobile(String mobile) {
        AdminUserInfo userDetai = new AdminUserInfo();
        userDetai = userInfoMapper.checkMobile(mobile);
        return userDetai;
    }



}
