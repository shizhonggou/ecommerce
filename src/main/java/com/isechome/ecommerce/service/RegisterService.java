package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.entity.CompanyInfo;
import com.isechome.ecommerce.mapper.isechome.UserInfoMapper;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoDetailMapper;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoMapper;
import com.isechome.ecommerce.constant.CommonConstant;
import org.apache.tomcat.util.security.MD5Encoder;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class RegisterService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;

    // 修改公司信息
    public void insertCompany(HttpServletRequest request ,@RequestParam(value = "file") MultipartFile file) {
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
        Byte wheref= 0;
        Date create_date = new Date();
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setComName( request.getParameter("com_name"));
        companyInfo.setComNameShort( request.getParameter("com_name_short"));
        companyInfo.setComDesc( request.getParameter("com_desc"));
        companyInfo.setAddressState( request.getParameter("address_state"));
        companyInfo.setAddressCity( request.getParameter("address_city"));
        companyInfo.setAddress( request.getParameter("address"));
        companyInfo.setEnclosureUrl( strPath);
        companyInfo.setCreateDate( create_date );
        companyInfo.setWhereFrom(wheref);
        companyInfoMapper.insert(companyInfo);
        Integer ID= companyInfo.getId();
        //System.out.println(ID+ "11111111111111111111111111111");
        AdminUserInfo userInfo = new AdminUserInfo();
        userInfo.setMid(ID);
        userInfo.setReal_name(request.getParameter("real_name"));
        userInfo.setNick_name(request.getParameter("nick_name"));
        userInfo.setUser_name(request.getParameter("user_name"));
        // 密码 MD5 加密
        userInfo.setPassword(MD5Util.encode(request.getParameter("password")));
        userInfo.setPay_password(MD5Util.encode(request.getParameter("pay_password")));
        userInfo.setMobile(request.getParameter("mobile"));
        userInfo.setIdcard(request.getParameter("idcard"));
        userInfo.setEmail(request.getParameter("email"));
        userInfo.setJob(request.getParameter("job"));
        userInfo.setSex(request.getParameter("sex"));
        userInfo.setAddress_user(request.getParameter("address_user"));
        userInfo.setCreate_date(create_date);
        // 权限设置
        Integer qx = 0;
        userInfo.setRights(qx.longValue());
        userInfo.setStatus(Short.valueOf("1"));
        userInfo.setCreate_user_id(null);
        userInfoMapper.accountAddAction(userInfo);

        Integer link_user_id= userInfo.getId();
        CompanyInfo companyInfo2 = new CompanyInfo();
        companyInfo2.setId(ID);
        companyInfo2.setLinkUserId(link_user_id);
        companyInfoMapper.updateusid(companyInfo2);
        //updateusid

       //companyInfoMapper.insert( request);
    }


}
