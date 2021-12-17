package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.entity.FinancialInfo;
import java.util.List;

public interface UserInfoMapper {
    // 查询个人信息
    AdminUserInfo selectUserInfoDetail(Integer id);
    void updateUserInfo(Integer id, String real_name,String nick_name,String user_name,String password,String pay_password,String mobile, String idcard,String email,String job,Integer sex,String address_user);
    // 账号管理  查询 所有账号
    List<AdminUserInfo> selectAllUser(Integer id);
    // 修改账号信息
    void accountUpdate(Integer id, String real_name,String nick_name,String user_name,String password,String pay_password,String mobile, String idcard,String email,String job,Integer sex,String address_user,Integer rights);
    // 删除账号
    void accountDelete(Integer id);
    // 新增账号
    void accountAddAction(AdminUserInfo userInfo);
    // 检查用户名
    AdminUserInfo checkUserName(String user_name);
    // 检查手机号
    AdminUserInfo checkMobile(String mobile);
    // 录入财务信息
    void financialInsert(FinancialInfo financialInfo);
}
