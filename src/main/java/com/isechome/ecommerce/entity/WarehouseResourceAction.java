package com.isechome.ecommerce.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class WarehouseResourceAction implements Serializable {
    private Integer id;

    private Integer wId;

    private Double num;

    private Double reservedNum;

    private Date reservedDate;

    private Integer type;

    @DateTimeFormat(pattern  = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer auditUserid;

    private Integer systemType;

    private Integer pmid;

    private String typeName;

    private String userName;

    private String pmName;

}