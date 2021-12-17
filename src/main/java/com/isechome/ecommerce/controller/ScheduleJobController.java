package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.entity.TaskConfigEntity;
import com.isechome.ecommerce.service.ScheduleJobService;
import com.isechome.ecommerce.service.WarehouseResourceService;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 任务调度控制器
 * @Author zhaofy
 * @Date 2021/8/17
 * @Param
 * @return
 **/
@RestController
@RequestMapping("job")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService jobService;

    @Autowired
    private WarehouseResourceService warehouseResourceService;

    @PostMapping("delete")
    public AjaxResult unloadJob(@RequestParam("taskId") String taskId) {
        try {
            jobService.unloadJob(taskId);
        } catch (SchedulerException e) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @PostMapping("stop")
    public AjaxResult stopJob(@RequestParam(value = "taskId") String taskId) {
        try {
            jobService.stopJob(taskId);
        } catch (SchedulerException e) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @PostMapping("resume")
    public AjaxResult resumeJob(@RequestParam(value = "taskId") String taskId) {
        try {
            jobService.resumeJob(taskId);
        } catch (SchedulerException e) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 添加任务
     * @Author zhaofy
     * @Date 2021/8/31
     * @Param [taskConfigEntity]
     **/
    @PostMapping("do_add")
    public AjaxResult doAdd(TaskConfigEntity taskConfigEntity) {
        try {
            jobService.loadJob(taskConfigEntity);
        } catch (SchedulerConfigException e) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 修改任务
     * @Author zhaofy
     * @Date 2021/8/31
     * @Param [taskConfigEntity]
     **/
    @PostMapping("do_edit")
    public AjaxResult doEdit(TaskConfigEntity taskConfigEntity) {
        return jobService.updateByPrimaryKey(taskConfigEntity);
    }

    /**
     * @return java.lang.String
     * @Description 测试用
     * @Author zhaofy
     * @Date 2021/8/26
     * @Param []
     **/
    @GetMapping("test")
    public String test(String base_id) {
//        warehouseResourceService.shelfResource(191233);
        warehouseResourceService.returnResource(191233);
//        warehouseResourceService.returnResourceByOrderId(base_id);
        return "成功";
    }
}
