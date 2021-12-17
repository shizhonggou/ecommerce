package com.isechome.ecommerce.service;

import cn.hutool.core.util.ObjectUtil;
import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.TaskConfigEntity;
import com.isechome.ecommerce.mapper.ecommerce.TaskConfigEntityMapper;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 任务调度服务
 * @Author zhaofy
 * @Date 2021/8/17
 * @Param
 * @return
 **/
@Component
@Log4j2
public class ScheduleJobService {
    @Autowired
    private TaskConfigEntityMapper taskConfigDao;

    @Autowired
    private Scheduler scheduler;

    /**
     * 程序启动开始加载定时任务
     */
    public void startJob() {
        List<TaskConfigEntity> taskConfigEntities = taskConfigDao.selectAll();
        if (taskConfigEntities == null || taskConfigEntities.size() == 0) {
            log.error("定时任务加载数据为空");
            return;
        }
        for (TaskConfigEntity configEntity : taskConfigEntities) {
            CronTrigger cronTrigger = null;
            JobDetail jobDetail = null;
            try {
                cronTrigger = getCronTrigger(configEntity);
                jobDetail = getJobDetail(configEntity);
                scheduler.scheduleJob(jobDetail, cronTrigger);
                log.info("编号：{}定时任务加载成功", configEntity.getTaskId());
            } catch (Exception e) {
                log.error("编号：{}定时任务加载失败", configEntity.getTaskId());
//                log.error("定时任务启动失败", e);
            }

        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("定时任务启动失败", e);
        }
    }

    /**
     * 停止任务
     *
     * @param taskId
     */
    public void stopJob(String taskId) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(taskId));
        taskConfigDao.changeTaskByTaskId(taskId, CommonConstant.TASK_STATUS_OFF);
    }

    /**
     * 恢复任务
     *
     * @param taskId
     * @throws SchedulerException
     */
    public void resumeJob(String taskId) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(taskId));
        taskConfigDao.changeTaskByTaskId(taskId, CommonConstant.TASK_STATUS_ON);
    }

    /**
     * 添加新的job
     *
     * @param taskConfigEntity
     * @throws SchedulerConfigException
     */
    public void loadJob(TaskConfigEntity taskConfigEntity) throws SchedulerConfigException {
        taskConfigEntity.setPmid(SecurityUserUtil.getPmid());
        taskConfigEntity.setStatus(CommonConstant.TASK_STATUS_ON);
        int id = taskConfigDao.insert(taskConfigEntity);
//        TaskConfigEntity taskConfigEntity = taskConfigDao.selectByPrimaryKey(id);

        try {
            JobDetail jobDetail = getJobDetail(taskConfigEntity);
            CronTrigger cronTrigger = getCronTrigger(taskConfigEntity);
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            taskConfigDao.realDeleteByPrimaryKey(id);
            log.error("加载定时任务异常", e);
            throw new SchedulerConfigException("加载定时任务异常", e);
        }
    }

    /**
     * @return void
     * @Description 删除任务
     * @Author zhaofy
     * @Date 2021/8/31
     * @Param [taskId]
     **/
    public void unloadJob(String taskId) throws SchedulerException {
        // 停止触发器
        scheduler.pauseTrigger(TriggerKey.triggerKey(taskId));
        // 卸载定时任务
        scheduler.unscheduleJob(TriggerKey.triggerKey(taskId));
        // 删除原来的job
        scheduler.deleteJob(JobKey.jobKey(taskId));
        //数据库软删除
        taskConfigDao.changeTaskDeleteByTaskId(taskId);
    }

    /**
     * 重新加载执行计划
     *
     * @throws Exception
     */
    public void reload(String taskId) throws Exception {
        TaskConfigEntity taskConfigEntity = taskConfigDao.selectByTaskId(taskId);

        String jobCode = taskConfigEntity.getTaskId();
        // 获取以前的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode);
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 删除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除原来的job
        scheduler.deleteJob(JobKey.jobKey(jobCode));

        JobDetail jobDetail = getJobDetail(taskConfigEntity);
        CronTrigger cronTrigger = getCronTrigger(taskConfigEntity);
        // 重新加载job
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    //组装JobDetail
    private JobDetail getJobDetail(TaskConfigEntity configEntity) throws ClassNotFoundException {

        Class<? extends Job> aClass = Class.forName(configEntity.getClassName()).asSubclass(Job.class);

        return JobBuilder.newJob()
                .withIdentity(JobKey.jobKey(configEntity.getTaskId()))
                .usingJobData("taskpmid", configEntity.getPmid())
                .withDescription(configEntity.getDescription())
                .ofType(aClass).build();
    }

    //组装CronTrigger
    private CronTrigger getCronTrigger(TaskConfigEntity configEntity) {
        CronTrigger cronTrigger = null;
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(configEntity.getCron());
        cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(configEntity.getTaskId()))
                .withSchedule(cronScheduleBuilder)
                .build();
        return cronTrigger;
    }

    /**
     * @return java.util.List<com.isechome.ecommerce.entity.TaskConfigEntity>
     * @Description 任务调度搜索列表
     * @Author zhaofy
     * @Date 2021/8/28
     * @Param [taskConfigEntity]
     **/
    public List<TaskConfigEntity> selectListByResource(TaskConfigEntity taskConfigEntity) {
        List<TaskConfigEntity> taskConfigEntityList = taskConfigDao.selectBySearch(taskConfigEntity);
        if (ObjectUtil.isNotEmpty(taskConfigEntityList)){
            for (TaskConfigEntity taskConfigEntity1 : taskConfigEntityList){
                //分割字符串，只保留最后一个 . 后面的类名
                int one = taskConfigEntity1.getClassName().lastIndexOf(".");
                String substring = taskConfigEntity1.getClassName().substring((one + 1));
                taskConfigEntity1.setClassName(substring);
            }
        }
        return taskConfigEntityList;
    }

    /**
     * @return com.isechome.ecommerce.entity.TaskConfigEntity
     * @Description 根据id查询一条记录
     * @Author zhaofy
     * @Date 2021/8/31
     * @Param [id]
     **/
    public TaskConfigEntity selectByPrimaryKey(int id) {
        return taskConfigDao.selectByPrimaryKey(id);
    }

    /**
     * @return void
     * @Description 修改任务
     * @Author zhaofy
     * @Date 2021/8/31
     * @Param [record]
     **/
    public AjaxResult updateByPrimaryKey(TaskConfigEntity record) {
        int count = taskConfigDao.updateByPrimaryKey(record);
        if (count > 0) {
            try {
                reload(record.getTaskId());
                return AjaxResult.success();
            } catch (Exception e) {
                return AjaxResult.error();
            }
        } else {
            return AjaxResult.error();
        }
    }
}
