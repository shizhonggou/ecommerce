package com.isechome.ecommerce.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.ScWorkTime;
import com.isechome.ecommerce.entity.ShelfResource;
import com.isechome.ecommerce.service.ResourceSalesService;
import com.isechome.ecommerce.service.ShelfResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.util.List;


@Controller
@Slf4j
/**
 * @Description:前台资源显示
 * @Author: shizg
 * @Date: 2021/5/31 11:28
 * @param null:
 * @return: null
 **/
@RequestMapping("/index")
public class ResourceShowController {
    @Autowired
    private ResourceSalesService resourceSalesService;

    @Autowired
    private ShelfResourceService shelfResourceService;

    /**
     * @Description:资源显示列表
     * @Author: shizg
     * @Date: 2021/5/31 11:29
     * @param request:
     * @param model:
     * @return: java.lang.String
     **/
    /*@RequestMapping({"/",""})
    public String resourceShow( HttpServletRequest request, Model model ){
        resourceSalesService.showResourceList( request, model );
        return "resource/show";
    }*/

    /**
     * @return java.lang.String
     * @Description 首页资源列表
     * @Author zhaofy
     * @Date 2021/8/30
     * @Param [page, model, shelfResource]
     **/
    @RequestMapping({"/", ""})
    public String resourceShow(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "vid", required = false, defaultValue = "1") Integer vid,
                               Model model, ShelfResource shelfResource, String searchwhere, String searchVid) {
        //未登录显示开盘时间
        String work_time_str = SecurityUserUtil.getPmid() != null ? "" : setWorkTime();
        //默认当天数据
        shelfResource.setDate(shelfResource.getDate() == null ? DateUtil.parseDate(DateUtil.today()) :
                shelfResource.getDate());
        shelfResource.setVId(vid);
        shelfResource.setStatus(CommonConstant.SHELF_STATUS_SHANGJIA);

        boolean isStart = false;
        if (SecurityUserUtil.getCurrentUserId() != null) {
            ScWorkTime scWorkTime = shelfResourceService.selectWorkTime();
            if (ObjectUtil.isNotEmpty(scWorkTime))
            isStart = Common.isEffectiveDate(scWorkTime.getStartTime(), scWorkTime.getEndTime());
        }

        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        List<ShelfResource> resourceList =
                shelfResourceService.selectListByResource(shelfResource, searchwhere, searchVid);
        PageInfo<ShelfResource> pageInfo = new PageInfo<>(resourceList, CommonConstant.NAVIGATE_PAGES);

        model.addAttribute("resourceList", resourceList);
        model.addAttribute("shelfResource", shelfResource);
        model.addAttribute("vid", shelfResource.getVId());
        model.addAttribute("searchwhere", searchwhere);
        model.addAttribute("searchVid", searchVid);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("title", "资源列表");
        model.addAttribute("isStart", isStart);
        model.addAttribute("work_time_str", work_time_str);
        model.addAttribute("user_id", SecurityUserUtil.getCurrentUserId());
        return "resource/show";
    }

    /**
     * @return void
     * @Description 获取开盘时间
     * @Author zhaofy
     * @Date 2021/12/2
     * @Param []
     **/
    protected String setWorkTime() {
        //显示开盘时间
        ScWorkTime scWorkTime = shelfResourceService.selectWorkTime();
        if (ObjectUtil.isNotEmpty(scWorkTime)) {
            LocalTime s_time = LocalTime.of(scWorkTime.getStartTime().getHours(), scWorkTime.getStartTime().getMinutes(),
                    scWorkTime.getStartTime().getSeconds());
            LocalTime e_time = LocalTime.of(scWorkTime.getEndTime().getHours(), scWorkTime.getEndTime().getMinutes(),
                    scWorkTime.getEndTime().getSeconds());
            return s_time + " - " + e_time;
        }else {
            return "";
        }
    }
}
