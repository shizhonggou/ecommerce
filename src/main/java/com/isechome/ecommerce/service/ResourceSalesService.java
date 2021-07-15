/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-06-26 10:11:05
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\ResourceSalesService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.security.SecuritySysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.*;

import com.isechome.ecommerce.entity.PieceWeight;
import com.isechome.ecommerce.entity.ResourceSales;
import com.isechome.ecommerce.mapper.isechome.AdminUserInfoMapper;
import com.isechome.ecommerce.mapper.isechome.PieceWeightMapper;
import com.isechome.ecommerce.mapper.isechome.ResourceSalesMapper;
import com.isechome.ecommerce.common.common;
import com.isechome.ecommerce.constant.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class ResourceSalesService {
    @Autowired
    ResourceSalesMapper resourceSalesMapper;
    @Autowired
    PieceWeightMapper pieceWeightMapper;
    @Autowired
    AdminUserInfoMapper adminUserInfoMapper;

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
        if ( loginMessage!=null && loginMessage.getCompanyInfo().getId() > 0 ) {
            purchaseMid = loginMessage.getCompanyInfo().getId();
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
        Integer salesMid = session.getAdminUserInfo().getMid();
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
        // resourceSalesMapper.insertExcel(resourceSales);
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
        common common=new common();
        String allsel = request.getParameter("allsel");
        for (String id: allsel.split(",")){
            Double piece = Double.parseDouble(request.getParameter("piece_"+id));
            Double num = Double.parseDouble(request.getParameter("num_"+id));
            Double price = Double.parseDouble(request.getParameter("price_"+id));
            Date sales_start_time = common.strToDate(request.getParameter("salesStartTime_"+id));
            Date sales_end_time = common.strToDate(request.getParameter("salesEndTime_"+id));
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
     * @Description: 执行加入购物车
     * @Author: shizg
     * @Date: 2021/6/7 16:02
     * @param request :
     * @return: java.lang.Integer
     **/
    public List<ResourceSales> add_purchase_car(HttpServletRequest request) {
        Integer returnInt = 0;
        HttpSession session = request.getSession();
        List<ResourceSales> resourceSalesList = (List<ResourceSales>)session.getAttribute("purchase_car");
        if ( resourceSalesList == null ) {
            resourceSalesList = new ArrayList();
        }
        String residsStr = request.getParameter("resids");
        if (residsStr != null && residsStr != "" ) {
            Integer resid = Integer.parseInt(residsStr);
            ResourceSales resourceSales = getResourceSalesById(resid);
            if ( resourceSales.getId() > 0 ) {
                Boolean existFlag = false; // 判断购物车是否已有该资源
                Iterator<ResourceSales> it = resourceSalesList.iterator();
                while (it.hasNext()) {
                    ResourceSales resourceSalesTemp = it.next();
                    Integer existid = resourceSalesTemp.getId();
                    if ( existid.equals(resid)  ) {
                        existFlag = true;
                        break;
                    }
                }
                if ( !existFlag ) {
                    Double num_0 = 0.0;
                    resourceSales.setWriteNum( num_0 );
                    resourceSales.setWritePiece( num_0 );
                    resourceSalesList.add(resourceSales);
                }
                session.setAttribute("purchase_car", resourceSalesList);
            } else {
                returnInt = 2; //资源不存在
            }
        }else {
            returnInt = 1; // 参数不正确
        }
        log.info( "资源"+residsStr+"加入购物车标识："+returnInt );
        return resourceSalesList;
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
     * @Description: 从购物车删除指定资源
     * @Author: shizg
     * @Date: 2021/6/8 17:36
     * @param request:
     * @return: void
     **/
    public Integer del_purchase_car(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<ResourceSales> resourceSalesList = (List<ResourceSales>)session.getAttribute("purchase_car");
        if ( resourceSalesList != null ) {
            String residsStr = request.getParameter("resids");
            Integer resid = Integer.parseInt(residsStr);

            Iterator<ResourceSales> it = resourceSalesList.iterator();
            while (it.hasNext()) {
                ResourceSales resourceSales = it.next();
                if (resourceSales.getId().equals(resid) ) {
                    it.remove();
                    break;
                }
            }
            session.removeAttribute("purchase_car");
            session.setAttribute("purchase_car", resourceSalesList );

        }
        return 1;
    }

    /**
     * @Description:更新购物车件数数量
     * @Author: shizg
     * @Date: 2021/6/9 17:06
     * @param request:
     * @return: void
     **/
    public void update_purchase_car_num(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<ResourceSales> resourceSalesList = (List<ResourceSales>)session.getAttribute("purchase_car");
        if ( resourceSalesList != null ) {
            String residsStr = request.getParameter("id");
            Integer resid = Integer.parseInt(residsStr);
            String pieceStr = request.getParameter("piece");
            Double piece = Double.parseDouble(pieceStr);
            String numStr = request.getParameter("num");
            Double num = Double.parseDouble(numStr);

            Iterator<ResourceSales> it = resourceSalesList.iterator();
            while (it.hasNext()) {
                ResourceSales resourceSales = it.next();
                if (resourceSales.getId().equals( resid )  ) {
                    int index = resourceSalesList.indexOf(resourceSales);
                    if ( piece > 0 ) {
                        resourceSales.setWritePiece(piece);
                    }
                    resourceSales.setWriteNum(num);
                    resourceSalesList.set(index,resourceSales);
                    break;
                }
            }

            session.setAttribute("purchase_car", resourceSalesList );

        }
    }
}
