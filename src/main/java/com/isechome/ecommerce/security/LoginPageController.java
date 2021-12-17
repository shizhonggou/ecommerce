package com.isechome.ecommerce.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class LoginPageController {
    @RequestMapping(value = "/login_p")
//    public String loginpage(@RequestHeader(value = "referer", required = false) final String referer, Model model){
    public String loginpage( HttpServletRequest request, Model model){
        String referer = request.getHeader("Referer");
        String token = request.getParameter("token");
        HttpSession session = request.getSession();
        session.setAttribute("token",token);
       // System.out.println(token);
        //        model.addAttribute("callback", referer);
        return "login";
    }
    @RequestMapping(value = "/page403")
    public String page403( HttpServletRequest request ){
        HttpSession session = request.getSession();
        int pagetype = 1;
        if ( session.getAttribute("errorpage403") != null ) {
            pagetype = Integer.parseInt( session.getAttribute("errorpage403").toString() );
        }else {
            pagetype = new Random().nextBoolean() ? 1:2;
            session.setAttribute("errorpage403", pagetype );
        }

        if ( pagetype == 1 ) {
            return "403_2";
        }else {
            return "403";
        }
    }

}
