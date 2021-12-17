/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-08-25 17:20:47
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\ScPieceWeightManageService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.ScPieceWeightManage;
import com.isechome.ecommerce.entity.ScSteelMill;
import com.isechome.ecommerce.mapper.ecommerce.ScPieceWeightManageMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSteelMillMapper;
import com.isechome.ecommerce.security.SecuritySysUser;

@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ScPieceWeightManageService {
    @Autowired
    ScPieceWeightManageMapper scPieceWeightManageMapper;
    @Autowired
    ScSteelMillMapper scSteelMillMapper;

    private PageInfo<ScPieceWeightManage> pageInfo;

    // 规格件重首页
    public void index(HttpServletRequest request, Model model) {
        Integer pmid = SecurityUserUtil.getPmid();
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

        String steel_name_short = request.getParameter("steel_name_short");
        String spec = request.getParameter("spec");
        String weight = request.getParameter("weight");
        if(steel_name_short == null){
            steel_name_short = "";
        }
        if(spec == null){
            spec = "";
        }
        if(weight == null){
            weight = "";
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pmid",pmid);
        map.put("steel_name_short",steel_name_short);
        map.put("spec",spec);
        map.put("weight",weight);
        List<ScPieceWeightManage> scPieceWeightManageList = new ArrayList<>();
        scPieceWeightManageList = scPieceWeightManageMapper.getPieceWeightList(map);
        System.out.println("---------------------------------------------------------------------");
        System.out.println(scPieceWeightManageList);
        
        pageInfo = new PageInfo<ScPieceWeightManage>(scPieceWeightManageList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("steel_name_short", steel_name_short);
        model.addAttribute("spec", spec);
        model.addAttribute("weight", weight);
        model.addAttribute("scPieceWeightManageList", scPieceWeightManageList);
        model.addAttribute("mark", "scpieceweightmanage");
    }

    // 规格件重编辑
    public void edit(HttpServletRequest request, Model model) {
        Integer pmid = SecurityUserUtil.getPmid();
        String idstr = request.getParameter("id");
        ScPieceWeightManage scPieceWeightManage = new ScPieceWeightManage();
        if(idstr != null && idstr != "" ){
            Integer id = Integer.parseInt(idstr);
            scPieceWeightManage = scPieceWeightManageMapper.selectByPrimaryKey(id);
        }
        List<ScSteelMill> scSteelMillList = new ArrayList<>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pmid",pmid);
        scSteelMillList = scSteelMillMapper.getScSteelMilList(map);

        model.addAttribute("smlist", scSteelMillList);
        model.addAttribute("arrinfo", scPieceWeightManage);
        model.addAttribute("id", idstr);
        model.addAttribute("title", "件重设置");
        model.addAttribute("mark", "scpieceweightmanage");
    }

    //保存修改钢厂件重信息
    public void doedit( HttpServletRequest request){
        SecuritySysUser sessionuser = SecurityUserUtil.getCurrentUser();  
        Integer userid = sessionuser.getId();
        Integer stmid = Integer.parseInt(request.getParameter("stmid"));
        String idstr = request.getParameter("id");
        String spec = request.getParameter("spec");
        Double weight = Double.parseDouble(request.getParameter("weight"));
        Integer id = 0;
        if(idstr != null && !idstr.equals("") ){
            id = Integer.parseInt(idstr);
        }
        ScPieceWeightManage scPieceWeightManage = new ScPieceWeightManage();
        scPieceWeightManage.setId(id);
        scPieceWeightManage.setStmid(stmid);
        scPieceWeightManage.setSpec(spec);
        scPieceWeightManage.setWeight(weight);
        Byte status = 1;
        scPieceWeightManage.setStatus(status);
        Date dateNow = new Date();
        if ( id > 0 ){
            ScPieceWeightManage scPieceWeightManageOld = new ScPieceWeightManage();
            scPieceWeightManageOld = scPieceWeightManageMapper.selectByPrimaryKey(id);
            scPieceWeightManage.setCreatTime(scPieceWeightManageOld.getCreatTime());
            scPieceWeightManage.setCreateUserId(scPieceWeightManageOld.getCreateUserId());
            scPieceWeightManage.setUpdateTime(dateNow);
            scPieceWeightManage.setUpdateUserId(userid);
            scPieceWeightManageMapper.updateByPrimaryKey(scPieceWeightManage);
        }else {
            scPieceWeightManage.setCreatTime(dateNow);
            scPieceWeightManage.setCreateUserId(userid);
            scPieceWeightManageMapper.insert(scPieceWeightManage);
        }
    }

    public void del( HttpServletRequest request ) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Byte status = -1;
        scPieceWeightManageMapper.updateStatusById(id, status);
    }

}
