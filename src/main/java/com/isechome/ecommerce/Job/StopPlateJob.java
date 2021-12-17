/*
 * @Author: lina
 * @Date: 2021-08-26 15:38:38
 * @LastEditTime: 2021-09-01 09:20:00
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\Job\StopPlateJob.java
 * @Description: 开停盘定时任务
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.Job;

import cn.hutool.core.date.DateUtil;

import com.isechome.ecommerce.service.StopOpenPlateService;

import com.isechome.ecommerce.service.WarehouseResourceService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class StopPlateJob implements Job {
    @Autowired
    StopOpenPlateService stopOpenPlateService; 

    @Autowired
    WarehouseResourceService warehouseResourceService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Integer taskpmid = Integer.valueOf(jobExecutionContext.getJobDetail().getJobDataMap().get("taskpmid").toString());
        //收盘上架资源返回仓库
        warehouseResourceService.returnResource(taskpmid);

        stopOpenPlateService.OpenStopPlateJob("0", taskpmid);

        System.out.println(taskpmid + "停盘定时任务开始：" + DateUtil.date(System.currentTimeMillis()));
    }
}