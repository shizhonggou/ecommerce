package com.isechome.ecommerce.security;

import com.isechome.ecommerce.common.ResponseUtils;
import com.isechome.ecommerce.security.mapper.isechome.SecurityUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@Api(value = "登录", tags = "登录，注销等操作")
@RestController
public class LoginController {

    @Autowired
    private SecurityUserMapper securityUserMapper;

    @CrossOrigin
//    @ApiOperation(value = "swagger端测试登录入口")
    @PostMapping("/login")
    public RespBean login(String username, String password, String token){
   System.out.println( "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");

    System.out.println(token);
        return RespBean.ok("登录成功!");
    }

//    @ApiOperation(value = "swagger端测试注销入口")
    @CrossOrigin
    @RequestMapping("/logout")
    public LoginResponse logout( HttpSession session, SessionStatus sessionStatus, HttpServletResponse response){
        session.invalidate();
        sessionStatus.setComplete();

        Cookie cookie = new Cookie("remember-me",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);


        System.out.println("注销成功!");
        return ResponseUtils.SUCCESS("注销成功!");
//        System.out.println(username + "------" + password);
    }

    @RequestMapping("/showsession")
    @PreAuthorize("hasRole('ROLE_BXA') or hasRole('ROLE_BXVIP')")
    public String showsession(HttpSession session){
        System.out.println(session);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)){
            SecuritySysUser user =  (SecuritySysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return SecurityContextHolder.getContext().getAuthentication().toString();
        }else {
            String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            //user.getMemberInfo().getId();
            return user;
        }
    }

}
