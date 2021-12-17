package com.isechome.ecommerce.service;

import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.AdminUserInfo;
import com.isechome.ecommerce.mapper.isechome.AdminUserInfoMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.security.mapper.isechome.SecurityUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: ecommerce
 * @description: 账号service
 * @author: Mr.shizg
 * @create: 2021-05-26 08:59
 **/
@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class AdminUserInfoService {
    @Autowired
    SecurityUserMapper securityUserMapper;
    @Autowired
    AdminUserInfoMapper adminuserInfoMapper;
    /**
     * 根据用户名查询
     * @param username
     */
    public void getUserInfoByUsername(String username) {
        securityUserMapper.getUserInfoByUsername(username);
    }
    public AdminUserInfo getadminuserinfobymobile(String tel) {
     return adminuserInfoMapper.getadminuserinfobymobile(tel);
        
    }
    public AdminUserInfo getUserInfoByUsername2(String username) {
        return adminuserInfoMapper.getUserInfoByUsername(username);
    }
    // 检验交易密码
    public String checkPayWord(String inputPayWord){
        MD5Util md5util=new MD5Util();
        String inputPayWordMd5 = md5util.encode(inputPayWord);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        String pay_password = session.getSysAdminUserInfo().getTradepwd();
        if (pay_password.equals(inputPayWordMd5)) {
            return "1";
        } else {
            return "2";
        }
    }

}
