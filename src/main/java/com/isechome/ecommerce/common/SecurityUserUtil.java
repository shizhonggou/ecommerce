package com.isechome.ecommerce.common;


import com.isechome.ecommerce.security.SecuritySysUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUserUtil {
    public static SecuritySysUser getCurrentUser() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof SecuritySysUser) {
            return (SecuritySysUser) obj;
        }
        return null;
    }

    public static Integer getCurrentUserId() {
        SecuritySysUser securitySysUser = getCurrentUser();
        if (null == securitySysUser) {
            return null;
        }
        return securitySysUser.getId();
    }
}

