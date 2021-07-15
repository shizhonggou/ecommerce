package com.isechome.ecommerce.security;


import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.security.entity.CompanyInfo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecuritySysUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String pay_password;

    private CompanyInfo companyInfo;
    private AdminUserInfo adminUserInfo;
    List<GrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public AdminUserInfo getAdminUserInfo() {
        return adminUserInfo;
    }

    public void setAdminUserInfo(AdminUserInfo adminUserInfo) {
        this.adminUserInfo = adminUserInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        //List<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> auth){
        authorities = auth;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
}
