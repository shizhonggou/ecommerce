package com.isechome.ecommerce.Job;

import cn.hutool.core.date.DateUtil;

import com.isechome.ecommerce.service.OrderStatusjobService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDelayJob implements Job {
    @Autowired
    OrderStatusjobService orderStatusjobService;  

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String orderid = jobExecutionContext.getJobDetail().getDescription().toString();
        String task_id = jobExecutionContext.getJobDetail().getKey().getName();
        orderStatusjobService.orderDelayJob(orderid, task_id);
        System.out.println("订单延时定时任务开始：" + DateUtil.date(System.currentTimeMillis()));
    }
}