package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.mapper.ecommerce.FinancialMapper;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.mapper.ecommerce.UserInfoMapper;
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

/**
 * @program: ecommerce
 * @description: 账号service
 * @author: Mr.shizg
 * @create: 2021-05-26 08:59
 **/
@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class SysAdminuserInfoService {
    @Autowired
    SysAdminuserMapper SysAdminuserMapper;
    /**
     * 根据用户名查询
     * @param username
     */
    public void getUserInfoByUsername(String username) {
        SysAdminuserMapper.checkUserName(username);
    }
    public SysAdminuser getadminuserinfobymobile(String tel) {
     return SysAdminuserMapper.checkMobile(tel);
        
    }
    public SysAdminuser getUserInfoByUsername2(String username) {
        return SysAdminuserMapper.checkUserName(username);
    }
    // 检验交易密码
    public String checkPayWord(String inputPayWord){
        new MD5Util();
        String inputPayWordMd5 = MD5Util.encode(inputPayWord);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        String pay_password = session.getSysAdminUserInfo().getTradepwd();
        if (pay_password.equals(inputPayWordMd5)) {
            return "1";
        } else {
            return "2";
        }
    }

}
