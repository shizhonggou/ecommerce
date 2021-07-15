package com.isechome.ecommerce.security.mapper;

import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.security.entity.CompanyInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserMapper {
    AdminUserInfo getUserInfoByUsername(String username);
    CompanyInfo getMemberInfoById(Integer id);
    AdminUserInfo getadminuserinfobymobile(String tel);

}
