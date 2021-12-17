package com.isechome.ecommerce.service;

import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.entity.ScOrderListBase;
import com.isechome.ecommerce.entity.ScWorkTime;
import com.isechome.ecommerce.entity.TaskConfigEntity;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListBaseMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScWorkTimeMapper;
import com.isechome.ecommerce.mapper.ecommerce.TaskConfigEntityMapper;

import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class OrderStatusjobService {
    @Autowired
    ScWorkTimeMapper scWorkTimeMapper;
    @Autowired
    TaskConfigEntityMapper taskConfigEntityMapper;
    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper;
    @Autowired
    ScheduleJobService scheduleJobService;
    @Autowired
    ScOrderListService scOrderListService;
    
    /**
     * @Author: lina
     * @msg: 保存订单入账延时任务接口
     * @param {String} orderid
     * @param {Integer} pmid
     * @return {*}
     */    
    public void saveComTask(String orderid, Integer pmid) {
        if (pmid != null) {
            Common common=new Common();
            ScWorkTime scWorkTime = new ScWorkTime();
            scWorkTime = scWorkTimeMapper.selectByPmid(pmid);
            if (scWorkTime != null) {
                Date delay_time = scWorkTime.getDelayTime();
                Integer delay_hour = common.getHour(delay_time);
                Integer delay_minu = common.getMinute(delay_time);
                Date dateNow = new Date();
                long datetime = dateNow.getTime() + delay_hour*60*60*1000L + delay_minu*60*1000L;
                String dateFormat="ss mm HH dd MM ?"; 
                String cron = common.dateToStr(new Date(datetime), dateFormat);
                
                String task_id_head = "OS";
                String class_name = "com.isechome.ecommerce.Job.OrderDelayJob";
                String max_task_id = taskConfigEntityMapper.getMaxTaskId(class_name);
                if (max_task_id == null) {
                    max_task_id = "00000000";
                } else {
                    max_task_id = max_task_id.replace(task_id_head, "");
                }
                Integer taskid = Integer.parseInt(max_task_id) + 1;
                String task_id = task_id_head + String.format("%08d", taskid);
                Byte status = 1;
                Byte is_deleted = 0;
                TaskConfigEntity taskConfigEntity = new TaskConfigEntity();
                taskConfigEntity.setTaskId(task_id);
                taskConfigEntity.setCron(cron);
                taskConfigEntity.setClassName(class_name);
                taskConfigEntity.setDescription(orderid);
                taskConfigEntity.setStatus(status);
                taskConfigEntity.setIsDeleted(is_deleted);
                taskConfigEntity.setPmid(pmid);
                try {
                    scheduleJobService.loadJob(taskConfigEntity);
                } catch (SchedulerConfigException e) {
                    e.printStackTrace(); 
                }
            }
        }
    }

    /**
     * @Author: lina
     * @msg: 执行订单入账延时定时程序
     * @param {String} orderid
     * @param {String} task_id
     * @return {*}
     */    
    public void orderDelayJob(String orderid, String task_id) {
        ScOrderListBase scOrderListBase = new ScOrderListBase();
        scOrderListBase = scOrderListBaseMapper.getInfoByOrderId(orderid);
        Byte status = scOrderListBase.getStatus();
        if (status == 2) {
            Byte status2 = 1;
            scOrderListService.checkOrderSale(orderid); //订单延迟付款时间到时取消订单调用上面施陶弟提供的接口
            scOrderListBaseMapper.updateOrderStatus(scOrderListBase.getId(), status2);
        }
        try {
            scheduleJobService.unloadJob(task_id);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
