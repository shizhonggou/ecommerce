package com.isechome.ecommerce.listener;

import com.isechome.ecommerce.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 监听容器启动,并开始从数据库加载定时任务
 */
@Component
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    private ScheduleJobService jobService;

    @Override
    public void run(String... strings) throws Exception {
        jobService.startJob();
    }
}
