package com.isechome.ecommerce.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author zhaofy
 * @Date 2021/8/17
 * @Param
 * @return
 **/
@Data
public class TaskConfigEntity implements Serializable {
    private Integer id;

    private String taskId;

    private String cron;

    private String className;

    private String description;

    private Byte status;

    private Byte isDeleted;

    private Integer pmid;

    private String pmName;
}