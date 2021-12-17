/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-09-07 10:07:43
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\ResourceSalesService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.HttpUtils;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.*;
import com.isechome.ecommerce.security.SecuritySysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.constant.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ResourceSalesService {
    @Autowired
    ResourceSalesMapper resourceSalesMapper;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    PieceWeightMapper pieceWeightMapper;
    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper;
    @Autowired
    ScOrderListDetailMapper scOrderListDetailMapper;
    @Autowired
    ScShoppingCartMapper scShoppingCartMapper;
    @Autowired
    StopOpenPlateService stopOpenPlateService;
    @Autowired
    ScPieceWeightManageMapper scPieceWeightManageMapper;
    @Autowired
    OrderStatusjobService orderStatusjobService;
    @Autowired
    ScfahuolibsMapper scfahuolibsMapper;
    @Autowired
    SmownmemberMapper sMapper;
    @Autowired
    ScShelfResourceMapper scShelfResourceMapper;
    @Autowired
    FinancialService financialService;
    @Autowired
    ScOrderListService scOrderListService;
    @Value("${smslogin.url}")
    public String sms_content_url;
    String sms_conten_param = "action=ajaxsendSMSContent&mobile=";
    

    // 获取资源列表
    public List<ResourceSales> selectAll(Map<String,Object> map) {
        return resourceSalesMapper.selectAll(map);
    }

    /**
     * @Description:前台资源列表
     * @Author: shizg
     * @Date: 2021/5/31 11:52
     * @param request:
     * @param model:
     * @return: void
     **/
    public void showResourceList(HttpServletRequest request, Model model) {
        //SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Byte vid = 1 ;
//        String varietyname = request.getParameter("varietyname");
        String searchVid = request.getParameter("searchVid");
        String searchwhere = request.getParameter("searchwhere");
        String material = request.getParameter("material");
        String spec = request.getParameter("spec");
        String originCode = request.getParameter("originCode");
        String cangku = request.getParameter("cangku");
        String vid_request = request.getParameter("vid");
        if ( vid_request != "" && vid_request != null ) {
            vid = Byte.parseByte(vid_request);
        }
        if (searchVid!="" && searchVid!=null ) {
            if ( searchVid.equals("螺纹") ){
                vid = CommonConstant.LUOWEN_VID;
            }else if ( searchVid.equals("盘螺") ) {
                vid = CommonConstant.PANLUO_VID;
            }else if ( searchVid.equals("高线") ) {
                vid = CommonConstant.GAOXIAN_VID;
            }
        }

        String pageNumStr = request.getParameter("page");
        Integer page = 0;
        if (pageNumStr == null || pageNumStr == "") {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumStr);
        }
        if (page < 1) {
            page = 1;
        }
        Integer purchaseMid = 0;
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        if ( loginMessage!=null && loginMessage.getSysCompanyInfo().getId() > 0 ) {
            purchaseMid = loginMessage.getSysCompanyInfo().getId();
        }
        Integer pageSize = CommonConstant.PAGE_SIZE;

        if (searchwhere!="" && searchwhere!=null) {
            //以下两行代码需紧跟，中间不能有其他查询的代码
            PageHelper.startPage(page, pageSize);
            List<ResourceSales> resourceSales = resourceSalesMapper.getSalesResourceListBySearch(vid,searchwhere);
            PageInfo<ResourceSales> pageInfo = new PageInfo<ResourceSales>(resourceSales);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("resourceSalesList",resourceSales);
        }else {
            //以下两行代码需紧跟，中间不能有其他查询的代码
            PageHelper.startPage(page, pageSize);
            List<ResourceSales> resourceSales = resourceSalesMapper.getSalesResourceList(vid, "", material, spec, originCode, cangku);
            PageInfo<ResourceSales> pageInfo = new PageInfo<ResourceSales>(resourceSales);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("resourceSalesList",resourceSales);
        }

        model.addAttribute("title","资源列表");
        model.addAttribute("vid",vid);
        model.addAttribute("purchaseMid",purchaseMid);
        model.addAttribute("material",material);
        model.addAttribute("spec",spec);
        model.addAttribute("originCode",originCode);
        model.addAttribute("cangku",cangku);
        model.addAttribute("searchwhere",searchwhere);
        model.addAttribute("searchVid",searchVid);
    }

    // 判断excel数据是否正确
    public String checkExcel(ResourceSales recs) {
        String str = "true";
        if (recs.getPiece() > 10000 || recs.getPiece() == 0 || recs.getPiece() < 0) {
            str = "false";
        }
        if (recs.getNum() > 10000 || recs.getNum() == 0 || recs.getNum() < 0) {
            str = "false";
        }
        if (recs.getPrice() > 10000 || recs.getPrice() == 0 || recs.getPrice() < 0) {
            str = "false";
        }
        return str;
    }

    //获取导入excel资源列表
    public Integer insertExcelList(ArrayList<String[]> arrayList){
        List<ResourceSales> resourceSales = new ArrayList<>();
        Map<String, Integer> colmap = new HashMap();
        if(arrayList.size()>0){
            String[] str = arrayList.get(0);
            for(int num=0;num<str.length;num++)
            {
                // String h_key = str[num].trim();
                // if(h_key.equals("品种名称")){
                //     h_key=h_key.replace("品种名称","品名");
                // }
                colmap.put(str[num].trim(), num);
            }  
        }

        Byte status = 1;
        Date now = new Date();
        Date salesEndTime = new Date();
        salesEndTime.setTime(salesEndTime.getTime() + 12 * 60 * 60 * 1000);
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        Integer createUserId = session.getId();
        Integer salesMid = session.getSysAdminUserInfo().getComid();
        int errorNum = 0;
        for(int n = 1;n < arrayList.size();n++){
            String[] str = arrayList.get(n);
            ResourceSales recs = new ResourceSales();
            recs.setVarietyname(str[colmap.get("品名")]);
            recs.setMaterial(str[colmap.get("材质")]);
            recs.setSpec(str[colmap.get("规格")]);
            recs.setOriginCode(str[colmap.get("产地")]);
            recs.setCangku(str[colmap.get("仓库")]);
            recs.setPiece(Math.floor(Double.valueOf(str[colmap.get("件数")])));
            recs.setNum((double) Math.floor(Double.valueOf(str[colmap.get("重量")]) * 1000)/1000);
            recs.setPrice(Double.valueOf(str[colmap.get("价格")]));
            Byte vid = getVid(str[colmap.get("品名")]);
            recs.setVid(vid);
            recs.setSalesStartTime(now);
            recs.setSalesEndTime(salesEndTime);
            recs.setStatus(status);
            recs.setSalesMid(salesMid);
            recs.setCreatTime(now);
            recs.setCreateUserId(createUserId);
            if(checkExcel(recs) == "false") {
                errorNum ++;
                continue;
            }
            resourceSales.add(recs);
        }
        resourceSalesMapper.insertExcel(resourceSales);
        return errorNum;
    }

    // 获取品种id
    public Byte getVid(String varietyname){
        Byte vid = 0;
        if (varietyname.contains("螺纹")) {
            vid = CommonConstant.LUOWEN_VID;
        } else if (varietyname.contains("盘螺")) {
            vid = CommonConstant.PANLUO_VID;
        } else if (varietyname.contains("高线")) {
            vid = CommonConstant.GAOXIAN_VID;
        }
        return vid;
    }

    // 资源更新
    public void  resourceupdate(HttpServletRequest request, HttpServletResponse res) throws IOException {
        Common common=new Common();
        String allsel = request.getParameter("allsel");
        for (String id: allsel.split(",")){
            Double piece = Double.parseDouble(request.getParameter("piece_"+id));
            Double num = Double.parseDouble(request.getParameter("num_"+id));
            Double price = Double.parseDouble(request.getParameter("price_"+id));
            Date sales_start_time = common.strToDate(request.getParameter("salesStartTime_"+id), "yyyy-MM-dd HH:mm:ss");
            Date sales_end_time = common.strToDate(request.getParameter("salesEndTime_"+id), "yyyy-MM-dd HH:mm:ss");
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id",Integer.parseInt(id));
            map.put("piece",piece);
            map.put("num",num);
            map.put("price",price);
            map.put("sales_start_time",sales_start_time);
            map.put("sales_end_time",sales_end_time);
            resourceSalesMapper.resourceUpdate(map);
        }
    }

    // 获取规格件重
    public Float getWeight(HttpServletRequest request){
        String spec = request.getParameter("spec");
        PieceWeight pieceWeight = new PieceWeight();
        pieceWeight = pieceWeightMapper.getBySpec(spec);
        Float weight;
        if ( pieceWeight == null) {
            weight = 0f;
        } else {
            weight = pieceWeight.getWeight();
        }
        return weight;
    }

    // 改变资源状态
    public void updateStatus(HttpServletRequest request){
        String allsel = request.getParameter("allsel");
        String act = request.getParameter("act");
        Byte status = 0;
        if (act.equals("sh")) {
            status = 2;
        } else if (act.equals("sj")) {
            status = 3;
        } else if (act.equals("xj")) {
            status = 4;
        }
        for (String id: allsel.split(",")){
            if (status != 0) {
                resourceSalesMapper.updateStatusById(Integer.parseInt(id), status);
            }
        }
    }

    // 结束时间小于当前时间的下架
    public void updateStatusByEndTime(){
        resourceSalesMapper.updateStatusByEndTime();
    }

    /**
     * @Description: 执行加入采购车
     * @Author: shizg
     * @Date: 2021/6/7 16:02
     * @param request :
     * @return: java.lang.Integer
     **/
    public AjaxResult add_purchase_car(String residsStr ){
        AjaxResult aResult = new AjaxResult();
        List<ScShoppingCartResoueces> ret_scShoppingCarts = new ArrayList<>();
        //String residsStr = request.getParameter("resids");
        
        Integer resid = 0;
        if(residsStr != null && !residsStr.equals("")){
            resid = Integer.parseInt(residsStr);;
        }
        ScShelfResource resourceSales = null;
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        Integer userid = session.getId();
        Integer pmid = SecurityUserUtil.getPmid();
        Integer status = 0;
        Integer purchase_mid = session.getSysAdminUserInfo().getComid();

        if(resid !=null && resid != 0){
            Boolean existFlag = false;
            //权限判断
            List aList = new ArrayList<>(session.getAuthorities());
            for (Object aList_obj : aList) {
                if(aList_obj.toString().equals("ROLE_GYLQX_YSH")){
                    existFlag = false;
                    break;
                }else{
                    existFlag = true;
                }
            }
            if(existFlag){
                aResult.put("success", 0);
                aResult.put("msg", "当前账号未审核，请联系客服！");
            }
            //开盘状态
            boolean iskp = stopOpenPlateService.getIsOpen();
            if(!iskp && !resid.equals(0)){
                aResult.put("success", 0);
                aResult.put("msg", "当前已停盘无法加入采购车！");
                existFlag = true;
                //return aResult;
            }else if(resid.equals(0)){
                aResult.put("success", 0);
                existFlag = true;
            }

            //同一条资源是否重复被加入采购车
            List<ScShoppingCartResoueces> scShoppingCarts = scShoppingCartMapper.getScShoppingCartAll(userid, pmid, status, getCurrDate());
            System.out.println("scShoppingCarts ==================== "+scShoppingCarts.toString());
            for (ScShoppingCartResoueces item : scShoppingCarts) {
                if(item.getResources_id().equals(resid)){
                    existFlag = true;
                    aResult.put("msg", "该资源已加入购物车！请勿重复购买！");
                    break;
                }
            }

            resourceSales = scShelfResourceMapper.selectByPrimaryKey(resid);
            if(resourceSales != null && resourceSales.getPmid().equals(purchase_mid)){
                aResult.put("success", 0);
                aResult.put("msg", "您无法购买自己的资源！");
                resid = 0;
                existFlag = true;
            }

            if(iskp && !existFlag){
                //执行插入采购车
                ScShoppingCart obj = new ScShoppingCart();
                obj.setUserid(userid);
                obj.setPmid(pmid);
                obj.setPurchase_mid(purchase_mid);
                obj.setResources_id(resid);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    obj.setCreate_date(df.parse(df.format(new Date())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    obj.setUpdate_time(df.parse(df.format(new Date())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                obj.setStatus(0);
                obj.setIs_split_lading(0);
                obj.setWeight(0);
                obj.setWeight2(0);
                obj.setPrice(resourceSales.getPrice());
                obj.setTicket(1);
                scShoppingCartMapper.insertScShoppingCart(obj);
                aResult.put("success", 1);
            }else{
                aResult.put("success", 0);
            }
        }
        
        ret_scShoppingCarts = scShoppingCartMapper.getScShoppingCartAll(userid, pmid, status, getCurrDate());
        for(int i = 0; i < ret_scShoppingCarts.size(); i++){
            ScPieceWeightManage sc_weight = scPieceWeightManageMapper.getByGcShortSpec(ret_scShoppingCarts.get(i).getFactory(),ret_scShoppingCarts.get(i).getSpecification(),pmid);
            if (sc_weight != null) {
                ret_scShoppingCarts.get(i).setD_weight(sc_weight.getWeight());
            }else{
                ret_scShoppingCarts.get(i).setD_weight(0.0);
            }
            ret_scShoppingCarts.get(i).setNum(ret_scShoppingCarts.get(i).getNum() - ret_scShoppingCarts.get(i).getSold_num());
        }
        aResult.put("gwclist", ret_scShoppingCarts);
        return aResult;
    }

    /**
     * @Description:根据id获取资源信息
     * @Author: shizg
     * @Date: 2021/6/7 15:51
     * @param id:
     * @return: com.isechome.ecommerce.entity.ResourceSales
     **/
    public ResourceSales getResourceSalesById( Integer id ){
        return resourceSalesMapper.getSalesResourceById(id);
    }

    /**
     * @Description: 从采购车删除指定资源
     * @Author: shizg
     * @Date: 2021/6/8 17:36
     * @param request:
     * @return: void
     **/
    public void del_purchase_car(HttpServletRequest request) {
        List<Integer> ids = new ArrayList<>();
        System.out.println(request.getParameter("gwc_id"));
        if(request.getParameter("gwc_id") != null && !request.getParameter("gwc_id").equals("")){
            String[] ids_arr = request.getParameter("gwc_id").split(",");
            for (String id : ids_arr) {
                ids.add(Integer.parseInt(id));
            }
            scShoppingCartMapper.deleteScShoppingCartById(ids);
        }
    }

    public void deleteScShoppingCart(int is_del_all){
        if(is_del_all == 1){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String curr_date = df.format(new Date()) + " 00:00:00";
            Date create_date2 = new Date();
            try {
                create_date2 = df2.parse(curr_date);
                scShoppingCartMapper.deleteScShoppingCartAll(create_date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description:更新采购车件数数量
     * @Author: shizg
     * @Date: 2021/6/9 17:06
     * @param request:
     * @return: void
     **/
    public void update_purchase_car_num(HttpServletRequest request) {
            String[] gwc_ids = request.getParameter("ids").split(",");
            String[] weights = request.getParameter("weights").split(",");
            String[] weights2 = request.getParameter("weights2").split(",");
            Integer gwcid = null;
            Double weight = null;
            Double weight2 = null;
            for (int i = 0; i< gwc_ids.length;i++) {
                gwcid = Integer.parseInt(gwc_ids[i]);
                weight = Double.parseDouble(weights[i]);
                weight2 = Double.parseDouble(weights2[i]);
                System.out.println("gwc_ids=="+gwc_ids.toString());
                scShoppingCartMapper.updateScShoppingCartById(gwcid,weight,weight2,getCurrDate2());
            }
    }

    //获取订单号
    public String getOrderNumber(){
        Random random = new Random();
        String dd_str =  "DD" + System.currentTimeMillis() + random.nextInt(10000);
        return dd_str;
    }

    
    public Date getCurrDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curr_date = df.format(new Date()) + " 00:00:00";
        Date create_date2 = new Date();
        try {
            create_date2 = df2.parse(curr_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return create_date2;
    }

    public Date getCurrDate2(){
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curr_date = df2.format(new Date());
        Date create_date2 = new Date();
        try {
            create_date2 = df2.parse(curr_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return create_date2;
    }

    public ModelAndView getShoppingCartList(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer userid = session.getId();
        Integer pmid = SecurityUserUtil.getPmid();
        Integer mid = session.getSysAdminUserInfo().getComid();
        Integer status = 0;
        Double z_weight = 0.0;
        Double kgl = 0.0;
        List<ScShoppingCartResoueces> ret_scShoppingCarts = scShoppingCartMapper.getScShoppingCartAll(userid, pmid, status, getCurrDate());
        for(int i = 0; i < ret_scShoppingCarts.size(); i++){
            ScPieceWeightManage sc_weight = scPieceWeightManageMapper.getByGcShortSpec(ret_scShoppingCarts.get(i).getFactory(),ret_scShoppingCarts.get(i).getSpecification(),pmid);
            
            kgl = Double.parseDouble((new DecimalFormat("#.000").format(ret_scShoppingCarts.get(i).getNum() - ret_scShoppingCarts.get(i).getSold_num())));
            ret_scShoppingCarts.get(i).setNum(kgl);
            if (sc_weight != null) {
                ret_scShoppingCarts.get(i).setD_weight(sc_weight.getWeight());
                z_weight = Math.floor((ret_scShoppingCarts.get(i).getNum())/sc_weight.getWeight());
                ret_scShoppingCarts.get(i).setZ_weight(z_weight);
            }else{
                ret_scShoppingCarts.get(i).setD_weight(0.0);
            }
        }
        modelAndView.addObject("scShoppingCarts", ret_scShoppingCarts);
        Smownmember smownmember = sMapper.selectByPmidAndMid(pmid,mid);
        Double preferential_amount = 0.0;
        if(smownmember != null){
            preferential_amount = smownmember.getPreferentialAmount();
        }
        modelAndView.addObject("preferential_amount", preferential_amount);
        modelAndView.addObject("dzmList",scfahuolibsMapper.selectAll2(pmid,mid));
        return modelAndView;
    }

    public AjaxResult create_order(HttpServletRequest request) {
        AjaxResult aResult = new AjaxResult();
        String[] gwc_id_arr = request.getParameter("gwc_ids").split(",");//采购车id
        String[] weights = request.getParameter("weights").split(",");//购买量
        String[] weights2 = request.getParameter("weights2").split(",");//购买重量
        String[] bargains = request.getParameter("bargains").split(",");//议价单价
        String[] dcfh_vaules = request.getParameter("dcfh_vaules").split("\\|");//多次发货
        String[] status_arr = request.getParameter("status").split(",");
        //Integer is_split_lading = Integer.parseInt(request.getParameter("is_split_lading"));//是否拆分提单
        Integer dz_code_id = 0;//到站码id
        Byte is_status = 2;//1：待确认订单 2：待付款订单 3：已付款订单
        if(!request.getParameter("dz_code").equals("")){
            dz_code_id = Integer.parseInt(request.getParameter("dz_code"));
        }
        Integer ticket = Integer.parseInt(request.getParameter("ticket"));//一票制 两票制
        String[] resources_ids = request.getParameter("resources_ids").split(",");//资源id
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();  
        Integer userid = session.getId();
        Integer purchase_mid = session.getSysAdminUserInfo().getComid();
        Integer pmid = SecurityUserUtil.getPmid();
        

        //到站码信息
        Scfahuolibs fhData = scfahuolibsMapper.selectByPrimaryKey(dz_code_id);
        Map<Integer,Map<String,List<Integer>>> order_data_map = new HashMap<Integer,Map<String,List<Integer>>>();
        //Map<String,List<Integer>> cangku_map = null;
        Map<Integer,ScShelfResource> res_map = new HashMap<>();
        for(int i = 0; i < resources_ids.length; i++){
            ScShelfResource resource_data = scShelfResourceMapper.selectByPrimaryKey(Integer.parseInt(resources_ids[i]));
            res_map.put(i, resource_data);

            Map<String,List<Integer>> newmap = order_data_map.get(res_map.get(i).getVid());
            List alist = new ArrayList();
            if (newmap == null){
                newmap =  new HashMap<String,List<Integer>>();
            }else if(order_data_map.get(res_map.get(i).getVid()).get(res_map.get(i).getWarehouse()) != null){
                alist = order_data_map.get(res_map.get(i).getVid()).get(res_map.get(i).getWarehouse());
            }
            alist.add(i);
            newmap.put(resource_data.getWarehouse(),alist);
            order_data_map.put(resource_data.getVid(),newmap);

            //判断到站码的仓库是否跟资源的仓库匹配
            if(ticket == 1 && !fhData.getCangkuname().equals(resource_data.getWarehouse())){
                aResult.put("success", 0);
                aResult.put("msg",resource_data.getWarehouse()+"的"+resource_data.getVarietyName()+" "+resource_data.getMaterial() +" "+resource_data.getSpecification()+"无法送至"+fhData.getDaozhanCangkuName());
                return aResult;
            }

        }


        for (Map.Entry<Integer,Map<String,List<Integer>>> order_entry : order_data_map.entrySet()) {
            for (Map.Entry<String,List<Integer>> cangku_entry : order_entry.getValue().entrySet()) {
                if(cangku_entry.getValue().size() > 0){
                    ScOrderListBase orderListBase = new ScOrderListBase();
                    List<ScOrderListDetail> scOrderListDetails = new ArrayList<>();
                    List<Integer> gwcidlist = new ArrayList<>();
                    ScOrderListDetail item = null;
                    String ordernumber =  getOrderNumber();
                    orderListBase.setOrderId(ordernumber);
                    orderListBase.setPmid(pmid);
                    orderListBase.setPurchaseMid(purchase_mid);
                    Byte iskp = 0;
                    orderListBase.setIskp(iskp);
                    orderListBase.setCreatTime(getCurrDate2());
                    orderListBase.setCreateUserId(userid);
                    orderListBase.setTicketType(ticket);
                    if(ticket == 1){
                        orderListBase.setDestination(fhData.getDaozhanCangkuName());
                        orderListBase.setArrivalCode(fhData.getDaozhanma());
                        orderListBase.setFreight(Double.parseDouble(fhData.getYunfei()));
                    }
                   
                    Double sum_piece = 0.0;
                    Double sum_num = 0.0;
                    Double total_price = 0.0;
                    Byte exist_save_action = 1;
                    Byte status = 2;//如有议价不通过的生成待确认订单，议价通过生成待付款订单，
                    for(int i = 0; i<cangku_entry.getValue().size(); i++){
                        int option = cangku_entry.getValue().get(i);
                        System.out.println("status_arr=================="+status_arr[option]);
                        if(status_arr[option].equals("1")){
                            //生成待确认订单
                            status = 1;
                        }
                        item = new ScOrderListDetail();
                        item.setResourceId(Integer.parseInt(resources_ids[option]));
                        item.setPmid(pmid);
                        item.setPurchaseMid(purchase_mid);
                        item.setPiece(Double.parseDouble(weights2[option]));
                        item.setNum(Double.parseDouble(weights[option]));
                        item.setPrice(Double.parseDouble(bargains[option]));
                        item.setOriginalPrice(res_map.get(option).getPrice());
                        item.setCreatTime(getCurrDate2());
                        item.setCreateUserId(userid);
                        item.setExistSaveAction(exist_save_action);
                        //if (is_split_lading.equals(1)) 
                        if(option < dcfh_vaules.length){
                            if(res_map.get(option).getVid().equals(Integer.parseInt(CommonConstant.LUOWEN_VID+""))){
                                item.setExtractPiece(dcfh_vaules[option]);
                                if(!dcfh_vaules[option].equals("")){
                                    String extractNum = "";
                                    ScPieceWeightManage sc_weight = scPieceWeightManageMapper.getByGcShortSpec(res_map.get(option).getFactory(),res_map.get(option).getSpecification(),pmid);
                                    String[] jianzhong_arr = dcfh_vaules[option].split(",");
                                    for (String jianzhong : jianzhong_arr) {
                                        if(extractNum == ""){
                                            extractNum = (Double.parseDouble(jianzhong) * sc_weight.getWeight()) + "";
                                        }else{
                                            extractNum = extractNum + "," + (Double.parseDouble(jianzhong) * sc_weight.getWeight());
                                        }
                                    }
                                    item.setExtractNum(extractNum);
                                }
                                
                            }else{
                                item.setExtractNum(dcfh_vaules[option]);
                            }
                        }else{
                            item.setExtractNum("");
                        }

                        scOrderListDetails.add(item);

                        sum_piece = sum_piece + Double.parseDouble(weights2[option]);
                        sum_num = sum_num + Double.parseDouble(weights[option]);
                        if(ticket == 1){
                            total_price = total_price + (Double.parseDouble(weights[option]) * (Double.parseDouble(bargains[option]) + Double.parseDouble(fhData.getYunfei())));

                        }else{
                            total_price = total_price + (Double.parseDouble(weights[option]) * (Double.parseDouble(bargains[option])));
                        }
                        gwcidlist.add(Integer.parseInt(gwc_id_arr[option]));
                    }
                    orderListBase.setStatus(status);
                    orderListBase.setPiece(sum_piece);
                    orderListBase.setNum(sum_num);
                    
                    orderListBase.setTotalPrice((double)Math.round(total_price*100)/100);

                    //生成订单
                    scOrderListBaseMapper.insert(orderListBase);
                    for(int k = 0 ; k < scOrderListDetails.size() ; k++){
                        scOrderListDetails.get(k).setOrderBaseid(ordernumber);
                        scOrderListDetailMapper.insert(scOrderListDetails.get(k));
                        
                    }

                    if(status.equals(is_status)){
                        //冻结余额接口调用
                        try {
                            scOrderListService.orderMoneyMach(pmid, purchase_mid, orderListBase.getTotalPrice());
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    //清空采购车
                    scShoppingCartMapper.deleteScShoppingCartById(gwcidlist);
                    //调用定时程序自动变更订单状态
                    orderStatusjobService.saveComTask(ordernumber, pmid);

                    //检查是否超量，如果超量发送短信
                    sendSms(resources_ids,res_map,pmid,session.getSysCompany().getComname());
                }
            }
        }
        //System.out.println("order_data_map====="+order_data_map.toString());
        aResult.put("success", 1);
        aResult.put("msg", "订单生成成功！");
        return aResult;
    }

    //发送短信
    public void sendSms(String[] resources_ids,Map<Integer, ScShelfResource> res_map,Integer pmid,String comname){
        for(int n = 0; n < resources_ids.length; n++){
            Integer cl_resid= Integer.parseInt(resources_ids[n]);
            Double kgsl = 0.0;//可购买数量
            String sms_content = "&content=";//短信内容
            for (Map.Entry<Integer,ScShelfResource> resData : res_map.entrySet()){
                if(resData.getValue().getId().equals(cl_resid)){
                    kgsl = resData.getValue().getNum() - resData.getValue().getSoldNum();
                    sms_content += resData.getValue().getFactory() + resData.getValue().getVarietyName() + resData.getValue().getMaterial() + resData.getValue().getSpecification();
                    break;
                }
            }
            if(!kgsl.equals(0.0)){
                BigDecimal total_number = scOrderListDetailMapper.selectSumbyresourceIdAndPmid(cl_resid,pmid,2,getCurrDate());//订单状态：0取消、1待确认、2待付款、3已付款、4已结算
                Double z_gmsl = 0.0;//已购买数量
                if(total_number == null)
                    z_gmsl = 0.0;
                else
                    z_gmsl = total_number.doubleValue();
                if(z_gmsl > kgsl){
                    List<SysAdminuser> cl_adminuser = sysAdminuserMapper.selecUserInfoByPmidAndRights(pmid);
                    for(int i = 0 ; i < cl_adminuser.size() ; i++){
                        cl_adminuser.get(i).getMobile();
                        if(!cl_adminuser.get(i).getMobile().equals("")){
                            String send_sms_conten_param = sms_conten_param + cl_adminuser.get(i).getMobile();
                            send_sms_conten_param += sms_content + "," + comname + "客户下订单超量未付款，为避免提货单生成延迟，请及时与客户联系！";
                            String returnmessage = HttpUtils.sendGet(sms_content_url, send_sms_conten_param);
                            JSONObject userJson = JSONObject.parseObject(returnmessage);
                            returnmessage=userJson.toString();
                        }
                    }
                }
            }
        }
    }
}
