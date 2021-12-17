package com.isechome.ecommerce.service;

import com.alibaba.druid.sql.visitor.functions.Length;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.InvoiceInformationManagementMapper;
import com.isechome.ecommerce.mapper.ecommerce.InvoiceManagementMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListBaseMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListDetailMapper;
import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class InvoiceManagementService {
    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper;
    @Autowired
    ScOrderListDetailMapper scOrderListDetailMapper;
    @Autowired
    InvoiceManagementMapper invoiceManagementMapper;
    @Autowired
    InvoiceInformationManagementMapper informationMapper;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;

    @Autowired
    SysCompanyMapper sysCompanyMapper;
    @Autowired
    SmownmemberMapper smownmemberMapper;
    
    private PageInfo<InvoiceManagement> pageInfo;
    private PageInfo<ScOrderListBase> pageInfo1;

    public void index(HttpServletRequest request,Model model){
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer Comid= session.getSysAdminUserInfo().getComid();
        Integer pmid=SecurityUserUtil.getPmid();
      // Integer pmid=1;
        //分页
        String pageNumStr = request.getParameter("page");
        Integer page = 0;
        if (pageNumStr == null || pageNumStr.equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumStr);
        }
        if (page < 1) {
            page = 1;
        }
        int limit = CommonConstant.PAGE_SIZE;
        PageHelper.startPage(page, limit);

        
        String kpstatus = request.getParameter("status");
        String mid = request.getParameter("companyid");
        String stime = request.getParameter("stime");
        String etime = request.getParameter("etime");
        String orderId = request.getParameter("orderId");
        String companyName = request.getParameter("companyname");
        String invoicenum = request.getParameter("sinvoicenum");
        System.out.println("打印====="+invoicenum);
        if(companyName == null){
            companyName = "";
        }
        if(mid == null){
            mid = "";
        }
        if(orderId == null){
            orderId = "";
        }
        if(stime == null){
            stime = "";
        }
        if(etime == null){
            etime = "";
        }
        if(invoicenum==null){
            invoicenum="";
        }
        Integer  source=0;
        Map<String,Object> invoicemap = new HashMap<String, Object>();
     
        if(Comid.equals(pmid)){//平台方
            source=1;
            if( kpstatus==null || kpstatus.equals("")){
                kpstatus="1";
            }
            invoicemap.put("pmid", Comid);
            if(mid.equals("") && !companyName.equals("")){//直接搜索文字
                List<Smownmember> oSmownmembers1=smownmemberMapper.selectAll4(pmid);
                String comids="";
                for (Smownmember smownmember : oSmownmembers1) {
                    comids+=smownmember.getApplymid()+",";
                }
                comids=comids.substring(0, comids.length()-1);
               
               // String searchName=request.getParameter("name");
                List<SysCompany> list=sysCompanyMapper.seletSerch(companyName,comids,200);
                for (SysCompany sysCompany : list) {
                    mid+=sysCompany.getId()+",";
                }
                System.out.print("mid===="+mid);
                if(!mid.equals("")){
                    mid=mid.substring(0, mid.length()-1);
                }
                invoicemap.put("mid",mid);
            }
            invoicemap.put("mid",mid);
        }else{
            invoicemap.put("mid", Comid);//购买方
            invoicemap.put("pmid", pmid);
        }
        if(kpstatus==null ||  kpstatus.equals("0") || kpstatus.equals("") ){
            kpstatus="0";
             //客户 ---申请开票，已申请，已开票
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("status", "4");
            map.put("iskp","0");
            map.put("purchase_mid",Comid);
            map.put("pmid",pmid);
            System.out.println("打印========");
            List<ScOrderListBase> scOrderList = scOrderListBaseMapper.getYjsOrderList(map);
           pageInfo1 = new PageInfo<ScOrderListBase>(scOrderList);
           model.addAttribute("pageInfo", pageInfo1);
            model.addAttribute("orderlist",scOrderList);
    
        }else {
            if(kpstatus.equals("1")){//已申请
                //获取已经申请开票的订单号
                invoicemap.put("status", "1");
            }else if(kpstatus.equals("2")){//已开票
                //获取已经开票的订单号
                invoicemap.put("status", "2");
            }
            if(stime!=""){
                stime+="00:00:00";
            }
            if(etime!=""){
                etime+="23:59:59";
            }
            invoicemap.put("stime",stime);
            invoicemap.put("etime",etime);
            invoicemap.put("orderId",orderId);
            invoicemap.put("invoiceNum", invoicenum);
            
            List<InvoiceManagement> invocelist=invoiceManagementMapper.getInvoiceList(invoicemap);
            pageInfo = new PageInfo<InvoiceManagement>(invocelist);
            String adminids="";
            String ComNameIDs="";
            String baseid="";
            for (InvoiceManagement invoice : invocelist) {
                adminids+=invoice.getDrawer()+",";
                adminids+=invoice.getCreateUser()+",";
                ComNameIDs+=invoice.getMid()+",";
                baseid+=invoice.getInvoiceBaseid()+",";

            }
            List<SysAdminuser> userinfo=null;
            if(!adminids.equals("")){
                adminids=adminids.substring(0, adminids.length()-1);
                userinfo=sysAdminuserMapper.selectAll(adminids);
            }
            List<SysCompany> companyinfo=null;
            if(!ComNameIDs.equals("")){
                ComNameIDs=ComNameIDs.substring(0, ComNameIDs.length()-1);
                companyinfo=sysCompanyMapper.seletIDs(ComNameIDs);
            }
            List<InvoiceInformationManagement> invoiceinformation=null;
            if(!baseid.equals("")){
                baseid=baseid.substring(0, baseid.length()-1);
                invoiceinformation=informationMapper.selectByInId(baseid);
            }

            for (InvoiceManagement invoice : invocelist) {
                if(userinfo!=null){
                    for (SysAdminuser sysAdminuser : userinfo) {
                        if(invoice.getCreateUser().equals(sysAdminuser.getId())){
                            invoice.setCreateUserName(sysAdminuser.getArealname());
                        }
                        if(invoice.getDrawer().equals(sysAdminuser.getId())){
                            invoice.setDrawerName(sysAdminuser.getArealname());
                        }
                    }
                  
                }else{
                    invoice.setCreateUserName("");
                    invoice.setDrawerName("");
                }
                if(companyinfo!=null){
                    for (SysCompany sysCompany : companyinfo) {
                        if(invoice.getMid().equals(sysCompany.getId())){
                            invoice.setComName(sysCompany.getComnameshort());
                        }
                    }
                }
                if(invoiceinformation!=null){
                    for (InvoiceInformationManagement invoiceInformationManagement : invoiceinformation) {
                        if(invoice.getInvoiceBaseid().equals(invoiceInformationManagement.getId())){
                            invoice.setInvoicerise(invoiceInformationManagement.getRise());
                        }
                    }
                }
                
                
            }
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("listinfo",invocelist);
        }
        model.addAttribute("mid", mid);
        model.addAttribute("stime", stime);
        model.addAttribute("etime", etime);
        model.addAttribute("orderId", orderId);
        model.addAttribute("issource", source);
        model.addAttribute("kpstatus", kpstatus);
        model.addAttribute("invoicenum", invoicenum);
        model.addAttribute("companyName", companyName);
    }

    public void getinvoiceinfo(String id,Model model,String invoicetype){
        if(id !=null && !id.equals("") && !invoicetype.equals("")){
            InvoiceInformationManagement invoiceinfo=informationMapper.selectByPrimaryKey(Integer.parseInt(id));
            model.addAttribute("invoiceinfo", invoiceinfo);
        }else if(id !=null && !id.equals("") && invoicetype.equals("")){
            String ids[]=id.split(",");
            var orderids="";
            for (String string : ids) {
                orderids+="'"+string+"',";
            }
            orderids=orderids.substring(0, orderids.length()-1);
            List<ScOrderListBase> orderList=scOrderListBaseMapper.getBaseOrderIds(orderids);
            model.addAttribute("orderlist", orderList);
        }else{
            SecuritySysUser session = SecurityUserUtil.getCurrentUser();
            Integer Comid= session.getSysAdminUserInfo().getComid();
            Integer pmid=SecurityUserUtil.getPmid();
            List<InvoiceInformationManagement> invoicelist=informationMapper.selectBymid(Comid,pmid);
            model.addAttribute("invoicelist", invoicelist);
        }
       
       
    }

    public void saveInvoice(HttpServletRequest request, Model model) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userId= session.getSysAdminUserInfo().getId();
        Integer Comid=session.getSysAdminUserInfo().getComid();
        Integer pmid=SecurityUserUtil.getPmid();
        String status=request.getParameter("status");
    //    System.out.println(request);
        // Map<String,>
        // pmid=1;
        if(Comid.equals(pmid)){//平台方
         
            String[] ids=request.getParameterValues("kpcheck");
            if(status.equals("1")){//确认开票
                InvoiceManagement record=new InvoiceManagement();
                record.setDrawer(userId);
                for(int i=0;i<ids.length;i++){
                    record.setId(Integer.parseInt(ids[i]));
                    record.setStatus(2);
                    String path=request.getParameter("imgsrc"+ids[i]);
                    record.setInvoiceUrl(path);
                    String invoiceNum=request.getParameter("invoiceNum"+ids[i]);
                    record.setInvoiceNum(invoiceNum);
                    invoiceManagementMapper.updateByPrimaryKey(record);
                   
                }
            }
        }else{//购买方---申请开票
            String orderIds=request.getParameter("orderId");
            String invoiceType=request.getParameter("kptype");
            List<ScOrderListBase> orderInfo=scOrderListBaseMapper.getBaseByIds(orderIds);
            if(status==null || status.equals("0")){
                System.out.println("开票类型");
                String kpid=request.getParameter("kpid");
                ScOrderListBase recode1=new ScOrderListBase();
                InvoiceManagement record=new InvoiceManagement();
                record.setInvoiceBaseid(Integer.parseInt(kpid));
                record.setPmid(pmid);
                record.setStatus(1);
                record.setCreateUser(userId);
                record.setMid(Comid);
                record.setInvoiceType(Integer.parseInt(invoiceType));
                var order_ids="";
                Double sum_money=0.00;
                for (ScOrderListBase scOrderListBase : orderInfo) {
                    order_ids+=scOrderListBase.getOrderId()+",";
                    sum_money+=scOrderListBase.getSettlementMoney();
                    recode1.setIskp((byte) 1);
                    recode1.setId(scOrderListBase.getId());
                    scOrderListBaseMapper.updatepurchase(recode1);
                } 
                order_ids=order_ids.substring(0, order_ids.length()-1);
                record.setOrderId(order_ids);
                record.setInvoiceMoney(sum_money);
                invoiceManagementMapper.insert(record);
            }
        }
    }  
    
   
    
    public Object getSearchCompay(HttpServletRequest request){
        Integer pmid=SecurityUserUtil.getPmid();
        List<Smownmember> oSmownmembers=smownmemberMapper.selectAll4(pmid);
        String comids="";
        for (Smownmember smownmember : oSmownmembers) {
            comids+=smownmember.getApplymid()+",";
        }
        comids=comids.substring(0, comids.length()-1);
        String searchName=request.getParameter("name");
        List<SysCompany> list=sysCompanyMapper.seletSerch(searchName,comids,200);
        JSONObject jsonObject=new JSONObject();
        for (SysCompany sysCompany : list) {
            jsonObject.put(String.valueOf(sysCompany.getId()),sysCompany.getComname());
           // options+="<option value='"+sysCompany.getId()+"'>" + sysCompany.getComname() + "</option>";
        }
       // options=js
      
        return jsonObject;
    }
    public List<SysCompany> getSearchAll(){
        Integer pmid=SecurityUserUtil.getPmid();
        List<Smownmember> oSmownmembers=smownmemberMapper.selectAll4(pmid);
        String comids="";
        for (Smownmember smownmember : oSmownmembers) {
            comids+=smownmember.getApplymid()+",";
        }
        comids=comids.substring(0, comids.length()-1);
        List<SysCompany> list=sysCompanyMapper.seletSerch("",comids,2000);
        return list;
    }

    public  Map<String, Object> getPdfinfo(Integer id,String orderids){
        Map<String, Object> pdfinfo = new HashMap<>();
        //String orderIds=request.getParameter("orderIds");
        orderids=orderids.replace(",", "','");
        List<ScOrderListDetail> listinfo=scOrderListDetailMapper.getAllDetailByBaseid("'"+orderids+"'");
       // String tmpid=  request.getParameter("id");
       // System.out.println("传递的参数id"+tmpid);
       // Integer id=Integer.parseInt(tmpid);
        System.out.println("转换的id"+id);
        InvoiceManagement invoice=invoiceManagementMapper.selectByPrimaryKey(id);
        String  ComNameIDs=invoice.getPmid()+"','"+invoice.getMid();
        List<SysCompany> companyinfo=null;
        String purchase_company="";
        String pmid_company="";
        if(!ComNameIDs.equals("")){
            companyinfo=sysCompanyMapper.seletIDs("'"+ComNameIDs+"'");
        }
        for (SysCompany sysCompany : companyinfo) {
            if(sysCompany.getId().equals(invoice.getMid())){
                purchase_company=sysCompany.getComname();
            }
            if(sysCompany.getId().equals(invoice.getPmid())){
                pmid_company=sysCompany.getComname();
            }
        }
        //发票代码
        InvoiceInformationManagement informationManagement=informationMapper.selectByPrimaryKey(invoice.getInvoiceBaseid());
        pdfinfo.put("sheet", listinfo);
        pdfinfo.put("pmid_company", pmid_company);
        pdfinfo.put("purchase_company", purchase_company);
        pdfinfo.put("invoice_num", invoice.getInvoiceNum());
        pdfinfo.put("invoice_code", informationManagement.getInvoiceCode());
      
        pdfinfo.put("kpdate", invoice.getUpdateTime());
         
        return pdfinfo;
    }
    
}
