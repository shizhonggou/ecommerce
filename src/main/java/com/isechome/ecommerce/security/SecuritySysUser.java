package com.isechome.ecommerce.security;
import com.isechome.ecommerce.entity.BalanceInfo;
import com.isechome.ecommerce.security.entity.Scplatformsidesetting;
import com.isechome.ecommerce.security.entity.SysAdminuser;
import com.isechome.ecommerce.security.entity.SysCompany;
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
    private Long rights;
    private String workTime;
    private SysAdminuser SysAdminuser;
    private SysCompany SysCompany;
    private BalanceInfo BalanceInfo;
    private Scplatformsidesetting Scplatformsidesetting;
    private Integer pmid;
    private String logoUrl;

    List<GrantedAuthority> authorities;

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

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
    public Long getRights() {
        return rights;
    }

    public void setRights(Long rights) {
        this.rights = rights;
    }
    
    public SysCompany getSysCompanyInfo() {
        return SysCompany;
    }

    public void setSysCompanyInfo(SysCompany SyscompanyInfo) {
        this.SysCompany = SyscompanyInfo;
    }

    public SysAdminuser getSysAdminUserInfo() {
        return SysAdminuser;
    }

    public void setSysAdminUserInfo(SysAdminuser SysadminUserInfo) {
        this.SysAdminuser = SysadminUserInfo;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    public BalanceInfo getBalanceInfo() {
        return BalanceInfo;
    }

    public void setBalanceInfo(BalanceInfo BalanceInfo) {
        this.BalanceInfo = BalanceInfo;
    }

    public Scplatformsidesetting getScplatformsidesetting() {
        return Scplatformsidesetting;
    }

    public void setScplatformsidesetting(Scplatformsidesetting Scplatformsidesetting) {
        this.Scplatformsidesetting = Scplatformsidesetting;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
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