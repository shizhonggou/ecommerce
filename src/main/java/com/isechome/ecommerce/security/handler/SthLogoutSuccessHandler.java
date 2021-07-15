package com.isechome.ecommerce.security.handler;

import com.isechome.ecommerce.security.RespBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SthLogoutSuccessHandler implements LogoutSuccessHandler  {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        RespBean respBean = RespBean.ok("注销成功!");

        request.getSession().invalidate();

        Cookie cookie = new Cookie("remember-me",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        new SthWebMvcWrite().writeToWeb(response, respBean);
        System.out.println("注销成功!");
    }
}
