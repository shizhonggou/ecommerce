/*
 * @Author: lina
 * @Date: 2021-08-26 15:38:38
 * @LastEditTime: 2021-09-01 10:25:25
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\Job\OpenPlateJob.java
 * @Description: 开停盘定时任务
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.Job;

import cn.hutool.core.date.DateUtil;

import com.isechome.ecommerce.service.StopOpenPlateService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class OpenPlateJob implements Job {
    @Autowired
    StopOpenPlateService stopOpenPlateService; 

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Integer taskpmid = Integer.valueOf(jobExecutionContext.getJobDetail().getJobDataMap().get("taskpmid").toString());
        stopOpenPlateService.OpenStopPlateJob("1", taskpmid);
        System.out.println(taskpmid + "开盘定时任务开始：" + DateUtil.date(System.currentTimeMillis()));
    }
}