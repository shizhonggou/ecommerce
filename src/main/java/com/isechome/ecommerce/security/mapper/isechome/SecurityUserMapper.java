package com.isechome.ecommerce.security.mapper.isechome;

import com.isechome.ecommerce.security.entity.SysAdminuser;
import com.isechome.ecommerce.security.entity.SysCompany;

import java.util.List;

import com.isechome.ecommerce.entity.Scplatformsidesetting;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserMapper {
    SysAdminuser getUserInfoByUsername(String username);
    SysCompany getMemberInfoById(Integer id);
    SysAdminuser getadminuserinfobymobile(String tel);
    Scplatformsidesetting selectbytoken(String token);
    Scplatformsidesetting selectnewpmid();
    Scplatformsidesetting selectLogo(Integer pmid);

}
