package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.AdminUserInfo;
import java.util.List;

public interface AdminUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUserInfo record);

    AdminUserInfo selectByPrimaryKey(Integer id);

    AdminUserInfo getadminuserinfobymobile(String tel);

    List<AdminUserInfo> selectAll();

    int updateByPrimaryKey(AdminUserInfo record);

    List<AdminUserInfo> selectByMid(Integer mid);

    AdminUserInfo getUserInfoByUsername(String username);
}