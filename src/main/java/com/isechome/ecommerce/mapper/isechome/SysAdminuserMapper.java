/*
 * @Author: shitaodi
 * @Date: 2021-08-24 17:13:44
 * @LastEditTime: 2021-09-01 11:02:39
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\isechome\SysAdminuserMapper.java
 * @Description:
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.SysAdminuser;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysAdminuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAdminuser record);

    SysAdminuser selectByPrimaryKey(Integer id);

    List<SysAdminuser> selectAll(String ids);

    int updateByPrimaryKey(SysAdminuser record);

    List<SysAdminuser> selectByComID(Integer id);

    // 查询个人信息
    SysAdminuser selectUserInfoDetail(Integer id);
    int updateUserInfo(Integer id, String real_name,String user_name,String password,String pay_password,String mobile, String idcard,String email,String job,Byte sex);
    //void updateUserInfo(SysAdminuser userInfo);
    // 账号管理  查询 所有账号
    List<SysAdminuser> selectAllUser(Integer id);
    // 修改账号信息
    int accountUpdate(Integer id, String real_name,String user_name,String password,String pay_password,String mobile, String idcard,String email,String job,Integer sex,Integer rights);
    // 删除账号
    int accountDelete(Integer id);
    // 新增账号
    int accountAddAction(SysAdminuser userInfo);
    // 检查用户名
    SysAdminuser checkUserName(String user_name);
    // 检查手机号
    SysAdminuser checkMobile(String mobile);
    // 更新主联系人
    void updateZhuLianXiRen(Integer id, Integer isMain);

    SysAdminuser selectMainAdminuserByComid(Integer ComID);

    int pwdupd(Integer id, String password);

    //用户信息map
    @MapKey("id")
    Map<Integer, SysAdminuser> selectIdAndName();

    //获取有销售管理权限用户信息
    List<SysAdminuser> selecUserInfoByPmidAndRights(Integer pmid);

}