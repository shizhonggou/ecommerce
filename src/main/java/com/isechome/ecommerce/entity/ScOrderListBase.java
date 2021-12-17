package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ScOrderListBase implements Serializable {
    private Integer id;

    private String orderId;

    private Integer pmid;

    private Integer purchaseMid;

    private Double piece;

    private Double bpiece;

    private Double num;

    private Double totalPrice;

    private Double settlementWeight;

    private Double settlementMoney;

    private Byte status;

    private String destination;

    private String arrivalCode;

    private Double freight;

    private Integer ticketType;

    private Byte iskp;

    private Date orderExpirationTime;

    private Date confirmTime;

    private Integer confirmUserId;

    private Date creatTime;

    private Integer createUserId;

    private ScShelfResource scshelfResource;

    private List<ScOrderListDetail> scOrderListDetail;

    private List<LogisticsPurchase> scOrderListPurchase;

}