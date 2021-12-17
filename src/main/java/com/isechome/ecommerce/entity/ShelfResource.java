package com.isechome.ecommerce.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShelfResource implements Serializable {
    private Integer id;

    private Integer wId;

    private Integer vId;

    private String varietyName;

    private String material;

    private String specification;

    private Double hd;

    private Double kd;

    private Double cd;

    private Double xd;

    private String factory;

    private String warehouse;

    private Double price;

    private Double num;

    private Double soldNum;

    @DateTimeFormat(pattern  = "yyyy-MM-dd")
    private Date date;

    private Integer status;

    private Integer type;

    private Integer systemType;

    private Integer pmid;

    @DateTimeFormat(pattern  = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //件重
    private double weight;
}