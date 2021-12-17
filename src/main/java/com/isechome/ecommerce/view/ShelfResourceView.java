package com.isechome.ecommerce.view;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.ShelfResource;
import com.isechome.ecommerce.service.ShelfResourceService;
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
@RequestMapping("shelf_resource")
public class ShelfResourceView {

    @Autowired
    ShelfResourceService shelfResourceService;

    /**
     * @return java.lang.String
     * @Description 上架资源管理列表
     * @Author zhaofy
     * @Date 2021/8/21
     * @Param [request, page, model]
     **/
    @PreAuthorize("hasRole('ROLE_GYLQX_MASTERKC') or (hasRole('ROLE_GYLQX_MASTER') and hasRole('ROLE_GYLQX_PTF'))")
    @RequestMapping("index")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        Model model, ShelfResource shelfResource) {
        //默认当天数据
        shelfResource.setDate(shelfResource.getDate() == null ? DateUtil.parseDate(DateUtil.today()) :
                shelfResource.getDate());
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        List<ShelfResource> resourceList =
                shelfResourceService.selectListByResource(shelfResource,null,null);
        PageInfo<ShelfResource> pageInfo = new PageInfo<>(resourceList, CommonConstant.NAVIGATE_PAGES);
        model.addAttribute("resourceList", resourceList);
        model.addAttribute("shelfResource", shelfResource);
        model.addAttribute("pageInfo", pageInfo);
        return "shelfResource/index";
    }
}
