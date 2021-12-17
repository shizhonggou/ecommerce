package com.isechome.ecommerce.controller;



import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.service.PieceWeightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("/pieceweight")
@Slf4j
/**
 * @Description: 件重管理
 * @Author: shizg
 * @Date: 2021/5/27 15:40
 * @param null:
 * @return: null
 **/
public class PieceWeightController {
    @Autowired
    private PieceWeightService pieceWeightService;

    /**
     * @Description:件重列表
     * @Author: shizg
     * @Date: 2021/5/27 15:40

     * @return: java.util.List<com.isechome.ecommerce.entity.CompanyInfo>
     **/
    @RequestMapping("list")
    public String getPieceWeightInfo( HttpServletRequest request, Model model ) {
        pieceWeightService.getPieceWeightInfo( request, model );
        return "pieceweight/list";
    }

    @RequestMapping("/test")
    public String test(){
        return "coming!!!";
    }

    @RequestMapping("add")
    public String insertPieceWeight(HttpServletRequest request , Model model ) {
        pieceWeightService.getPieceWeightInfoByid( request , model);
        return "pieceweight/add";
    }

    @RequestMapping("save")
    public void savePieceWeight(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
        String saveMessage = pieceWeightService.savePieceWeight( request , model);
        response.setContentType("text/html;charset=UTF-8");
        Common pulFun = new Common();
        PrintWriter out=new PrintWriter(response.getOutputStream());
        pulFun.alert(saveMessage, out );
        pulFun.goURL("/pieceweight/list", out );
        //return "redirect:/pieceweight/list";
    }

    /**
     * @Description: 删除件重
     * @Author: shizg
     * @Date: 2021/5/31 11:13
     * @param request:
     * @return: java.lang.String
     **/
    @RequestMapping("delete")
    public String deletePieceWeight ( HttpServletRequest request ){
        pieceWeightService.deletePieceWeight( request );
        return "redirect:/pieceweight/list";
    }

    /**
     * @Description:执行审核
     * @Author: shizg
     * @Date: 2021/6/1 10:16

     * @return: java.lang.String
     **/
    @RequestMapping("review")
    public String review (){
        pieceWeightService.review();
        return "redirect:/pieceweight/list";
    }
}
