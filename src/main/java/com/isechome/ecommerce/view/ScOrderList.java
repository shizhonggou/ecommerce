/*
 * @Author: shitaodi
 * @Date: 2021-08-23 16:17:03
 * @LastEditTime: 2021-11-15 10:50:51
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\view\ScOrderList.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.view;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isechome.ecommerce.service.ScOrderListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.util.PdfUtil;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "scorderlist")
public class ScOrderList extends AbstractPdfView {

    @Autowired
    private ScOrderListService scOrderListService;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = new Date().getTime() + "_quotation.pdf"; // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "filename=" + new String(fileName.getBytes(), "iso8859-1"));
        PdfUtil pdfUtil = new PdfUtil();
        // document.setMargins(1.0f,1.0f,1.0f,1.0f);
        document.newPage();
        Rectangle pageSize = new Rectangle(842.f, 595.f);

        if (Integer.parseInt(model.get("type").toString()) == 2) {
            pageSize.setBorderColor(Color.red);
        }
        pageSize.rotate();
        document.setPageSize(pageSize);

        pdfUtil.createPDF(document, writer, model);
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        scOrderListService.index(request, model);
        return "scorderlist/index";
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        scOrderListService.detail(request, response, model);
        return "scorderlist/detail";
    }

    @RequestMapping(value = "/resourceIndex")
    public String resourceIndex(HttpServletRequest request, Model model, ScShelfResource scShelfResource)
            throws IOException {
        scOrderListService.resourceIndex(request, model, scShelfResource);
        model.addAttribute("scShelfResource", scShelfResource);
        return "scorderlist/resourceindex";
    }

    @RequestMapping(value = "/getWeightNum")
    @ResponseBody
    public HashMap<String, Object> getWeightNum(HttpServletRequest request, HttpServletResponse response)
            throws ParseException {
        return scOrderListService.getWeightNum(request, response);
    }

    @PreAuthorize("((hasRole('ROLE_GYLQX_MASTERCG') or hasRole('ROLE_GYLQX_MASTERCGSH')) and hasRole('ROLE_GYLQX_PTF')) or (hasRole('ROLE_GYLQX_MASTER') and hasRole('ROLE_GYLQX_PTF'))")
    @RequestMapping(value = "/cgindex")
    public String cgindex(HttpServletRequest request, Model model) {
        scOrderListService.cgindex(request, model);
        return "scorderlist/cgindex";
    }

    @PreAuthorize("(hasRole('ROLE_GYLQX_MASTERCG') and hasRole('ROLE_GYLQX_PTF')) or (hasRole('ROLE_GYLQX_MASTER') and hasRole('ROLE_GYLQX_PTF'))")
    @RequestMapping(value = "/addpurchase")
    public String addpurchase(HttpServletRequest request, Model model) {
        scOrderListService.addpurchase(request, model);
        return "scorderlist/addpurchase";
    }

    @RequestMapping(value = "/addpurchaseSave")
    public void addpurchaseSave(HttpServletRequest request, HttpServletResponse response)
            throws ParseException, IOException {
        int res = scOrderListService.addpurchaseSave(request);
        if (res == 1) {
            this.alert("添加成功", response);
            this.goURL("/scorderlist/cgindex", response);
        } else {
            this.alert("添加失败", response);
            this.goBack(response);
            this.reload(response);
        }

    }

    // java后台向前台返回弹窗
    public void alert(String str, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); // 转码
        PrintWriter out = response.getWriter();
        String alt = "<script>alert('" + str + "');</script>";
        out.print(alt);
    }

    // //页面刷新
    public void reload(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html += "window.location.reload();";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

    // 后退 刷新
    public void goBack(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html += "history.back();";
        // html+= "window.location.reload();";
        html += "</script>";
        out.print(html);
    }

    // 页面跳转
    public void goURL(String url, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String html = "<script>";
        html += "window.location.href='" + url + "'";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

}