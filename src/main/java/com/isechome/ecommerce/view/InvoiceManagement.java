package com.isechome.ecommerce.view;


import java.util.Date;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.service.ScOrderListService;
import com.isechome.ecommerce.service.InvoiceManagementService;
import com.isechome.ecommerce.util.FileUtil;

import com.isechome.ecommerce.util.orderDetailPdf;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;

import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.document.AbstractPdfView;




@Controller
@RequestMapping(value="invoiceManagement")
public class InvoiceManagement extends AbstractPdfView {
   
    @Autowired
    InvoiceManagementService invoiceService;

    @Autowired
    ScOrderListService scOrderListService;

    SecuritySysUser session ;
    


    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,Model model ) {  
        session=SecurityUserUtil.getCurrentUser();
        if(session==null){
           return "redirect:/login_p";
        }else{
            invoiceService.index(request, model);
            model.addAttribute("tmpcompay", invoiceService.getSearchAll()) ;
            return "userInfo/invoiceManagement";
        }
        
    }

    @RequestMapping(value = "/getinvoice")
    public String getinvoice(HttpServletRequest request,Model model){
        String id=request.getParameter("id");
        String invoicetype=request.getParameter("type");
        invoiceService.getinvoiceinfo(id, model,invoicetype);
        if(id !=null && !id.equals("") && !invoicetype.equals("")){
            model.addAttribute("type", invoicetype);
            return "userInfo/invoiceManagement::article_detail";
        }else if(id !=null && !id.equals("") && invoicetype.equals("")){
            return "userInfo/invoiceManagement::article_order";
        }else{
            return "userInfo/invoiceManagement::article_type";
        }
    }

    @RequestMapping(value = "/saveInvoice")
    public String saveInvoice(HttpServletRequest request,Model model){
        String starus=request.getParameter("status");
        invoiceService.saveInvoice(request,model);
       // return null;
        return "redirect:/invoiceManagement/index?status="+starus;
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(HttpServletRequest request,@RequestParam(value = "myfile",required = false)MultipartFile file){
      
        try {
            String filePath= FileUtil.executeUpload(file);
            return filePath;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "failed";
        }
       
    }
    @RequestMapping(value = "/getCompay")
    @ResponseBody
    public Object getCompay(HttpServletRequest request,Model model){
      
        Object optValue="";
        optValue=invoiceService.getSearchCompay(request);
      
        return optValue;
       
    }



    @Override
//    protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document document,
//            com.lowagie.text.pdf.PdfWriter writer, HttpServletRequest arg3, HttpServletResponse response) throws Exception {
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // PdfReport_BX pdfutil=new PdfReport_BX();
        // PdfReport_BX.pdf();
        String fileName = new Date().getTime()+"_quotation.pdf"; // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","filename=" + new String(fileName.getBytes(), "iso8859-1"));
        orderDetailPdf pdfUtil = new orderDetailPdf();
        //document.setMargins(1.0f,1.0f,1.0f,1.0f);
        document.newPage();
        Rectangle pageSize = new Rectangle(842.f,595.f);
        pageSize.rotate();
        document.setPageSize(pageSize);
        pdfUtil.createPDF(document, writer, model);
    }

    

}