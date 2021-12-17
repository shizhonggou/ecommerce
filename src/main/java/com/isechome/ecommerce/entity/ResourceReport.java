package com.isechome.ecommerce.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResourceReport implements Serializable {
    private Integer id;

    private Double salesToday;

    private Double shipToday;

    private Double readyShipToday;

    private Double inventoryToday;

    private Date date;

    private Date createTime;

    private Integer systemType;

    private Integer pmid;
}