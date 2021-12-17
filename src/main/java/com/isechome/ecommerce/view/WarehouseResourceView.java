package com.isechome.ecommerce.view;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.WarehouseResource;
import com.isechome.ecommerce.entity.WarehouseResourceAction;
import com.isechome.ecommerce.service.WarehouseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 仓库资源信息页面
 * @Author zhaofy
 * @Date 2021/8/19
 **/
@Controller
@RequestMapping("warehouse_resource")
public class WarehouseResourceView {

    @Autowired
    WarehouseResourceService warehouseResourceService;

    /**
     * @return java.lang.String
     * @Description 仓库资源管理列表
     * @Author zhaofy
     * @Date 2021/8/21
     * @Param [request, page, model]
     **/
    @PreAuthorize("hasRole('ROLE_GYLQX_MASTERKC') or (hasRole('ROLE_GYLQX_MASTER') and hasRole('ROLE_GYLQX_PTF'))")
    @RequestMapping("index")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        Model model, WarehouseResource warehouseResource) {
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        List<WarehouseResource> warehouseResourceList =
                warehouseResourceService.selectListByResource(warehouseResource);
        PageInfo<WarehouseResource> pageInfo = new PageInfo<>(warehouseResourceList, CommonConstant.NAVIGATE_PAGES);
        model.addAttribute("warehouseResourceList", warehouseResourceList);
        model.addAttribute("warehouseResource", warehouseResource);
        model.addAttribute("pageInfo", pageInfo);
        return "warehouseResource/index";
    }

    /**
     * @return java.lang.String
     * @Description 仓库资源操作日志列表
     * @Author zhaofy
     * @Date 2021/9/23
     * @Param []
     **/
    @RequestMapping("resource_action")
    public String resource_action(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  Model model, WarehouseResourceAction warehouseResourceAction) {
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        List<WarehouseResourceAction> warehouseResourceActionList =
                warehouseResourceService.selectByResource(warehouseResourceAction);
        PageInfo<WarehouseResourceAction> pageInfo = new PageInfo<>(warehouseResourceActionList,
                CommonConstant.NAVIGATE_PAGES);
        model.addAttribute("warehouseResourceAction", warehouseResourceAction);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("warehouseResourceActionList", warehouseResourceActionList);
        return "warehouseResource/resource_action";
    }
}
