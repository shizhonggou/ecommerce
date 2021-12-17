package com.isechome.ecommerce.Job;

import cn.hutool.core.date.DateUtil;
import com.isechome.ecommerce.service.WarehouseResourceService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description 资源定时任务
 * @Author zhaofy
 * @Date 2021/8/17
 * @Param
 * @return
 **/
@Log4j2
public class ResourceJob implements Job {
    @Autowired
    WarehouseResourceService warehouseResourceService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //定时上架
        warehouseResourceService.shelfResource(Integer.parseInt(jobExecutionContext.getJobDetail().getJobDataMap().get("taskpmid").toString()));
        log.info("定时任务开始：{}", DateUtil.now());
    }
}