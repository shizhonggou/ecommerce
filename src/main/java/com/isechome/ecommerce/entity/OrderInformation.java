package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderInformation implements Serializable {
    private Integer id;

    private String orderId;

    private Integer rsId;

    private Integer purchaseMid;

    private Byte orderType;

    private Double piece;

    private Double num;

    private Double actualPiece;

    private Double actualNum;

    private Double price;

    private Date orderExpirationTime;

    private Byte status;

    private Byte whereFrom;

    private Date creatTime;

    private Integer createUserId;

    private Date confirmTime;

    private Integer confirmUserId;

    private ResourceSales resourceSales;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getRsId() {
        return rsId;
    }

    public void setRsId(Integer rsId) {
        this.rsId = rsId;
    }

    public Integer getPurchaseMid() {
        return purchaseMid;
    }

    public void setPurchaseMid(Integer purchaseMid) {
        this.purchaseMid = purchaseMid;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Double getPiece() {
        return piece;
    }

    public void setPiece(Double piece) {
        this.piece = piece;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getActualPiece() {
        return actualPiece;
    }

    public void setActualPiece(Double actualPiece) {
        this.actualPiece = actualPiece;
    }

    public Double getActualNum() {
        return actualNum;
    }

    public void setActualNum(Double actualNum) {
        this.actualNum = actualNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getOrderExpirationTime() {
        return orderExpirationTime;
    }

    public void setOrderExpirationTime(Date orderExpirationTime) {
        this.orderExpirationTime = orderExpirationTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getWhereFrom() {
        return whereFrom;
    }

    public void setWhereFrom(Byte whereFrom) {
        this.whereFrom = whereFrom;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Integer confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public ResourceSales getResourceSales() {
        return resourceSales;
    }

    public void setResourceSales(ResourceSales resourceSales) {
        this.resourceSales = resourceSales;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", rsId=").append(rsId);
        sb.append(", purchaseMid=").append(purchaseMid);
        sb.append(", orderType=").append(orderType);
        sb.append(", piece=").append(piece);
        sb.append(", num=").append(num);
        sb.append(", actualPiece=").append(actualPiece);
        sb.append(", actualNum=").append(actualNum);
        sb.append(", price=").append(price);
        sb.append(", orderExpirationTime=").append(orderExpirationTime);
        sb.append(", status=").append(status);
        sb.append(", whereFrom=").append(whereFrom);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", confirmUserId=").append(confirmUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}