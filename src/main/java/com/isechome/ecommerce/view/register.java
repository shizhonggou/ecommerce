package com.isechome.ecommerce.view;

import com.isechome.ecommerce.security.entity.AdminUserInfo;
import com.isechome.ecommerce.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

@Controller
@RequestMapping(value = "register")
public class register {
@Autowired
RegisterService RegisterService;


@RequestMapping (value = "/reg")
  public ModelAndView registeradd (HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("register");
    return modelAndView;
    }
    @RequestMapping (value = "/registerAddAction", method = RequestMethod.POST)
    public void registerAddAction (HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file") MultipartFile file) throws IOException, ParseException {
        RegisterService.insertCompany(request, file);


        this.alert("保存成功", response);
        this.goBack(response);
    }
    // java后台向前台返回弹窗
    public void alert(String str,HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        String alt = "<script>alert('"+str+"')</script>";
        out.print(alt);
    }
    public void goBack(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html+= "window.location.href='/login_p';";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

}
