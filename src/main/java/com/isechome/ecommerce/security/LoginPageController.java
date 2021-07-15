package com.isechome.ecommerce.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginPageController {
    @RequestMapping(value = "/login_p")
//    public String loginpage(@RequestHeader(value = "referer", required = false) final String referer, Model model){
      public String loginpage(HttpServletRequest req, Model model){
        String referer = req.getHeader("Referer");
        System.out.println(referer);
//        model.addAttribute("callback", referer);
        return "login";
    }
}
