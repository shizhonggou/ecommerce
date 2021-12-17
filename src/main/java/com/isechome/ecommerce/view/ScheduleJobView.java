package com.isechome.ecommerce.view;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.TaskConfigEntity;
import com.isechome.ecommerce.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 任务调度管理页面
 * @Author zhaofy
 * @Date  2021/8/28
 **/
@Controller
@RequestMapping("job")
public class ScheduleJobView {

    @Autowired
    ScheduleJobService scheduleJobService;

    /**
     * @Description 任务调度列表
     * @Author zhaofy
     * @Date  2021/8/28
     * @Param [page, model, shelfResource]
     * @return java.lang.String
     **/
    @RequestMapping("index")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        Model model, TaskConfigEntity taskConfigEntity) {
        taskConfigEntity.setPmid(SecurityUserUtil.getPmid());
        PageHelper.startPage(page, CommonConstant.PAGE_SIZE);
        List<TaskConfigEntity> taskConfigEntityList = scheduleJobService.selectListByResource(taskConfigEntity);
        PageInfo<TaskConfigEntity> pageInfo = new PageInfo<>(taskConfigEntityList, CommonConstant.NAVIGATE_PAGES);
        model.addAttribute("taskList", taskConfigEntityList);
        model.addAttribute("taskConfigEntity", taskConfigEntity);
        model.addAttribute("pageInfo", pageInfo);
        return "scheduleJob/index";
    }

    @RequestMapping("add")
    public String add(){
        return "scheduleJob/add";
    }

    @RequestMapping("edit")
    public String edit(@RequestParam(value = "id", required = false) Integer id, Model model){
        TaskConfigEntity taskConfigEntity = scheduleJobService.selectByPrimaryKey(id);
        model.addAttribute("taskConfigEntity", taskConfigEntity);
        return "scheduleJob/edit";
    }
}
