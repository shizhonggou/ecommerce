/*
 * @Author: lina
 * @Date: 2021-05-29 11:40:27
 * @LastEditTime: 2021-09-02 11:10:53
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\service\ScSteelMillService.java
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
import com.isechome.ecommerce.entity.ReturnObj;
import com.isechome.ecommerce.entity.ScPieceWeightManage;
import com.isechome.ecommerce.entity.ScSteelMill;
import com.isechome.ecommerce.mapper.ecommerce.ScPieceWeightManageMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSteelMillMapper;
import com.isechome.ecommerce.security.SecuritySysUser;

@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ScSteelMillService {
    @Autowired
    ScSteelMillMapper scSteelMillMapper;
    @Autowired
    ScPieceWeightManageMapper scPieceWeightManageMapper;

    private PageInfo<ScSteelMill> pageInfo;

    // 钢厂首页
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

        String name = request.getParameter("name");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        if(name == null){
            name = "";
        }
        if(state == null){
            state = "";
        }
        if(city == null){
            city = "";
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pmid",pmid);
        map.put("name",name);
        map.put("state",state);
        map.put("city",city);
        List<ScSteelMill> scSteelMillList = new ArrayList<>();
        scSteelMillList = scSteelMillMapper.getScSteelMilList(map);
        
        pageInfo = new PageInfo<ScSteelMill>(scSteelMillList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);
        model.addAttribute("state", state);
        model.addAttribute("city", city);
        model.addAttribute("city", city);
        model.addAttribute("scSteelMillList", scSteelMillList);
        model.addAttribute("mark", "scsteelmill");
    }

    // 钢厂编辑
    public void edit(HttpServletRequest request, Model model) {
        String idstr = request.getParameter("id");
        ScSteelMill scSteelMill = new ScSteelMill();
        if(idstr != null && idstr != "" ){
            Integer id = Integer.parseInt(idstr);
            scSteelMill = scSteelMillMapper.selectByPrimaryKey(id);
        }
        model.addAttribute("scSteelMill", scSteelMill);
        model.addAttribute("id", idstr);
        model.addAttribute("mark", "scsteelmill");
    }

    //保存修改钢厂信息
    public void doedit( HttpServletRequest request){
        Integer pmid = SecurityUserUtil.getPmid();
        SecuritySysUser sessionuser = SecurityUserUtil.getCurrentUser();  
        Integer userid = sessionuser.getId();
        String idstr = request.getParameter("id");
        String steel_name = request.getParameter("steel_name");
        String steel_name_short = request.getParameter("steel_name_short");
        String steel_desc = request.getParameter("steel_desc");
        String address_state = request.getParameter("address_state");
        String address_city = request.getParameter("address_city");
        String address = request.getParameter("address");
        Integer id = 0;
        if(idstr != null && !idstr.equals("") ){
            id = Integer.parseInt(idstr);
        }
        ScSteelMill scSteelMill = new ScSteelMill();
        scSteelMill.setId(id);
        scSteelMill.setPmid(pmid);
        scSteelMill.setSteelName(steel_name);
        scSteelMill.setSteelNameShort(steel_name_short);
        scSteelMill.setAddressState(address_state);
        scSteelMill.setAddressCity(address_city);
        scSteelMill.setAddress(address);
        scSteelMill.setSteelDesc(steel_desc);
        Date dateNow = new Date();
        if ( id > 0 ){
            ScSteelMill scSteelMillOld = new ScSteelMill();
            scSteelMillOld = scSteelMillMapper.selectByPrimaryKey(id);
            scSteelMill.setCreateDate(scSteelMillOld.getCreateDate());
            scSteelMill.setCreateUserId(scSteelMillOld.getCreateUserId());
            scSteelMill.setUpdateTime(dateNow);
            scSteelMill.setUpdateUserId(userid);
            scSteelMillMapper.updateByPrimaryKey(scSteelMill);
        } else {
            scSteelMill.setCreateDate(dateNow);
            scSteelMill.setCreateUserId(userid);
            scSteelMillMapper.insert(scSteelMill);
        }
    }

    public ReturnObj del( HttpServletRequest request ) {
        ReturnObj returnObj = new ReturnObj();
        returnObj.setSuccess("1");
        returnObj.setMessage("操作成功！");
        Integer id = Integer.parseInt(request.getParameter("id"));
        List<ScPieceWeightManage> scPieceWeightManageList = new ArrayList<>();
        scPieceWeightManageList = scPieceWeightManageMapper.getInfoByStmid(id);
        if (scPieceWeightManageList != null && !scPieceWeightManageList.isEmpty()) {
            returnObj.setSuccess("0");
            returnObj.setMessage("请先删除该钢厂规格件重信息！");
        }
        // System.out.println("---------------------------------------------------------------------");
        // System.out.println(scPieceWeightManageList);

        if (returnObj.getSuccess() == "1") {
            Byte status = -1;
            scSteelMillMapper.updateStatusById(id, status);
        }
        return returnObj;
    }

}
