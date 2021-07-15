package com.isechome.ecommerce.security;

import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.security.entity.CompanyInfo;
import com.isechome.ecommerce.security.mapper.SecurityUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: libing
 * @Date: 2021/4/13
 * @Description: spring-security权限管理框架
 **/
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private SecurityUserMapper securityUserMapper;

    /**
     * @Description 实现了UserDetailsService接口中的loadUserByUsername方法
     * 执行登录,构建Authentication对象必须的信息,
     * 如果用户不存在，则抛出UsernameNotFoundException异常
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecuritySysUser sysuser = new SecuritySysUser();
        AdminUserInfo user = securityUserMapper.getUserInfoByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不对");
        }

        CompanyInfo member = securityUserMapper.getMemberInfoById(user.getMid());

        sysuser.setId(user.getId());
        sysuser.setUsername(username);
        sysuser.setPassword(user.getPassword());
        sysuser.setAdminUserInfo(user);
        sysuser.setCompanyInfo(member);
        //System.out.println("111"+user.getPassword()+"登录成功2!");
 /**
        // 获取角色
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_LOGIN")); //登录用户
        if( user.getId() == member.getLink_user_id() ){ //主账号
            list.add(new SimpleGrantedAuthority("ROLE_MASTER"));
        }
        if(user.getMid()==1){ //钢之家员工
            list.add(new SimpleGrantedAuthority("ROLE_GZJ"));
        }
//        Map<Integer,String> level_map = new HashMap<Integer,String>();
//        level_map.put(1,"T"); //试用
//        level_map.put(2,"F"); //免费
//        level_map.put(4,"C"); //丙级
//        level_map.put(5,"B"); //乙级
//        level_map.put(6,"A"); //甲级
//        level_map.put(7,"VIP"); //甲级
//
//        sysuser.setAuthorities(list);
 */
        return sysuser;
    }


}
