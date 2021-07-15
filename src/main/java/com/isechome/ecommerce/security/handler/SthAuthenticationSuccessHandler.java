package com.isechome.ecommerce.security.handler;


import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.security.RespBean;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        response.setContentType("application/json;charset=utf-8");

        RespBean respBean = RespBean.ok("登录成功!", SecurityUserUtil.getCurrentUser());
        String url = "/index";
        if(savedRequest != null) {
            url = savedRequest.getRedirectUrl();
        }
        respBean.setJumpTo(url);
        new SthWebMvcWrite().writeToWeb(response, respBean);

    }
}
