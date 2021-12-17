package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.TaskConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskConfigEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int realDeleteByPrimaryKey(Integer id);

    int insert(TaskConfigEntity record);

    TaskConfigEntity selectByPrimaryKey(Integer id);

    TaskConfigEntity selectByTaskId(String task_id);

    TaskConfigEntity getTask(TaskConfigEntity record);

    String getMaxTaskId(String class_name);

    String getMaxTaskIdByTaskId(String task_id_head);

    List<TaskConfigEntity> selectAll();

    List<TaskConfigEntity> selectNowTaskByClass(String cron, String class_name);

    int updateByPrimaryKey(TaskConfigEntity record);

    int updateTask(TaskConfigEntity record);

    /**
     * @return java.util.List<com.isechome.ecommerce.entity.TaskConfigEntity>
     * @Description 任务调度搜索
     * @Author zhaofy
     * @Date 2021/8/28
     * @Param []
     **/
    List<TaskConfigEntity> selectBySearch(TaskConfigEntity record);

    /**
     * @Description 停启用任务
     * @Author zhaofy
     * @Date  2021/8/31
     * @Param [task_id, status]
     * @return void
     **/
    void changeTaskByTaskId(@Param("task_id") String task_id, @Param("status") int status);

    /**
     * @Description 任务软删除
     * @Author zhaofy
     * @Date  2021/8/31
     * @Param [task_id, is_deleted]
     * @return void
     **/
    void changeTaskDeleteByTaskId(@Param("task_id") String task_id);
}