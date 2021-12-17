package com.isechome.ecommerce.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.HttpUtils;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.*;
import com.isechome.ecommerce.mapper.isechome.Province2AreaMapper;
import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.security.UserSecurityService;
import com.isechome.ecommerce.util.ArithmeticUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ScOrderListService {
    @Autowired
    ScOrderListMapper scOrderListMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    LogisticsInformationMapper logisticsInformationMapper;
    @Autowired
    ScPieceWeightManageMapper scPieceWeightManageMapper;
    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper;
    @Autowired
    ScOrderListDetailMapper scOrderListDetailMapper;
    @Autowired
    ScCompanyBalanceMapper scCompanyBalanceMapper;
    @Autowired
    LogisticsPurchaseMapper logisticsPurchaseMapper;
    @Autowired
    ScShelfResourceMapper scShelfResourceMapper;
    @Autowired
    CompanyInfoService companyInfoService;
    @Autowired
    Province2AreaMapper province2AreaMapper;
    @Autowired
    ScPurchasBaseMapper scPurchasBaseMapper;
    @Autowired
    ScPurchasDetailMapper scPurchasDetailMapper;
    @Autowired
    InvoiceManagementService invoiceService;
    @Autowired
    StopOpenPlateService stopOpenPlateService;
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    FinancialMapper financialMapper;
    @Autowired
    SmownmemberMapper smownmemberMapper;
    @Value("${smslogin.url}")
    public String sms_content_url;
    String sms_conten_param = "action=ajaxsendSMSContent&mobile=";

    // 超量资源管理
    public void resourceIndex(HttpServletRequest request, Model model, ScShelfResource scShelfResource) {
        Integer pmid = SecurityUserUtil.getPmid();
        Map<String, Object> resIds = new HashMap<String, Object>();
        Map<String, Object> detailIds = new HashMap<String, Object>();
        Map<String, Object> extractIds = new HashMap<String, Object>();
        Map<Integer, Object> shenhe = new HashMap<Integer, Object>();
        Map<Integer, Object> thd = new HashMap<Integer, Object>();
        Map<Integer, Object> cgd = new HashMap<Integer, Object>();
        Map<Integer, Object> cgnum = new HashMap<Integer, Object>();
        Map<Integer, Object> heji = new HashMap<Integer, Object>();
        Map<Integer, Object> iskcf = new HashMap<Integer, Object>();

        String varietyName = request.getParameter("varietyName");
        String material = request.getParameter("material");
        String specification = request.getParameter("specification");
        String factory = request.getParameter("factory");
        String warehouse = request.getParameter("warehouse");
        String orderId = request.getParameter("orderId");
        String companyid = request.getParameter("companyid");
        String companyName = request.getParameter("companyname");
        String stime = request.getParameter("stime");
        String PATTEN_DEFAULT_YMD = "yyyy-MM-dd";
        // 当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(PATTEN_DEFAULT_YMD);
        // 获取今天的日期
        String nowDay = sf.format(now);
        int today = 1;
        if (request.getParameter("stime") != null) {
            if (!nowDay.equals(stime)) {
                today = 0;
            }
        }

        if (companyid == null) {
            companyid = "";
        }
        if (companyName == null) {
            companyName = "";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("varietyName", varietyName);
        map.put("material", material);
        map.put("specification", specification);
        map.put("factory", factory);
        map.put("warehouse", warehouse);
        map.put("orderId", orderId);
        map.put("company", companyid);
        map.put("pmid", pmid);
        map.put("stime", stime);

        if (companyName != "" && companyid == "") {
            List<SysCompany> companys = sysCompanyMapper.selectCompanyByName(companyName);
            var comids = "";
            for (SysCompany com : companys) {
                comids += "'" + com.getId() + "',";
            }
            comids = comids != "" ? comids.substring(0, comids.length() - 1) : "0";
            map.put("comids", comids);

            System.out.println("comids=" + comids);
        }

        List<ScShelfResource> scResourceList = scShelfResourceMapper.getExcessResource(map);
        String ComNameIDs = "";

        System.out.println("scResourceList=" + scResourceList);

        for (int i = scResourceList.size() - 1; i >= 0; i--) {
            BigDecimal sold_sum_yfk2 = scOrderListDetailMapper.getSumbyResourceId(scResourceList.get(i).getId(), pmid);
            Double sold_sum_yfk = sold_sum_yfk2 != null ? sold_sum_yfk2.doubleValue() : 0;
            Double sold_sum_k = sub(scResourceList.get(i).getNum(), sold_sum_yfk);

            Double sold_sum_y = scOrderListDetailMapper.selectSumbyresourceId(scResourceList.get(i).getId(), 3, pmid)
                    .doubleValue();

            System.out.println("ID=" + scResourceList.get(i).getId());
            System.out.println("sold_sum_y=" + sold_sum_y);
            System.out.println("sold_sum=" + scResourceList.get(i).getNum());

            if (sold_sum_y <= scResourceList.get(i).getNum()) {
                scResourceList.remove(i);
                // IDS.add(i);
            } else {
                scResourceList.get(i).setNum(sold_sum_k);
            }
        }

        for (ScShelfResource res : scResourceList) {
            ScPurchasBase purchasbase = scPurchasBaseMapper.selectByResourceId(res.getId());
            Byte isShenhe = purchasbase == null ? 0 : purchasbase.getType();
            shenhe.put(res.getId(), isShenhe);
            // if(isShenhe!=0 && isShenhe!=2){
            // continue;
            // }
            Double weight = (double) 1;

            if (res.getVid() == 1) {
                ScPieceWeightManage weightManage = scPieceWeightManageMapper.getByGcShortSpec(res.getFactory(),
                        res.getSpecification(), pmid);
                if (weightManage != null) {
                    weight = weightManage.getWeight();
                }
                resIds.put(res.getId().toString(), Math.floor(div(res.getNum(), weight)));
                res.setNum(Math.floor(Double.parseDouble(replace(Double.toString(div(res.getNum(), weight))))));
            } else {
                resIds.put(res.getId().toString(), res.getNum());
                res.setNum(res.getNum());
            }

            Integer qdcount = scOrderListBaseMapper.getDQCountByResourceId(pmid, res.getId());

            System.out.println("qdcount=" + qdcount);
            if (qdcount > 0) {
                iskcf.put(res.getId(), 1);
            } else {
                iskcf.put(res.getId(), 0);
            }

            for (ScOrderListDetail detail : res.getScOrderListDetail()) {
                if (detail.getExistSaveAction() == 3) {
                    List<LogisticsPurchase> purchaselist = logisticsPurchaseMapper
                            .selectByOrderDetailId(detail.getId());
                    if (purchaselist != null) {
                        String thd_detail = "";
                        String cgd_detail = "";
                        String cgd_num = "0";
                        for (LogisticsPurchase purchase : purchaselist) {
                            if (purchase.getKind() == 0) {
                                if (res.getVid() == 1) {
                                    thd_detail += replace(Double.toString(div(purchase.getNum(), weight))) + ",";
                                } else {
                                    thd_detail += replace(purchase.getNum().toString()) + ",";
                                }
                            } else if (purchase.getKind() == 1) {
                                if (res.getVid() == 1) {
                                    cgd_detail += replace(Double.toString(div(purchase.getNum(), weight))) + ",";
                                    cgd_num = replace(Double.toString(
                                            add(Double.parseDouble(cgd_num), div(purchase.getNum(), weight))));
                                } else {
                                    cgd_detail += replace(purchase.getNum().toString()) + ",";
                                    cgd_num = replace(
                                            Double.toString(add(Double.parseDouble(cgd_num), purchase.getNum())));
                                }
                            }
                        }
                        if (!thd_detail.equals("")) {
                            thd_detail = thd_detail.substring(0, thd_detail.length() - 1);
                        }
                        if (!cgd_detail.equals("")) {
                            cgd_detail = cgd_detail.substring(0, cgd_detail.length() - 1);
                        }
                        thd.put(detail.getId(), thd_detail);
                        cgd.put(detail.getId(), cgd_detail);
                        cgnum.put(detail.getId(), cgd_num);

                        if (res.getVid() == 1) {
                            heji.put(detail.getId(), sub(detail.getPiece(), Double.parseDouble(cgd_num)));
                        } else {
                            heji.put(detail.getId(), sub(detail.getNum(), Double.parseDouble(cgd_num)));
                        }
                    }
                }

                Double sum = (double) 0;
                ComNameIDs += detail.getPurchaseMid() + ",";
                if (res.getVid().equals(1)) {
                    if (detail.getExtractPiece() == null || detail.getExtractPiece().equals("")) {
                        detail.setExtractPiece(replace(detail.getPiece().toString()));
                    }
                    String[] numArray = StringUtils.split(detail.getExtractPiece(), ",");
                    for (int i = 0; i < numArray.length; i++) {
                        sum = add(sum, Double.parseDouble(numArray[i]));
                    }
                    detailIds.put(detail.getId().toString(), replace(sum.toString()));
                    extractIds.put(detail.getId().toString(), detail.getExtractPiece());
                } else {
                    if (detail.getExtractNum() == null || detail.getExtractNum().equals("")) {
                        detail.setExtractNum(replace(detail.getNum().toString()));
                    }
                    String[] numArray = StringUtils.split(detail.getExtractNum(), ",");
                    for (int i = 0; i < numArray.length; i++) {
                        sum = add(sum, Double.parseDouble(numArray[i]));
                    }
                    detailIds.put(detail.getId().toString(), replace(sum.toString()));
                    extractIds.put(detail.getId().toString(), detail.getExtractNum());
                }
            }
        }

        List<SysCompany> companyinfo = null;
        Map<Integer, Object> commap = new HashMap<Integer, Object>();
        if (!ComNameIDs.equals("")) {
            ComNameIDs = ComNameIDs.substring(0, ComNameIDs.length() - 1);
            companyinfo = sysCompanyMapper.seletIDs(ComNameIDs);
            for (SysCompany com : companyinfo) {
                commap.put(com.getId(), com.getComname());
            }
        }

        System.out.println("iskcf=" + iskcf);

        model.addAttribute("iskcf", iskcf);
        model.addAttribute("thd", thd);
        model.addAttribute("cgd", cgd);
        model.addAttribute("cgnum", cgnum);
        model.addAttribute("heji", heji);
        model.addAttribute("shenhe", shenhe);
        model.addAttribute("commap", commap);
        model.addAttribute("resIds", JSONObject.fromObject(resIds).toString());
        model.addAttribute("detailIds", JSONObject.fromObject(detailIds).toString());
        model.addAttribute("extractIds", JSONObject.fromObject(extractIds).toString());
        model.addAttribute("resIds2", resIds);
        model.addAttribute("mark", "resourceindex");
        model.addAttribute("scResourceList", scResourceList);
        model.addAttribute("tmpcompay", invoiceService.getSearchAll());
        model.addAttribute("orderId", orderId);
        model.addAttribute("stime", stime);
        model.addAttribute("today", today);
    }

    // 订单列表
    public void index(HttpServletRequest request, Model model) {
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
        int limit = 15;

        String status = request.getParameter("status");
        String varietyName = request.getParameter("varietyName");
        String Material = request.getParameter("Material");
        String Spec = request.getParameter("Spec");
        String origin_code = request.getParameter("origin_code");
        String stime = request.getParameter("stime");
        String etime = request.getParameter("etime");
        String mid = request.getParameter("companyid");
        String companyName = request.getParameter("companyname");
        String orderId = request.getParameter("orderId");
        String status1 = request.getParameter("status");

        if (status == null || status.equals("all")) {
            status = "";
            status1 = "all";
        }
        if (varietyName == null) {
            varietyName = "";
        }
        if (Material == null) {
            Material = "";
        }
        if (Spec == null) {
            Spec = "";
        }
        if (origin_code == null) {
            origin_code = "";
        }
        if (stime == null) {
            stime = "";
        }
        if (etime == null) {
            etime = "";
        }
        if (mid == null) {
            mid = "";
        }
        if (companyName == null) {
            companyName = "";
        }

        model.addAttribute("stime", stime);
        model.addAttribute("etime", etime);

        if (stime != "") {
            stime += " 00:00:00";
        }
        if (etime != "") {
            etime += " 23:59:59";
        }

        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        Integer comid = loginMessage.getSysCompany().getId();
        Integer pmid = SecurityUserUtil.getPmid();
        Integer pt = comid.equals(pmid) ? 1 : 0;

        if (pt == 0) {
            mid = comid.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("varietyName", varietyName);
        map.put("Material", Material);
        map.put("Spec", Spec);
        map.put("origin_code", origin_code);
        map.put("stime", stime);
        map.put("etime", etime);
        map.put("pmid", pmid);
        map.put("pt", pt);
        map.put("purchase_mid", mid);
        map.put("orderId", orderId);

        if (companyName != "" && mid == "") {
            List<SysCompany> companys = sysCompanyMapper.selectCompanyByName(companyName);
            var comids = "";
            for (SysCompany com : companys) {
                comids += "'" + com.getId() + "',";
            }
            comids = comids != "" ? comids.substring(0, comids.length() - 1) : "0";
            map.put("comids", comids);

            System.out.println("comids=" + comids);
        }

        PageHelper.startPage(page, limit);
        List<ScOrderListBase> scOrderList2 = scOrderListBaseMapper.getOrderList(map);

        PageInfo<ScOrderListBase> pageInfo = new PageInfo<ScOrderListBase>(scOrderList2, CommonConstant.NAVIGATE_PAGES);

        var orderids = "";
        for (ScOrderListBase base : scOrderList2) {
            orderids += "'" + base.getId() + "',";
        }

        System.out.println("orderids=" + orderids);
        orderids = orderids != "" ? orderids.substring(0, orderids.length() - 1) : "0";

        List<ScOrderListBase> scOrderList = scOrderListBaseMapper.getOrderListByIds(orderids);

        // PageInfo<ScOrderListBase> pageInfo = new
        // PageInfo<ScOrderListBase>(scOrderList,CommonConstant.NAVIGATE_PAGES);
        Map<Integer, Object> isht = new HashMap<Integer, Object>();
        String ComNameIDs = "";
        for (ScOrderListBase order : scOrderList) {
            ComNameIDs += order.getPurchaseMid() + ",";
            order.setTotalPrice(Math.ceil(order.getTotalPrice() * 100) / 100);

            Byte action = 1;
            for (ScOrderListDetail detail : order.getScOrderListDetail()) {
                if (detail.getExistSaveAction() != null && detail.getExistSaveAction() == 2) {
                    action = 2;
                    break;
                }
            }
            isht.put(order.getId(), action);
        }

        List<SysCompany> companyinfo = null;
        Map<Integer, Object> commap = new HashMap<Integer, Object>();
        if (!ComNameIDs.equals("")) {
            ComNameIDs = ComNameIDs.substring(0, ComNameIDs.length() - 1);
            companyinfo = sysCompanyMapper.seletIDs(ComNameIDs);
            for (SysCompany com : companyinfo) {
                commap.put(com.getId(), com.getComname());
            }
        }

        Date today = Common.getNowDateShort();
        model.addAttribute("today", today);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("orderlist", scOrderList);
        model.addAttribute("varietyName", varietyName);
        model.addAttribute("Material", Material);
        model.addAttribute("Spec", Spec);
        model.addAttribute("origin_code", origin_code);

        model.addAttribute("mark", "order");
        model.addAttribute("mid", mid);
        model.addAttribute("companyName", companyName);
        model.addAttribute("pt", pt);
        model.addAttribute("status", status1);
        model.addAttribute("commap", commap);
        model.addAttribute("orderId", orderId);
        model.addAttribute("isht", isht);
        model.addAttribute("tmpcompay", invoiceService.getSearchAll());
        String[] allstatus = { "已取消", "待确认", "待付款", "已付款", "已结算" };
        model.addAttribute("allstatus", allstatus);
    }

    // 添加采购单
    public void addpurchase(HttpServletRequest request, Model model) {
        Integer pmid = SecurityUserUtil.getPmid();
        List<ScShelfResource> resourceList = scShelfResourceMapper.selectAllByPmid(pmid);
        model.addAttribute("resourceList", resourceList);
    }

    // 添加采购单
    public int addpurchaseSave(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        Integer pmid = SecurityUserUtil.getPmid();
        Integer resource_id = Integer.parseInt(request.getParameter("resource_id"));
        Double num = Double.parseDouble(request.getParameter("num"));
        ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(resource_id);

        ScPurchasBase purchasBase = new ScPurchasBase();
        purchasBase.setDate(DateUtil.parseDate(DateUtil.today()));
        purchasBase.setResourceId(resource_id);
        purchasBase.setType((byte) 2);
        purchasBase.setKind(2);
        purchasBase.setPurchaseNum(num);
        purchasBase.setPrice(resource.getPrice());
        purchasBase.setDepository(resource.getWarehouse());
        purchasBase.setCreateUserId(userid);
        return scPurchasBaseMapper.insertPurchasBase(purchasBase);
    }

    // 采购单列表
    public void cgindex(HttpServletRequest request, Model model) {
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

        String type = request.getParameter("type");
        if (type == null) {
            type = "2";
        }
        Byte type1 = (byte) Integer.parseInt(type);

        String varietyName = request.getParameter("varietyName");
        String Material = request.getParameter("Material");
        String Spec = request.getParameter("Spec");
        String origin_code = request.getParameter("origin_code");
        String warehouse = request.getParameter("warehouse");
        String stime = request.getParameter("stime");
        String etime = request.getParameter("etime");
        String supplier_mid = request.getParameter("companyid_sup");
        String supplierName = request.getParameter("suppliername");

        // String purchasename = request.getParameter("selected_purchasename");
        String orderId = request.getParameter("orderId");

        if (varietyName == null) {
            varietyName = "";
        }
        if (Material == null) {
            Material = "";
        }
        if (Spec == null) {
            Spec = "";
        }
        if (origin_code == null) {
            origin_code = "";
        }

        if (stime == null) {
            stime = "";
        }
        if (etime == null) {
            etime = "";
        }

        if (stime != "") {
            stime += " 00:00:00";
        }
        if (etime != "") {
            etime += " 23:59:59";
        }

        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        // userSecurityService.loadUserByUsername(loginMessage.getUsername());
        Integer comid = loginMessage.getSysCompany().getId();
        Integer pmid = SecurityUserUtil.getPmid();
        Integer pt = comid.equals(pmid) ? 1 : 0;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type1);
        map.put("varietyName", varietyName);
        map.put("Material", Material);
        map.put("Spec", Spec);
        map.put("origin_code", origin_code);
        map.put("warehouse", warehouse);
        map.put("stime", stime);
        map.put("etime", etime);
        map.put("mid", comid);
        map.put("pmid", pmid);
        map.put("pt", pt);
        map.put("supplier_mid", supplier_mid);
        map.put("supplierName", supplierName);
        map.put("orderId", orderId);
        List<ScPurchasBase> purchaseList = new ArrayList<>();
        if (pt == 1) {
            // purchaseList=scOrderListBaseMapper.getDetaiLogisticsList(map);

            purchaseList = scPurchasBaseMapper.getPurchasBaseList(map);

        }

        /*
         * String ComNameIDs=""; for (ScOrderListBase order : purchaseList) {
         * ComNameIDs+=order.getPurchaseMid()+","; }
         * 
         * List<SysCompany> companyinfo=null; Map<Integer,Object> commap = new
         * HashMap<Integer, Object>(); if(!ComNameIDs.equals("")){
         * ComNameIDs=ComNameIDs.substring(0, ComNameIDs.length()-1);
         * companyinfo=sysCompanyMapper.seletIDs(ComNameIDs); for(SysCompany
         * com:companyinfo){ commap.put(com.getId(), com.getComname()); } }
         */

        PageInfo<ScPurchasBase> pageInfo = new PageInfo<ScPurchasBase>(purchaseList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("purchaseList", purchaseList);
        model.addAttribute("type", type1);
        model.addAttribute("mark", "purchase");
        // model.addAttribute("commap", commap);
        model.addAttribute("suppliermid", supplier_mid);
        model.addAttribute("supplierName", supplierName);
        // model.addAttribute("purchasename", purchasename);
        model.addAttribute("orderId", orderId);
        model.addAttribute("tmpcompay", invoiceService.getSearchAll());
    }

    // 订单详情界面
    public void detail(HttpServletRequest request, HttpServletResponse res, Model model) throws IOException {
        Integer pmid = SecurityUserUtil.getPmid();
        ScOrderListBase scOrderList = new ScOrderListBase();

        if (request.getParameter("id") != null) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            scOrderList = scOrderListBaseMapper.getOrderDetailById(id);
        }
        if (request.getParameter("orderId") != null) {
            String orderId = request.getParameter("orderId");
            scOrderList = scOrderListBaseMapper.getOrderByorderId(orderId);
        }

        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer mid = session.getSysCompany().getId();
        Integer pt = mid.equals(pmid) ? 1 : 0;

        System.out.println("pmid=" + pmid);
        System.out.println("scOrderList=" + scOrderList);

        if (!pmid.equals(scOrderList.getPmid()) || (!mid.equals(scOrderList.getPurchaseMid()) && pt == 0)) {
            res.sendRedirect("/scorderlist/index");
        }

        // 供应方
        Integer sales_mid = scOrderList.getPmid();
        SysCompany salesCompany = new SysCompany();
        salesCompany = sysCompanyMapper.selectByPrimaryKey(sales_mid);

        // 采购方
        Integer purchase_mid = scOrderList.getPurchaseMid();
        SysCompany purchaseCompany = new SysCompany();
        purchaseCompany = sysCompanyMapper.selectByPrimaryKey(purchase_mid);
        String provice = "";

        if (isNumeric(purchaseCompany.getState())) {
            Province2Area province2Area = province2AreaMapper
                    .selectByProviceId(Integer.parseInt(purchaseCompany.getState()));
            provice = province2Area == null ? "" : province2Area.getProvicename();
        } else {
            provice = purchaseCompany.getState();
        }

        Integer link_user_id = scOrderList.getCreateUserId();

        SysAdminuser linkUser = new SysAdminuser();
        if (link_user_id > 0) {
            linkUser = sysAdminuserMapper.selectByPrimaryKey(link_user_id);
        } else {
            linkUser = sysAdminuserMapper.selectMainAdminuserByComid(purchase_mid);
        }

        // 采购方
        Integer iscaigou = 0;
        if (purchase_mid.equals(mid)) {
            iscaigou = 1;
        }

        Integer thdCount = scOrderListDetailMapper.getDetailCountByAction(scOrderList.getOrderId());

        Date today = Common.getNowDateShort();
        model.addAttribute("today", today);
        model.addAttribute("orderInfo", scOrderList);
        model.addAttribute("salesCompany", salesCompany);
        model.addAttribute("purchaseCompany", purchaseCompany);
        model.addAttribute("linkUser", linkUser);
        model.addAttribute("iscaigou", iscaigou);
        model.addAttribute("thdCount", thdCount);
        model.addAttribute("mark", "order");
        model.addAttribute("provice", provice);
        Date datenow = new Date();
        model.addAttribute("datenow", datenow);
        model.addAttribute("pt", pt);
    }

    // 获取件重
    public HashMap<String, Object> getWeightNum(HttpServletRequest request, HttpServletResponse response)
            throws ParseException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Integer resource_id = request.getParameter("resource_id") == null || request.getParameter("resource_id") == ""
                ? 0
                : Integer.parseInt(request.getParameter("resource_id"));
        String factory = request.getParameter("factory");
        String specification = request.getParameter("specification");
        Integer pmid = SecurityUserUtil.getPmid();
        ScPieceWeightManage weightManage = scPieceWeightManageMapper.getByGcShortSpec(factory, specification, pmid);
        Double weight = (double) 0;
        if (weightManage != null) {
            weight = weightManage.getWeight();
        }
        map.put("weight", weight);

        ScShelfResource scShelfResource = scShelfResourceMapper.selectByPrimaryKey(resource_id);
        map.put("surplus", sub(scShelfResource.getNum(), scShelfResource.getSoldNum()));

        return map;
    }

    public ScOrderListDetail getDetailByid(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        return scOrderListDetailMapper.getDetailByid(id);
    }

    public ScOrderListDetail getDetaiLogisticsByid(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer pmid = SecurityUserUtil.getPmid();
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer mid = session.getSysCompany().getId();
        Integer pt = mid.equals(pmid) ? 1 : 0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("pmid", pmid);
        map.put("purchase_mid", mid);
        map.put("pt", pt);

        ScOrderListDetail detail = scOrderListDetailMapper.getDetaiLogisticsByid(map);

        /*
         * Double
         * resnum=scShelfResourceMapper.selectByPrimaryKey(detail.getResourceId()).
         * getNum(); BigDecimal num_sum =
         * logisticsPurchaseMapper.getSumNumByResid(detail.getResourceId());
         * if(num_sum!=null && !num_sum.equals("")){ resnum =
         * sub(resnum,num_sum.doubleValue()); } Map<String,Object> map2 = new
         * HashMap<String, Object>(); map2.put("detail",detail);
         * map2.put("resNum",resnum);
         * 
         * System.out.println(resnum+"############");
         * System.out.println(num_sum+"############");
         */

        return detail;
    }

    // 订单修改
    public int updateDetailById(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String extract_piece = request.getParameter("extract_piece");
        String extract_num = request.getParameter("extract_num");
        String extract_price = request.getParameter("price");
        Integer detail_id = Integer.parseInt(request.getParameter("detail_id"));
        Double piece = Double.parseDouble(request.getParameter("order_piece"));
        Double num = Double.parseDouble(request.getParameter("order_num"));
        ScOrderListBase scOrderListBase = scOrderListBaseMapper.getDetailById(detail_id);
        Double oldprice = scOrderListBase.getScOrderListDetail().get(0).getPrice();// 成交价
        Double ynum = scOrderListBase.getScOrderListDetail().get(0).getNum();// 原重量
        Integer resource_id = scOrderListBase.getScOrderListDetail().get(0).getResourceId();
        double total_price = scOrderListBase.getTotalPrice();
        Integer pmid = SecurityUserUtil.getPmid();

        Smownmember smownmember = smownmemberMapper.selectByPmidAndUid(pmid, scOrderListBase.getCreateUserId());
        System.out.println("smownmember=" + smownmember);
        System.out.println("getPreferentialAmount=" + smownmember.getPreferentialAmount());
        Double amount = smownmember != null
                ? smownmember.getPreferentialAmount() != null ? smownmember.getPreferentialAmount() : 0
                : 0;
        // 检查是否超量
        Byte type = checkOrderSell(resource_id);

        Double price = oldprice;
        if (!extract_price.equals("")) {
            price = Double.parseDouble(extract_price);
        }

        if (scOrderListBase.getTicketType() == 1) { // 一票制加运费
            Double frieight = scOrderListBase.getFreight();
            price = add(price, frieight);
        }

        if (scOrderListBase.getScOrderListDetail().size() == 1) {
            total_price = sub(total_price, mul(oldprice, ynum));
            total_price = add(total_price, mul(price, num));
        } else {
            total_price = mul(price, num);
        }
        scOrderListBaseMapper.updateTotalPriceById(total_price, scOrderListBase.getId());
        scOrderListDetailMapper.updateDetailById(detail_id, price, piece, num, extract_piece, extract_num);

        // 检查是否超量
        Byte type2 = checkOrderSell(resource_id);

        if (type == 1 && type2 == 2) {
             sendSms(resource_id, pmid, scOrderListBase.getPurchaseMid());
        }

        ScOrderListBase scOrderListBase2 = scOrderListBaseMapper.getOrderById(scOrderListBase.getId());
        Boolean flag = true;
        for (ScOrderListDetail detail : scOrderListBase2.getScOrderListDetail()) {
            ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(detail.getResourceId());
            if (detail.getPrice() < sub(resource.getPrice(), amount)) {
                flag = false;
            }
        }

        // 议价优惠幅度，更新为待付款状态
        if (flag) {
            scOrderListBaseMapper.updateOrderStatus(scOrderListBase.getId(), (byte) 2);
        }

        return 1;
    }

    // 发送短信
    public void sendSms(Integer resources_id, Integer pmid, Integer company_id) {
        ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(resources_id);
        SysCompany companyinfo = sysCompanyMapper.selectByPrimaryKey(company_id);

        String sms_content = "&content=";// 短信内容
        sms_content += resource.getFactory() + resource.getVarietyName() + resource.getMaterial()
                + resource.getSpecification();

        List<SysAdminuser> cl_adminuser = sysAdminuserMapper.selecUserInfoByPmidAndRights(pmid);
        for (int i = 0; i < cl_adminuser.size(); i++) {
            cl_adminuser.get(i).getMobile();
            if (!cl_adminuser.get(i).getMobile().equals("")) {
                // String send_sms_conten_param = sms_conten_param +
                // cl_adminuser.get(i).getMobile();
                String send_sms_conten_param = sms_conten_param + cl_adminuser.get(i).getMobile();
                send_sms_conten_param += sms_content + "," + companyinfo.getComname()
                        + "客户下订单超量未付款，为避免提货单生成延迟，请及时与客户联系！";
                String returnmessage = HttpUtils.sendGet(sms_content_url, send_sms_conten_param);
                System.out.println("returnmessage=" + returnmessage);
                // JSONObject userJson = JSONObject.fromObject(returnmessage);
                // com.alibaba.fastjson.JSONObject userJson =
                // com.alibaba.fastjson.JSONObject.parseObject(returnmessage);
                // returnmessage = userJson.toString();
            }
        }

    }

    public double div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        // BigDecimal b3 = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP);
        return b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public double sum(String[] array) {
        Double sum = (double) 0;
        for (int i = 0; i < array.length; i++) {
            BigDecimal b1 = new BigDecimal(sum);
            BigDecimal b2 = new BigDecimal(array[i]);
            sum = b1.add(b2).doubleValue();
        }
        return sum;
    }

    public double sum2(List<String> array) {
        Double sum = (double) 0;
        for (int i = 0; i < array.size(); i++) {
            BigDecimal b1 = new BigDecimal(sum);
            BigDecimal b2 = new BigDecimal(array.get(i));
            sum = b1.add(b2).doubleValue();
        }
        return sum;
    }

    // 采购单审核
    public int shenheOrderPurchase(HttpServletRequest request) throws ParseException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer status = Integer.parseInt(request.getParameter("status"));
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        ScPurchasBase scPurchasBase = new ScPurchasBase();

        if (status == 3) {
            // 审核不通过
            scPurchasBase.setId(id);
            scPurchasBase.setType((byte) 3);
            scPurchasBase.setAuditUserId(userid);
            return scPurchasBaseMapper.updatePurchaseById(scPurchasBase);
        } else {
            Double purchase_amount = Double.parseDouble(request.getParameter("purchase_amount"));
            String supplier_name = request.getParameter("supplier_name");
            if (supplier_name != "") {
                // 采购公司不存在则注册
                Integer supplier_id = request.getParameter("supplier_id").equals("")
                        ? companyInfoService.insertbycompanyname(supplier_name)
                        : Integer.parseInt(request.getParameter("supplier_id"));

                scPurchasBase.setId(id);
                // 审核通过
                if (status == 2) {
                    scPurchasBase.setType((byte) 4);
                    scPurchasBase.setAuditUserId(userid);
                }
                scPurchasBase.setPurchaseAmount(purchase_amount);
                scPurchasBase.setPurchaseUserId(userid);
                scPurchasBase.setSupplierMid(supplier_id);
                scPurchasBase.setSupplierName(supplier_name);
                return scPurchasBaseMapper.updatePurchaseById(scPurchasBase);
            } else {
                return 0;
            }
        }

    }

    // 采购单审核不通过
    public int rejectOrderPurchase(HttpServletRequest request) throws ParseException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        return logisticsPurchaseMapper.purchaseMoneyMach(id, (byte) 3);
    }

    // 拆分提货单
    public int setPurchaseList(HttpServletRequest request) throws ParseException {
        String cgd = request.getParameter("cgd_list");
        String[][] cgd_list = JSON.parseObject(cgd, String[][].class);
        String thd = request.getParameter("thd_list");
        String[][] thd_list = JSON.parseObject(thd, String[][].class);

        Double pur_sum = Double.parseDouble(request.getParameter("pur_sum"));
        Integer rid = Integer.parseInt(request.getParameter("rid"));
        ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(rid);

        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        Integer pmid = SecurityUserUtil.getPmid();

        Double weight = (double) 1;
        if (resource.getVid() == 1) {
            ScPieceWeightManage weightManage = scPieceWeightManageMapper.getByGcShortSpec(resource.getFactory(),
                    resource.getSpecification(), pmid);
            if (weightManage != null) {
                weight = weightManage.getWeight();
            }
            pur_sum = mul(pur_sum, weight);
        }

        for (int i = 0; i < thd_list.length; i++) {
            Integer detail_id = Integer.parseInt(thd_list[i][0]);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("detail_id", detail_id);
            logisticsPurchaseMapper.deletePurchaseByIds(map);

            ScOrderListDetail detail = scOrderListDetailMapper.selectByPrimaryKey(detail_id);
            ScPurchasBase scPurchasBase = scPurchasBaseMapper.selectByResourceId(detail.getResourceId());
            if (scPurchasBase != null) {
                scPurchasDetailMapper.deleteBybaseId(scPurchasBase.getId());
                scPurchasBaseMapper.deleteByPrimaryKey(scPurchasBase.getId());

            }

            if (!thd_list[i][1].equals("") && !thd_list[i][1].equals("0")) {

                // ScShelfResource resource
                // =scShelfResourceMapper.selectByPrimaryKey(detail.getResourceId());

                // List<LogisticsPurchase>
                // purchaselist=logisticsPurchaseMapper.selectByOrderDetailId(detail_id);
                // logisticsPurchaseMapper.deletePurchaseByIds(map);

                String[] resourceArray = StringUtils.split(thd_list[i][1], ",");

                // 提货单
                for (int j = 0; j < resourceArray.length; j++) {
                    LogisticsPurchase purchase = new LogisticsPurchase();
                    purchase.setOrderDetailid(detail_id);
                    purchase.setOrderId(detail.getOrderBaseid());
                    purchase.setResourceId(detail.getResourceId());
                    purchase.setExtractId(get_order_tdid(detail.getResourceId()));
                    purchase.setType((byte) 1);
                    purchase.setKind((byte) 0);
                    purchase.setPrice(resource.getPrice());
                    purchase.setDepository(resource.getWarehouse());
                    purchase.setCreateUserId(userid);
                    Double num = Double.parseDouble(resourceArray[j]);
                    if (resource.getVid() == 1) {
                        num = mul(num, weight);
                    }
                    purchase.setNum(num);
                    logisticsPurchaseMapper.insertLogistics(purchase);
                }
                scOrderListDetailMapper.updateSaveActionByid(detail_id, (byte) 3);
            }
        }

        int baseid = 0;
        if (pur_sum > 0) {
            ScPurchasBase purchasbase = new ScPurchasBase();
            purchasbase.setDate(Common.getNowDateShort());
            purchasbase.setResourceId(rid);
            purchasbase.setType((byte) 2);
            purchasbase.setKind(1);
            purchasbase.setPurchaseNum(pur_sum);
            purchasbase.setPrice(resource.getPrice());
            purchasbase.setDepository(resource.getWarehouse());
            purchasbase.setCreateUserId(userid);
            scPurchasBaseMapper.insert(purchasbase);
            baseid = purchasbase.getId();
        }

        for (int i = 0; i < cgd_list.length; i++) {
            Integer detail_id = Integer.parseInt(cgd_list[i][0]);
            if (!cgd_list[i][1].equals("") && !cgd_list[i][1].equals("0")) {
                ScOrderListDetail detail = scOrderListDetailMapper.selectByPrimaryKey(detail_id);
                // List<LogisticsPurchase>
                // purchaselist=logisticsPurchaseMapper.selectByOrderDetailId(detail_id);

                String[] resourceArray = StringUtils.split(cgd_list[i][1], ",");

                // 提货单
                for (int j = 0; j < resourceArray.length; j++) {
                    LogisticsPurchase purchase = new LogisticsPurchase();
                    String extractId = get_order_tdid(detail.getResourceId());

                    purchase.setOrderDetailid(detail_id);
                    purchase.setOrderId(detail.getOrderBaseid());
                    purchase.setResourceId(detail.getResourceId());
                    purchase.setExtractId(extractId);
                    purchase.setType((byte) 2);
                    purchase.setKind((byte) 1);
                    purchase.setPrice(resource.getPrice());
                    purchase.setDepository(resource.getWarehouse());
                    purchase.setCreateUserId(userid);
                    Double num = Double.parseDouble(resourceArray[j]);
                    if (resource.getVid() == 1) {
                        num = mul(num, weight);
                    }
                    purchase.setNum(num);
                    logisticsPurchaseMapper.insertLogistics(purchase);

                    ScPurchasDetail purchasdetail = new ScPurchasDetail();
                    purchasdetail.setBaseId(baseid);
                    purchasdetail.setOrderId(detail.getOrderBaseid());
                    purchasdetail.setExtractId(extractId);
                    purchasdetail.setPurchaseNum(num);
                    scPurchasDetailMapper.insert(purchasdetail);
                }
                scOrderListDetailMapper.updateSaveActionByid(detail_id, (byte) 3);
            }
        }

        return 1;
    }

    // 超量资源管理取消
    public Map<String, Object> delete_order_resource(HttpServletRequest request) throws ParseException {
        Integer index = Integer.parseInt(request.getParameter("index"));
        Integer detail_id = Integer.parseInt(request.getParameter("detail_id"));
        Integer pmid = SecurityUserUtil.getPmid();
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        int data = 0;
        String user = session.getSysAdminuser().getArealname();
        HashMap<String, Object> result = new HashMap<String, Object>();

        ScOrderListDetail detail = scOrderListDetailMapper.selectByPrimaryKey(detail_id);

        // 取消资源
        if (index == 1) {

            // ScPurchasBase purchasbase =
            // scPurchasBaseMapper.selectByResourceId(detail.getResourceId());
            ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(detail.getResourceId());
            Integer resource_id = resource.getId();

            List<Integer> tbodys = new ArrayList<Integer>();
            List<Integer> trs = new ArrayList<Integer>();

            ScOrderListBase scOrderListBase = scOrderListBaseMapper.getInfoByOrderId(detail.getOrderBaseid());

            // 订单资源数量
            Integer dcount = scOrderListDetailMapper.getDetailCountByorderId(detail.getOrderBaseid());

            // 一票制加运费
            Double price = detail.getPrice();
            if (scOrderListBase.getTicketType() == 1) {
                Double frieight = scOrderListBase.getFreight();
                price = add(price, frieight);
            }
            Double money = dcount > 1 ? mul(price, detail.getNum()) : scOrderListBase.getTotalPrice();
            Integer company_id = detail.getPurchaseMid();

            // 返回余额
            ScCompanyBalance companeybalance = scCompanyBalanceMapper.getBalanceByComid(pmid, company_id);
            SysCompany companyinfo = sysCompanyMapper.selectByPrimaryKey(company_id);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            String dateStr = df.format(new Date());
            if (companeybalance != null) {
                Double balance = companeybalance.getBalance();
                balance = add(balance, money);
                scCompanyBalanceMapper.updateBalanceByComid(pmid, company_id, balance);
            } else {
                companeybalance = new ScCompanyBalance();
                companeybalance.setPmid(pmid);
                companeybalance.setCompanyId(company_id);
                companeybalance.setCompanyName(companyinfo.getComname());
                companeybalance.setBalance(money);
                companeybalance.setUserId(userid);
                companeybalance.setInputUser(user);

                companeybalance.setInputDate(df.parse(dateStr));
                scCompanyBalanceMapper.insert(companeybalance);
            }

            // 退款记录
            FinancialInfo financialInfo = new FinancialInfo();
            financialInfo.setCompany_id(companyinfo.getId());
            financialInfo.setCompany_name(companyinfo.getComname());
            financialInfo.setMoney(money);
            financialInfo.setBill(3);
            financialInfo.setBill_type(1);
            financialInfo.setUser_id(userid);
            financialInfo.setInput_user(user);
            financialInfo.setInput_date(df.parse(dateStr));
            financialInfo.setRemark("超量资源取消");
            financialInfo.setPmid(pmid);
            financialMapper.financialInsert(financialInfo);

            // 删除资源提货单
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("detail_id", detail_id);
            logisticsPurchaseMapper.deletePurchaseByIds(map);

            // 资源详情删除
            ScOrderListBase scOrderListBase2 = new ScOrderListBase();
            scOrderListBase2.setId(scOrderListBase.getId());
            ScOrderListDetail scOrderListDetail = new ScOrderListDetail();
            scOrderListDetail.setId(detail.getId());
            scOrderListDetail.setIsDeleted((byte) 1);
            scOrderListDetailMapper.updateByPrimaryKey(scOrderListDetail);

            if (dcount > 1) {

                Double total_price = scOrderListBase.getTotalPrice();
                total_price = sub(total_price, money);
                // 修改订单下单总金额
                scOrderListBase2.setTotalPrice(total_price);
                // 修改订单的件数和重量
                if (resource.getVid() == 1) {
                    scOrderListBase2.setPiece(sub(scOrderListBase.getPiece(), detail.getPiece()));
                }
                scOrderListBase2.setNum(sub(scOrderListBase.getNum(), detail.getNum()));
                scOrderListBaseMapper.updateByPrimaryKey(scOrderListBase2);
            } else {
                // 只有一个资源，取消订单
                scOrderListBaseMapper.updateOrderStatus(scOrderListBase.getId(), (byte) 0);
            }

            // 检查是否超量
            Byte type = checkOrderSell(resource_id);

            // 判断是否超量销售
            BigDecimal res_sum = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 3, pmid);
            Double res_sums = (double) 0;
            if (res_sum != null) {
                res_sums = res_sum.doubleValue();
            }

            if (resource.getNum() >= res_sums) {
                // 资源销售量
                scShelfResourceMapper.updateSoldNumByRid(resource_id, res_sums);
                // 非超量销售
                result.put("data", 2);
                // 订单详情修改状态
                tbodys.add(resource.getId());
                scOrderListDetailMapper.updateSaveActionByrsid(resource.getId(), (byte) 1, (byte) 1, pmid);
            } else {
                BigDecimal th_sum = logisticsPurchaseMapper.getSumNumByResid(resource_id);
                Double allsum = (double) 0;
                if (th_sum != null && !th_sum.equals("")) {
                    allsum = add(allsum, th_sum.doubleValue());
                }
                // 超量销售需重新拆分
                if (allsum < resource.getNum()) {
                    result.put("data", 3);
                }
            }

            trs.add(detail.getId());
            result.put("tbodys", tbodys);
            result.put("trs", trs);
            return result;
        } else if (index == 2) {
            // 取消订单
            String order_id = detail.getOrderBaseid();
            String cl_sold = "";
            List<Integer> tbodys = new ArrayList<Integer>();
            List<Integer> trs = new ArrayList<Integer>();

            List<ScOrderListDetail> detailist = scOrderListDetailMapper.selectAllByOrderId(order_id);

            for (ScOrderListDetail detail2 : detailist) {
                if (detail2.getExistSaveAction() == 3) {
                    result.put("data", 1);
                    return result;
                }
            }

            for (ScOrderListDetail detail3 : detailist) {
                ScShelfResource resource3 = scShelfResourceMapper.selectByPrimaryKey(detail3.getResourceId());
                Integer resource_id = resource3.getId();

                ScOrderListBase scOrderListBase = scOrderListBaseMapper.getInfoByOrderId(detail3.getOrderBaseid());

                // 订单资源数量
                // Integer
                // dcount=scOrderListDetailMapper.getDetailCountByorderId(detail3.getOrderBaseid());

                Double price = detail3.getPrice();
                if (scOrderListBase.getTicketType() == 1) {
                    Double frieight = scOrderListBase.getFreight();
                    price = add(price, frieight);
                }
                Double money = mul(price, detail3.getNum());

                Integer company_id = detail3.getPurchaseMid();

                // 返回余额
                ScCompanyBalance companeybalance = scCompanyBalanceMapper.getBalanceByComid(pmid, company_id);
                SysCompany companyinfo = sysCompanyMapper.selectByPrimaryKey(company_id);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                String dateStr = df.format(new Date());
                if (companeybalance != null) {
                    Double balance = companeybalance.getBalance();
                    balance = add(balance, money);
                    scCompanyBalanceMapper.updateBalanceByComid(pmid, company_id, balance);
                } else {
                    companeybalance = new ScCompanyBalance();
                    companeybalance.setPmid(pmid);
                    companeybalance.setCompanyId(company_id);
                    companeybalance.setCompanyName(companyinfo.getComname());
                    companeybalance.setBalance(money);
                    companeybalance.setUserId(userid);
                    companeybalance.setInputUser(user);
                    companeybalance.setInputDate(df.parse(dateStr));
                    scCompanyBalanceMapper.insert(companeybalance);
                }
                // 退款记录
                FinancialInfo financialInfo = new FinancialInfo();
                financialInfo.setCompany_id(companyinfo.getId());
                financialInfo.setCompany_name(companyinfo.getComname());
                financialInfo.setMoney(money);
                financialInfo.setBill(3);
                financialInfo.setBill_type(1);
                financialInfo.setUser_id(userid);
                financialInfo.setInput_user(user);
                financialInfo.setInput_date(df.parse(dateStr));
                financialInfo.setRemark("超量资源取消");
                financialInfo.setPmid(pmid);
                financialMapper.financialInsert(financialInfo);

                // 订单取消状态
                scOrderListBaseMapper.updateOrderStatus(scOrderListBase.getId(), (byte) 0);

                // 资源详情表
                // ScOrderListDetail scOrderListDetail = new ScOrderListDetail();
                // scOrderListDetail.setId(detail3.getId());
                // scOrderListDetail.setIsDeleted((byte) 1);
                // scOrderListDetailMapper.updateByPrimaryKey(scOrderListDetail);

                // 检查是否超量
                Byte type = checkOrderSell(resource_id);

                // 删除资源提货单
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("detail_id", detail3.getId());
                logisticsPurchaseMapper.deletePurchaseByIds(map);

                // 判断是否超量销售
                BigDecimal res_sum = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 3, pmid);
                Double res_sums = (double) 0;
                if (res_sum != null) {
                    res_sums = res_sum.doubleValue();
                }

                if (resource3.getNum() >= res_sums) {
                    // 资源销售量
                    scShelfResourceMapper.updateSoldNumByRid(resource_id, res_sums);
                    // 非超量销售
                    result.put("data", 2);
                    // 正常销售
                    tbodys.add(resource3.getId());
                    scOrderListDetailMapper.updateSaveActionByrsid(resource3.getId(), (byte) 1, (byte) 3, pmid);
                } else {
                    // BigDecimal th_sum = logisticsPurchaseMapper.getSumNumByResid(resource_id);
                    // Double allsum = (double) 0;
                    // if (th_sum != null && !th_sum.equals("")) {
                    // allsum = add(allsum, th_sum.doubleValue());
                    // }
                    // 超量销售需重新拆分
                    // if (allsum < resource3.getNum()) {
                    // cl_sold += resource3.getId() + ",";
                    result.put("data", 3);
                    // }
                }
                trs.add(detail3.getId());
            }
            result.put("cl_sold", cl_sold);
            result.put("tbodys", tbodys);
            result.put("trs", trs);
            return result;
        }
        return result;
    }

    // 拆分提货单
    public int addDetailPurchase(HttpServletRequest request) throws ParseException {
        Integer detail_id = Integer.parseInt(request.getParameter("detail_id"));
        String update = request.getParameter("update");
        String[][] updates = JSON.parseObject(update, String[][].class);
        String insert = request.getParameter("insert");
        String[][] inserts = JSON.parseObject(insert, String[][].class);
        String ids = request.getParameter("ids");
        Integer[] idss = JSON.parseObject(ids, Integer[].class);
        ScOrderListDetail scOrderListDetail = scOrderListDetailMapper.getDetailByid(detail_id);

        Double resnum = scShelfResourceMapper.selectByPrimaryKey(scOrderListDetail.getResourceId()).getNum();

        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();

        Double allsum = (double) 0;
        for (int i = 0; i < inserts.length; i++) {
            Byte type = (byte) (Integer.parseInt(inserts[i][0]) == 1 ? 2 : 1);
            if (type == 1) {
                allsum = add(allsum, Double.parseDouble(inserts[i][1]));
            }
        }

        for (int i = 0; i < updates.length; i++) {
            Byte type = (byte) (Integer.parseInt(updates[i][1]) == 1 ? 2 : 1);
            if (type == 1) {
                allsum = add(allsum, Double.parseDouble(updates[i][2]));
            }
        }

        double sum = allsum;
        BigDecimal num_sum = logisticsPurchaseMapper.getSumNumByResid(scOrderListDetail.getResourceId());
        if (num_sum != null && !num_sum.equals("")) {
            sum = add(allsum, num_sum.doubleValue());
        }

        if (sum > resnum) {
            return 2;
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("detail_id", detail_id);
        map.put("ids", idss);
        logisticsPurchaseMapper.deletePurchaseByIds(map);

        for (int i = 0; i < inserts.length; i++) {
            LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
            logisticsPurchase.setOrderDetailid(detail_id);
            logisticsPurchase.setOrderId(scOrderListDetail.getOrderBaseid());
            logisticsPurchase.setResourceId(scOrderListDetail.getResourceId());
            logisticsPurchase.setExtractId(get_order_tdid(scOrderListDetail.getResourceId()));
            Byte type = (byte) (Integer.parseInt(inserts[i][0]) == 1 ? 2 : 1);
            logisticsPurchase.setType(type);
            Byte kind = (byte) (Integer.parseInt(inserts[i][0]) == 1 ? 1 : 0);
            logisticsPurchase.setKind(kind);
            logisticsPurchase.setActualNum((double) 0);
            logisticsPurchase.setNum(Double.parseDouble(inserts[i][1]));
            logisticsPurchase.setPrice(scOrderListDetail.getPrice());
            logisticsPurchase.setDepository(scOrderListDetail.getScShelfResource().getWarehouse());
            logisticsPurchase.setCreateUserId(userid);
            logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
        }

        for (int i = 0; i < updates.length; i++) {
            Byte type = (byte) (Integer.parseInt(updates[i][1]) == 1 ? 2 : 1);
            Byte kind = (byte) (Integer.parseInt(updates[i][1]) == 1 ? 1 : 0);
            logisticsPurchaseMapper.updateLogisticsById(Integer.parseInt(updates[i][0]), type, kind,
                    Double.parseDouble(updates[i][2]));
        }

        return scOrderListDetailMapper.updateSaveActionByid(detail_id, (byte) 3);
    }

    // 取消
    public int updateOrderStatus(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Byte status = (byte) Integer.parseInt(request.getParameter("status"));
        scOrderListBaseMapper.updateOrderStatus(id, status);
        ScOrderListBase base = scOrderListBaseMapper.selectByPrimaryKey(id);

        // 检查是否超量
        checkOrderSale(base.getOrderId());

        // 资源详情删除
        scOrderListDetailMapper.deleteByOrderId(base.getOrderId());

        return 1;
    }

    // 订单确认
    public Integer updateOrderQren(HttpServletRequest request) throws ParseException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        ScOrderListBase scOrderListBase = scOrderListBaseMapper.getOrderById(id);
        Double totalPrice = scOrderListBase.getTotalPrice();
        Integer pmid = scOrderListBase.getPmid();
        Integer company_id = scOrderListBase.getPurchaseMid();
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        String user = session.getSysAdminuser().getArealname();

        // 检查余额+额度
        Boolean flag = false;
        Smownmember smownmember = smownmemberMapper.selectByPmidAndMid(pmid, company_id);
        ScCompanyBalance companeybalance = scCompanyBalanceMapper.getBalanceByComid(pmid, company_id);

        if (companeybalance == null) {
            SysCompany companyinfo = sysCompanyMapper.selectByPrimaryKey(company_id);
            companeybalance = new ScCompanyBalance();
            companeybalance.setPmid(pmid);
            companeybalance.setCompanyId(company_id);
            companeybalance.setCompanyName(companyinfo.getComname());
            companeybalance.setBalance((double) 0);
            companeybalance.setUserId(userid);
            companeybalance.setInputUser(user);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            String dateStr = df.format(new Date());
            companeybalance.setInputDate(df.parse(dateStr));
            scCompanyBalanceMapper.insert(companeybalance);
        }

        Double balanceAndCredit = companeybalance.getBalance();

        if (smownmember != null && smownmember.getMember_type().equals(2)) {
            balanceAndCredit = add(balanceAndCredit, smownmember.getCredit_amount());
        }

        if (balanceAndCredit >= scOrderListBase.getTotalPrice()) {
            flag = true;
        } else {
            flag = false;
        }

        // 资源调价
        for (ScOrderListDetail scOrderList : scOrderListBase.getScOrderListDetail()) {
            ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(scOrderList.getResourceId());
            if (!resource.getPrice().equals(scOrderList.getOriginalPrice()) && scOrderList.getOriginalPrice() != null) {
                return 2;
            }
        }

        if (flag) {
            Double balance = sub(companeybalance.getBalance(), totalPrice);
            scCompanyBalanceMapper.updateBalanceByComid(pmid, company_id, balance);

            for (ScOrderListDetail scOrderList : scOrderListBase.getScOrderListDetail()) {
                Integer resource_id = scOrderList.getResourceId();
                // 检查是否超量
                Byte save_action = checkOrderSell(resource_id);
                scOrderListDetailMapper.updateSaveActionByid(scOrderList.getId(), save_action);

                if (scOrderList.getNum() != null && scOrderList.getNum() >= 0) {

                    ScShelfResource scShelfResource = scShelfResourceMapper.selectByPrimaryKey(resource_id);
                    Double sold_num = scShelfResource.getSoldNum();
                    Double salses_num = scShelfResource.getNum();
                    Double order_num = scOrderList.getNum();
                    Double num_dif = salses_num - sold_num - order_num;

                    if (num_dif >= 0) {
                        scShelfResourceMapper.updateSoldNumById(resource_id, order_num);
                    } else {
                        scShelfResourceMapper.updateSoldNumById(resource_id, salses_num - sold_num);
                        // scOrderListDetailMapper.updateSaveActionByrsid(rs_id, save_action);
                    }
                    stopOpenPlateService.overSaleStop(pmid);
                }

                if (scOrderList.getExtractNum() != null && !scOrderList.getExtractNum().equals("")) {
                    String[] numArray = StringUtils.split(scOrderList.getExtractNum(), ",");
                    for (int num = 0; num < numArray.length; num++) {
                        if (numArray[num] != null) {
                            LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                            logisticsPurchase.setOrderDetailid(scOrderList.getId());
                            logisticsPurchase.setOrderId(scOrderListBase.getOrderId());
                            logisticsPurchase.setResourceId(resource_id);
                            logisticsPurchase.setExtractId(get_order_tdid(resource_id));
                            logisticsPurchase.setType((byte) 1);
                            logisticsPurchase.setKind((byte) 0);
                            logisticsPurchase.setActualNum((double) 0);
                            logisticsPurchase.setNum(Double.parseDouble(numArray[num]));
                            logisticsPurchase.setPrice(scOrderList.getScShelfResource().getPrice());
                            logisticsPurchase.setDepository(scOrderList.getScShelfResource().getWarehouse());
                            logisticsPurchase.setCreateUserId(userid);
                            logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
                        }
                    }
                } else {
                    if (scOrderList.getNum() != null && scOrderList.getNum() >= 0) {
                        LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                        logisticsPurchase.setOrderDetailid(scOrderList.getId());
                        logisticsPurchase.setOrderId(scOrderListBase.getOrderId());
                        logisticsPurchase.setResourceId(resource_id);
                        logisticsPurchase.setExtractId(get_order_tdid(resource_id));
                        logisticsPurchase.setType((byte) 1);
                        logisticsPurchase.setKind((byte) 0);
                        logisticsPurchase.setActualNum((double) 0);
                        logisticsPurchase.setNum(scOrderList.getNum());
                        logisticsPurchase.setPrice(scOrderList.getScShelfResource().getPrice());
                        logisticsPurchase.setDepository(scOrderList.getScShelfResource().getWarehouse());
                        logisticsPurchase.setCreateUserId(userid);
                        logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
                    }
                }
            }
            return scOrderListBaseMapper.updateOrderStatus(id, (byte) 3);
        }
        return 0;
    }

    // 出账采购金匹配采购单
    public void purchaseMoneyMach(Integer pmid, Integer supplier_mid, Double money) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();

        /// Integer
        /// purchase_id=logisticsPurchaseMapper.getPurchaseIdBymoney(pmid,supplier_mid,money);

        // if(purchase_id!=null && purchase_id>0){
        // logisticsPurchaseMapper.purchaseMoneyMach(purchase_id,(byte) 5);
        // }

        Integer purchase_id = scPurchasBaseMapper.getPurchaseIdBymoney(pmid, supplier_mid, money);

        if (purchase_id != null && purchase_id > 0) {
            scPurchasBaseMapper.purchaseMoneyMach(purchase_id, (byte) 5);
        }

        // 平台方
        ScCompanyBalance companeybalance = scCompanyBalanceMapper.getBalanceByComid(pmid, pmid);// 余额
        if (companeybalance != null && money > 0 && companeybalance.getBalance() >= money) {
            Double balance = companeybalance.getBalance();
            balance = sub(balance, money);
            scCompanyBalanceMapper.updateBalanceByComid(pmid, pmid, balance);

            // 采购公司
            ScCompanyBalance companeybalance3 = scCompanyBalanceMapper.getBalanceByComid(pmid, supplier_mid);// 余额
            if (companeybalance3 == null && money > 0) {
                ScCompanyBalance companeybalance2 = new ScCompanyBalance();
                String user = session.getSysAdminuser().getArealname();
                SysCompany companyinfo = sysCompanyMapper.selectByPrimaryKey(supplier_mid);
                companeybalance2.setPmid(pmid);
                companeybalance2.setCompanyId(supplier_mid);
                companeybalance2.setCompanyName(companyinfo.getComname());
                companeybalance2.setBalance(money);
                companeybalance2.setUserId(userid);
                companeybalance2.setInputUser(user);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                String dateStr = df.format(new Date());
                companeybalance2.setInputDate(df.parse(dateStr));
                scCompanyBalanceMapper.insert(companeybalance2);
            } else if (companeybalance3 != null && money > 0 && companeybalance3.getBalance() >= money) {
                Double balance2 = companeybalance3.getBalance();
                // balance2+=money;
                balance2 = add(balance2, money);
                scCompanyBalanceMapper.updateBalanceByComid(pmid, supplier_mid, balance2);
            }
        }
    }

    // 提货单pdf打印
    public int getLogisticsCount(Integer id) {
        Integer mid = SecurityUserUtil.getPmid();
        LogisticsPurchase logisticsPurchase = logisticsPurchaseMapper.selectByPrimaryKey(id);
        ScOrderListBase scOrderListBase = new ScOrderListBase();

        List<ScShelfResource> resourceList = new ArrayList<>();
        List<LogisticsPurchase> purchaseList = new ArrayList<>();

        if (logisticsPurchase != null) {
            scOrderListBase = scOrderListBaseMapper.getLogisticsByOrderId(mid, logisticsPurchase.getOrderId());

            int sort = 0;
            int kind = 0;
            for (ScOrderListDetail detail : scOrderListBase.getScOrderListDetail()) {
                for (int i = 0; i < detail.getLogisticsPurchase().size(); i++) {
                    if (detail.getLogisticsPurchase().get(i).getId().equals(id)) {
                        sort = i;
                        kind = detail.getLogisticsPurchase().get(i).getKind();
                        break;
                    }
                }
            }
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("kind=" + kind);

            int num = 0;
            for (ScOrderListDetail detail : scOrderListBase.getScOrderListDetail()) {
                System.out.println(detail.getLogisticsPurchase());
                // && detail.getLogisticsPurchase().get(sort).getType() == 1
                if (sort < detail.getLogisticsPurchase().size()
                        && kind == detail.getLogisticsPurchase().get(sort).getKind()) {
                    ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(detail.getResourceId());
                    if (detail.getExistSaveAction() != 2) {
                        resourceList.add(num, resource);
                        purchaseList.add(num, detail.getLogisticsPurchase().get(sort));
                        num++;
                    }
                }
            }
        }

        int count = purchaseList.size();
        return count;
    }

    // 订单所有提货单
    public Map<String, LogisticsPurchase> getOrderThd(HttpServletRequest request) {
        Map<String, LogisticsPurchase> logisticsPurchaseList = new HashMap<>();
        // List<LogisticsPurchase> logisticsPurchaseList=new ArrayList<>();
        Integer mid = SecurityUserUtil.getPmid();
        String orderId = request.getParameter("orderId");
        ScOrderListBase scOrderListBase = scOrderListBaseMapper.getLogisticsByOrderId(mid, orderId);

        int num = 0;
        for (ScOrderListDetail detail : scOrderListBase.getScOrderListDetail()) {
            if (detail.getLogisticsPurchase().size() > num) {
                num = detail.getLogisticsPurchase().size();
            }
        }

        // List<LogisticsPurchase> logisticsPurchaseList=new ArrayList<>();

        for (ScOrderListDetail detail : scOrderListBase.getScOrderListDetail()) {
            for (int i = 0; i < detail.getLogisticsPurchase().size(); i++) {

                if (logisticsPurchaseList.containsKey(Integer.toString(i))
                        && logisticsPurchaseList.get(Integer.toString(i)) != null) {
                    if (logisticsPurchaseList.get(Integer.toString(i)).getKind() != detail.getLogisticsPurchase().get(i)
                            .getKind() && detail.getLogisticsPurchase().get(i).getKind() == 1) {
                        logisticsPurchaseList.put(Integer.toString(num), detail.getLogisticsPurchase().get(i));
                        num++;
                    } else if (logisticsPurchaseList.get(Integer.toString(i)).getKind() == detail.getLogisticsPurchase()
                            .get(i).getKind() && detail.getLogisticsPurchase().get(i).getKind() == 0) {
                        if (detail.getLogisticsPurchase().get(i).getOrderDetailid() < logisticsPurchaseList
                                .get(Integer.toString(i)).getOrderDetailid()) {
                            logisticsPurchaseList.put(Integer.toString(i), detail.getLogisticsPurchase().get(i));
                        }
                    }
                } else {
                    if (detail.getLogisticsPurchase().get(i).getKind() == 0) {
                        logisticsPurchaseList.put(Integer.toString(i), detail.getLogisticsPurchase().get(i));
                    } else {
                        logisticsPurchaseList.put(Integer.toString(num), detail.getLogisticsPurchase().get(i));
                        num++;
                    }
                }
            }

        }

        System.out.println("logisticsPurchaseList=" + logisticsPurchaseList);

        for (String key : logisticsPurchaseList.keySet()) {
            Integer lid = logisticsPurchaseList.get(key).getId();
            if (getLogisticsCount(lid) == 0) {
                // System.out.println("lid=" + lid);
                // System.out.println("Count=" + getLogisticsCount(lid));
                logisticsPurchaseList.remove(key);
            }
        }

        return logisticsPurchaseList;
    }

    // 提货单pdf打印
    public Map<String, Object> getLogisticsById(Integer id) {
        Integer mid = SecurityUserUtil.getPmid();
        LogisticsPurchase logisticsPurchase = logisticsPurchaseMapper.selectByPrimaryKey(id);
        ScOrderListBase scOrderListBase = new ScOrderListBase();
        SysCompany sysCompany1 = new SysCompany();
        SysCompany sysCompany2 = new SysCompany();

        List<ScShelfResource> resourceList = new ArrayList<>();
        List<LogisticsPurchase> purchaseList = new ArrayList<>();
        Double sum = (double) 0;

        if (logisticsPurchase != null) {
            scOrderListBase = scOrderListBaseMapper.getLogisticsByOrderId(mid, logisticsPurchase.getOrderId());
            sysCompany1 = sysCompanyMapper.selectByPrimaryKey(scOrderListBase.getPmid());
            sysCompany2 = sysCompanyMapper.selectByPrimaryKey(scOrderListBase.getPurchaseMid());
            int sort = 0;
            int kind = 0;
            for (ScOrderListDetail detail : scOrderListBase.getScOrderListDetail()) {
                for (int i = 0; i < detail.getLogisticsPurchase().size(); i++) {
                    if (detail.getLogisticsPurchase().get(i).getId().equals(id)) {
                        sort = i;
                        kind = detail.getLogisticsPurchase().get(i).getKind();
                        break;
                    }
                }
            }

            int num = 0;
            for (ScOrderListDetail detail : scOrderListBase.getScOrderListDetail()) {
                System.out.println(detail.getLogisticsPurchase());
                if (sort < detail.getLogisticsPurchase().size()
                        && kind == detail.getLogisticsPurchase().get(sort).getKind()
                        && detail.getLogisticsPurchase().get(sort).getType() == 1) {
                    ScShelfResource resource = scShelfResourceMapper.selectByPrimaryKey(detail.getResourceId());
                    if (detail.getExistSaveAction() != 2) {
                        resourceList.add(num, resource);
                        purchaseList.add(num, detail.getLogisticsPurchase().get(sort));
                        sum = add(sum, detail.getLogisticsPurchase().get(sort).getNum());
                        num++;
                    }
                }
            }
        }

        Map<String, Object> model = new HashMap<>();
        String chineseNum = ParseNumberWeight(sum.toString(), true);
        Integer userid = scOrderListBase.getCreateUserId();
        String username = "";
        if (sysAdminuserMapper.selectByPrimaryKey(userid) != null) {
            username = sysAdminuserMapper.selectByPrimaryKey(userid).getArealname();
            username = username != null ? username : "";
        }

        model.put("username", username);
        model.put("chineseNum", chineseNum);
        model.put("sheet", scOrderListBase);
        model.put("resourceList", resourceList);
        model.put("purchaseList", purchaseList);
        model.put("sold_company", sysCompany1.getComname());
        model.put("purchase_company", sysCompany2.getComname());
        return model;
    }

    // 合同打印
    public Map<String, Object> getOrderHtById(Integer id) {
        Map<String, Object> model = new HashMap<>();
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        Integer comid = loginMessage.getSysCompany().getId();
        Integer pmid = SecurityUserUtil.getPmid();
        Integer pt = comid.equals(pmid) ? 1 : 0;
        ScOrderListBase orderlistbase = new ScOrderListBase();
        SysCompany pt_company = new SysCompany();
        SysCompany pur_company = new SysCompany();
        ScOrderListBase orderlistbase2 = scOrderListBaseMapper.getOrderById(id);
        Byte action = 1;
        for (ScOrderListDetail detail : orderlistbase2.getScOrderListDetail()) {
            if (detail.getExistSaveAction() == 2) {
                action = 2;
                break;
            }
        }

        System.out.println("action=" + action);

        if (action == 1 && (pt == 1
                || (pmid.equals(orderlistbase2.getPmid()) && comid.equals(orderlistbase2.getPurchaseMid())))) {
            orderlistbase = orderlistbase2;
            pt_company = sysCompanyMapper.selectByPrimaryKey(orderlistbase2.getPmid());
            pur_company = sysCompanyMapper.selectByPrimaryKey(orderlistbase2.getPurchaseMid());
        }
        model.put("orderlistbase", orderlistbase);
        model.put("pt_company", pt_company);
        model.put("pur_company", pur_company);
        return model;
    }

    public Boolean selectCompaneyBalanceAndCredit(Integer pmid, Integer mid, Double money) {
        Boolean res = false;
        Smownmember smownmember = smownmemberMapper.selectByPmidAndMid(pmid, mid);
        ScCompanyBalance companeybalance = scCompanyBalanceMapper.getBalanceByComid(pmid, mid);
        Double balanceAndCredit = 0.0;
        if (smownmember != null && companeybalance != null) {
            if (smownmember.getMember_type().equals(2)) {
                balanceAndCredit = companeybalance.getBalance() + smownmember.getCredit_amount();
            } else {
                balanceAndCredit = companeybalance.getBalance();
            }
        }
        if (balanceAndCredit >= money) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    // 入账
    public void orderMoneyMach(Integer pmid, Integer company_id, Double money) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        String user = session.getSysAdminuser().getArealname();

        // 检查余额+额度
        Boolean flag = false;
        Smownmember smownmember = smownmemberMapper.selectByPmidAndMid(pmid, company_id);
        ScCompanyBalance companeybalance = scCompanyBalanceMapper.getBalanceByComid(pmid, company_id);
        if (companeybalance == null) {
            SysCompany companyinfo = sysCompanyMapper.selectByPrimaryKey(company_id);
            companeybalance = new ScCompanyBalance();
            companeybalance.setPmid(pmid);
            companeybalance.setCompanyId(company_id);
            companeybalance.setCompanyName(companyinfo.getComname());
            companeybalance.setBalance((double) 0);
            companeybalance.setUserId(userid);
            companeybalance.setInputUser(user);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            String dateStr = df.format(new Date());
            companeybalance.setInputDate(df.parse(dateStr));
            scCompanyBalanceMapper.insert(companeybalance);
        }

        Double balanceAndCredit = companeybalance.getBalance();

        if (smownmember != null && smownmember.getMember_type().equals(2)) {
            balanceAndCredit = add(balanceAndCredit, smownmember.getCredit_amount());
        }

        Integer oid = scOrderListBaseMapper.getOrderMoneyMachId(pmid, company_id, money);// 订单匹配

        if (oid != null && oid > 0) {
            if (balanceAndCredit >= money) {
                flag = true;
            } else {
                flag = false;
            }
            // 额度够
            if (flag) {
                ScOrderListBase scOrderListBase = scOrderListBaseMapper.getOrderById(oid);// 订单匹配

                // 余额处理
                money = sub(companeybalance.getBalance(), money);
                scCompanyBalanceMapper.updateBalanceByComid(pmid, company_id, money);

                for (ScOrderListDetail scOrderList : scOrderListBase.getScOrderListDetail()) {
                    Integer rs_id = scOrderList.getResourceId();

                    // 检查是否超量
                    Byte save_action = checkOrderSell(rs_id);
                    scOrderListDetailMapper.updateSaveActionByid(scOrderList.getId(), save_action);

                    // 扣除上架资源量
                    if (scOrderList.getNum() != null && scOrderList.getNum() >= 0) {
                        ScShelfResource scShelfResource = scShelfResourceMapper.selectByPrimaryKey(rs_id);
                        Double sold_num = scShelfResource.getSoldNum();
                        Double salses_num = scShelfResource.getNum();
                        Double order_num = scOrderList.getNum();
                        Double num_dif = salses_num - sold_num - order_num;

                        if (num_dif >= 0) {
                            scShelfResourceMapper.updateSoldNumById(rs_id, order_num);
                        } else {
                            scShelfResourceMapper.updateSoldNumById(rs_id, salses_num - sold_num);
                            // scOrderListDetailMapper.updateSaveActionByrsid(rs_id, save_action);
                        }
                        stopOpenPlateService.overSaleStop(pmid);// 超量销售订单
                    }

                    if (scOrderList.getExtractNum() != null && !scOrderList.getExtractNum().equals("")) {
                        String[] numArray = StringUtils.split(scOrderList.getExtractNum(), ",");
                        for (int num = 0; num < numArray.length; num++) {
                            if (numArray[num] != null) {
                                LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                                logisticsPurchase.setOrderDetailid(scOrderList.getId());
                                logisticsPurchase.setOrderId(scOrderListBase.getOrderId());
                                logisticsPurchase.setResourceId(rs_id);
                                logisticsPurchase.setExtractId(get_order_tdid(rs_id));
                                logisticsPurchase.setType((byte) 1);
                                logisticsPurchase.setKind((byte) 0);
                                logisticsPurchase.setActualNum((double) 0);
                                logisticsPurchase.setNum(Double.parseDouble(numArray[num]));
                                logisticsPurchase.setPrice(scOrderList.getScShelfResource().getPrice());
                                logisticsPurchase.setDepository(scOrderList.getScShelfResource().getWarehouse());
                                logisticsPurchase.setCreateUserId(userid);
                                logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
                            }
                        }
                    } else {

                        if (scOrderList.getNum() != null && scOrderList.getNum() >= 0) {
                            LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                            logisticsPurchase.setOrderDetailid(scOrderList.getId());
                            logisticsPurchase.setOrderId(scOrderListBase.getOrderId());
                            logisticsPurchase.setResourceId(rs_id);
                            logisticsPurchase.setExtractId(get_order_tdid(rs_id));
                            logisticsPurchase.setType((byte) 1);
                            logisticsPurchase.setKind((byte) 0);
                            logisticsPurchase.setActualNum((double) 0);
                            logisticsPurchase.setNum(scOrderList.getNum());
                            logisticsPurchase.setPrice(scOrderList.getScShelfResource().getPrice());
                            logisticsPurchase.setDepository(scOrderList.getScShelfResource().getWarehouse());
                            logisticsPurchase.setCreateUserId(userid);
                            logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
                        }
                    }
                }

                // 修改订单状态已付款
                ScOrderListBase base = new ScOrderListBase();
                base.setId(scOrderListBase.getId());
                base.setStatus((byte) 3);
                base.setConfirmUserId(userid);
                scOrderListBaseMapper.updateByPrimaryKey(base);

            }
        } else {

            // 无匹配订单 待付款订单按金额大到小付款
            List<ScOrderListBase> scOrderListBaseList = scOrderListBaseMapper.getOrderListByStatus(pmid, company_id,
                    (byte) 2);
            Double balance = companeybalance.getBalance();

            if (scOrderListBaseList != null && scOrderListBaseList.size() > 0) {
                for (ScOrderListBase scOrderList : scOrderListBaseList) {
                    if (balanceAndCredit >= scOrderList.getTotalPrice()) {
                        // 余额处理
                        balance = sub(balance, scOrderList.getTotalPrice());
                        // 额度
                        balanceAndCredit = sub(balanceAndCredit, scOrderList.getTotalPrice());

                        for (ScOrderListDetail scOrderList2 : scOrderList.getScOrderListDetail()) {
                            Integer rs_id = scOrderList2.getResourceId();
                            // 检查是否超量
                            Byte save_action = checkOrderSell(rs_id);
                            scOrderListDetailMapper.updateSaveActionByid(scOrderList2.getId(), save_action);

                            // 扣除上架资源量
                            if (scOrderList2.getNum() != null && scOrderList.getNum() >= 0) {
                                ScShelfResource scShelfResource = scShelfResourceMapper.selectByPrimaryKey(rs_id);
                                Double sold_num = scShelfResource.getSoldNum();
                                Double salses_num = scShelfResource.getNum();
                                Double order_num = scOrderList2.getNum();
                                Double num_dif = salses_num - sold_num - order_num;

                                if (num_dif >= 0) {
                                    scShelfResourceMapper.updateSoldNumById(rs_id, order_num);
                                } else {
                                    scShelfResourceMapper.updateSoldNumById(rs_id, salses_num - sold_num);
                                    // scOrderListDetailMapper.updateSaveActionByrsid(rs_id, save_action);
                                }
                                stopOpenPlateService.overSaleStop(pmid);
                            }

                            if (scOrderList2.getExtractNum() != null && !scOrderList2.getExtractNum().equals("")) {
                                String[] numArray = StringUtils.split(scOrderList2.getExtractNum(), ",");
                                for (int num = 0; num < numArray.length; num++) {
                                    if (numArray[num] != null) {
                                        LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                                        logisticsPurchase.setOrderDetailid(scOrderList2.getId());
                                        logisticsPurchase.setOrderId(scOrderList.getOrderId());
                                        logisticsPurchase.setResourceId(rs_id);
                                        logisticsPurchase.setExtractId(get_order_tdid(rs_id));
                                        logisticsPurchase.setType((byte) 1);
                                        logisticsPurchase.setKind((byte) 0);
                                        logisticsPurchase.setActualNum((double) 0);
                                        logisticsPurchase.setNum(Double.parseDouble(numArray[num]));
                                        logisticsPurchase.setPrice(scOrderList2.getScShelfResource().getPrice());
                                        logisticsPurchase
                                                .setDepository(scOrderList2.getScShelfResource().getWarehouse());
                                        logisticsPurchase.setCreateUserId(userid);
                                        logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
                                    }
                                }
                            } else {
                                if (scOrderList2.getNum() != null && scOrderList2.getNum() >= 0) {
                                    LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                                    logisticsPurchase.setOrderDetailid(scOrderList2.getId());
                                    logisticsPurchase.setOrderId(scOrderList.getOrderId());
                                    logisticsPurchase.setResourceId(rs_id);
                                    logisticsPurchase.setExtractId(get_order_tdid(rs_id));
                                    logisticsPurchase.setType((byte) 1);
                                    logisticsPurchase.setKind((byte) 0);
                                    logisticsPurchase.setActualNum((double) 0);
                                    logisticsPurchase.setNum(scOrderList2.getNum());
                                    logisticsPurchase.setPrice(scOrderList2.getScShelfResource().getPrice());
                                    logisticsPurchase.setDepository(scOrderList2.getScShelfResource().getWarehouse());
                                    logisticsPurchase.setCreateUserId(userid);
                                    logisticsPurchaseMapper.insertLogistics(logisticsPurchase);
                                }
                            }
                        }

                        // 修改订单状态已付款
                        ScOrderListBase base = new ScOrderListBase();
                        base.setId(scOrderList.getId());
                        base.setStatus((byte) 3);
                        base.setConfirmUserId(userid);
                        scOrderListBaseMapper.updateByPrimaryKey(base);

                    }
                }
                scCompanyBalanceMapper.updateBalanceByComid(pmid, company_id, balance);// 余额
            }

        }
    }

    public Byte checkOrderSell(Integer resource_id) {
        Integer pmid = SecurityUserUtil.getPmid();
        // 待付款量+待确认资源量
        BigDecimal sold_sum_dqr = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 1, pmid);
        sold_sum_dqr = sold_sum_dqr != null ? sold_sum_dqr : new BigDecimal(Double.toString(0));

        BigDecimal sold_sum_dfk = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 2, pmid);
        sold_sum_dfk = sold_sum_dfk != null ? sold_sum_dfk : new BigDecimal(Double.toString(0));

        BigDecimal sold_sum_yfk = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 3, pmid);
        Double sold_sum_yfk2 = sold_sum_yfk != null ? sold_sum_yfk.doubleValue() : 0;

        Double sold_sum_y = sold_sum_dqr.add(sold_sum_dfk).doubleValue();

        // Double sold_sum_yfk =
        // scOrderListDetailMapper.selectSumbyresourceId(resource_id, 3).doubleValue();
        ScShelfResource scShelfResource = scShelfResourceMapper.selectByPrimaryKey(resource_id);
        Byte type = 1;
        // 资源可销售量
        Double sold_sum_k = sub(scShelfResource.getNum(), scShelfResource.getSoldNum());

        System.out.println("sold_sum_k=" + sold_sum_k);
        System.out.println("sold_sum_y=" + sold_sum_y);

        if (sold_sum_y == 0) {
            if (sold_sum_yfk2 <= scShelfResource.getNum()) {
                scOrderListDetailMapper.updateSaveActionByrsid(resource_id, (byte) 1, (byte) 2, pmid);
            }
        } else if (sold_sum_y <= sold_sum_k) {
            // 非超量 修改为正常销售
            // logisticsPurchaseMapper.updatePurchaseByResourceId(resource_id);
            scOrderListDetailMapper.updateSaveActionByrsid(resource_id, (byte) 1, (byte) 2, pmid);
        } else {
            type = 2;
        }
        return type;
    }

    public void checkOrderSale(String order_id) {
        Integer pmid = SecurityUserUtil.getPmid();
        // 待付款量+待确认资源量
        if (order_id != null && !order_id.equals("")) {
            ScOrderListBase scOrderList = scOrderListBaseMapper.getOrderByorderId2(order_id);

            System.out.print(scOrderList);

            for (ScOrderListDetail detail : scOrderList.getScOrderListDetail()) {
                Integer resource_id = detail.getResourceId();
                BigDecimal sold_sum_dqr = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 1, pmid);
                sold_sum_dqr = sold_sum_dqr != null ? sold_sum_dqr : new BigDecimal(Double.toString(0));

                BigDecimal sold_sum_dfk = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 2, pmid);
                sold_sum_dfk = sold_sum_dfk != null ? sold_sum_dfk : new BigDecimal(Double.toString(0));

                BigDecimal sold_sum_yfk = scOrderListDetailMapper.selectSumbyresourceId(resource_id, 3, pmid);
                Double sold_sum_yfk2 = sold_sum_yfk != null ? sold_sum_yfk.doubleValue() : 0;

                Double sold_sum_y = sold_sum_dqr.add(sold_sum_dfk).doubleValue();

                // Double sold_sum_yfk =
                // scOrderListDetailMapper.selectSumbyresourceId(resource_id, 3).doubleValue();
                ScShelfResource scShelfResource = scShelfResourceMapper.selectByPrimaryKey(resource_id);

                // 资源可销售量
                Double sold_sum_k = sub(scShelfResource.getNum(), scShelfResource.getSoldNum());

                if (sold_sum_y == 0) {
                    if (sold_sum_yfk2 <= scShelfResource.getNum()) {
                        scOrderListDetailMapper.updateSaveActionByrsid(resource_id, (byte) 1, (byte) 2, pmid);
                    }
                } else if (sold_sum_y <= sold_sum_k) {
                    // 非超量 修改为正常销售
                    // logisticsPurchaseMapper.updatePurchaseByResourceId(resource_id);
                    scOrderListDetailMapper.updateSaveActionByrsid(resource_id, (byte) 1, (byte) 2, pmid);
                }
            }
        }

    }

    public void checkOrderTop(Integer pmid) {
        // 停盘修改为提单
        // logisticsPurchaseMapper.updatePurchaseByResourceId(resource_id);
        // scOrderListDetailMapper.updateSaveActionByPmid(pmid);
    }

    // 使用String的split 方法
    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    // 提单号
    private String get_order_tdid(Integer rid) {
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String orderId = ft.format(now) + rid;
        return orderId;
    }

    public static String ParseNumberWeight(String numbers, Boolean mode) {
        if (mode) {
            numbers = replace(numbers);
        }

        String[] aa = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String[] dw = { "", "拾", "佰", "仟", "", "万", "亿", "兆" };

        String dec = "点";
        String retval = "";
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        if (mode) {
            map.put(0, numbers);
            if (numbers.indexOf(".") > -1) {
                String[] arr = StringUtils.split(numbers, ".");
                map.put(1, arr[0]);
                map.put(2, arr[1]);
            } else {
                map.put(1, numbers);
                map.put(2, "");
            }
        } else {
            map.put(0, numbers);
            map.put(1, numbers);
            map.put(2, "");
        }

        if (map.get(2) != "") {
            retval = dec + ParseNumberWeight(map.get(2).toString(), false);
        }
        if (map.get(1) != "") {
            String str = new StringBuilder(map.get(1).toString()).reverse().toString();
            String[] str2 = str.split("");
            String[] out = new String[str2.length];

            for (int i = 0; i < str2.length; i++) {
                String rr = aa[Integer.parseInt(str2[i])];
                System.out.println(rr + "^^^^^");
                if (mode) {
                    rr += str2[i] != "0" ? dw[i % 4] : "";
                    Integer qq = Integer.parseInt(str2[i]);
                    if (i > 0) {
                        qq += Integer.parseInt(str2[i - 1]);
                    }
                    if (qq == 0 && str2.length > 1) {
                        rr = "";
                    } else {
                        rr += dw[4 + (i / 4)];
                    }
                }
                out[i] = rr;
            }
            out = arrayReverse2(out);
            retval = StringUtils.join(out, "") + retval;
        }
        if (mode) {
            retval += "吨";
        }
        return retval;
    }

    public static String[] arrayReverse2(String[] originArray) {
        int length = originArray.length;
        String[] reverseArray = new String[length];
        for (int i = 0; i < length; i++) {
            reverseArray[i] = originArray[length - i - 1];
        }

        return reverseArray;
    }

    public static String replace(String s) {
        if (null != s && s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @Description 该资源id是否存在未付款 or 未确认的订单
     * @Author zhaofy
     * @Date  2021/12/1
     * @Param [resourceId]
     * @return boolean
     **/
    public boolean haveOrder(int resourceId){
        int count = scOrderListDetailMapper.getCountOrderByResourceId(resourceId, SecurityUserUtil.getPmid());
        return count > 0;
    }

    /**
     * @Description 根据资源id取消订单
     * @Author zhaofy
     * @Date  2021/12/2
     * @Param [resourceId]
     * @return boolean
     **/
    public boolean dealHaveOrder(int resourceId) {
        List<ScOrderListDetail> scOrderListDetailList = scOrderListDetailMapper.getListOrderByResourceId(resourceId,
                SecurityUserUtil.getPmid());
        for (ScOrderListDetail scOrderListDetail : scOrderListDetailList) {
            //通过订单id查询该订单是否有多条资源 多条资源只删除一条detail记录，只有一条资源时取消订单即可
            int count = scOrderListDetailMapper.getDetailCountByorderId(scOrderListDetail.getOrderBaseid());
            if (count > 1) {
                ScOrderListBase scOrderListBase = new ScOrderListBase();
                scOrderListBase.setOrderId(scOrderListDetail.getOrderBaseid());
                scOrderListBase.setPiece(ArithmeticUtils.sub(scOrderListDetail.getScOrderListBase().getPiece(),
                 scOrderListDetail.getPiece()));
                scOrderListBase.setNum(ArithmeticUtils.sub(scOrderListDetail.getScOrderListBase().getNum(),
                 scOrderListDetail.getNum()));
                scOrderListBase.setTotalPrice(ArithmeticUtils.sub(scOrderListDetail.getScOrderListBase().getTotalPrice(), scOrderListDetail.getPrice()*scOrderListDetail.getNum()));
                //将订单base表的件数 数量 总价减掉
                scOrderListBaseMapper.subOrderNum(scOrderListBase);
                //将订单detail表的状态改为删除
                scOrderListDetailMapper.updateOrderDetailDelete(scOrderListDetail.getId());
            } else {
                //取消订单
                scOrderListBaseMapper.cancelOrder(scOrderListDetail.getOrderBaseid());
                //将订单detail表的状态改为删除
                scOrderListDetailMapper.updateOrderDetailDelete(scOrderListDetail.getId());
            }
        }
        return true;
    }

}
