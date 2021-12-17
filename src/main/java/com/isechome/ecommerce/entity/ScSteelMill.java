package com.isechome.ecommerce.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ScSteelMill implements Serializable {
    private Integer id;

    private Integer pmid;
    
    private Byte status;
    
    private String steelName;

    private String steelNameShort;

    private String steelDesc;

    private String addressState;

    private String addressCity;

    private String address;

    private String enclosureUrl;

    private Date createDate;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private List<PieceWeight> pieceWeightList;

}