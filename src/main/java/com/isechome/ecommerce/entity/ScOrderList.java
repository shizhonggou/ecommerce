package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.servlet.View;

public class ScOrderList implements Serializable{
    private Integer id;

    private String orderId;

    private Integer resourceId;

    private Integer pmid;

    private Integer purchaseMid;

    private Double piece;

    private Double num;

    private Double price;

    private Double originalPrice;

    private Double settlementWeight;

    private Double settlementMoney;

    private Byte status;

    private Byte iskp;

    private String extractPiece;

    private String extractNum;

    private Date orderExpirationTime;

    private Date confirmTime;

    private Integer confirmUserId;

    private Date creatTime;

    private Integer createUserId;

    private ScShelfResource  scshelfResource;

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

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public Integer getPurchaseMid() {
        return purchaseMid;
    }

    public void setPurchaseMid(Integer purchaseMid) {
        this.purchaseMid = purchaseMid;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    
    public Double getSettlementWeight() {
        return settlementWeight;
    }

    public void setSettlementWeight(Double settlementWeight) {
        this.settlementWeight = settlementWeight;
    }

    public Double getSettlementMoney() {
        return settlementMoney;
    }

    public void setSettlementMoney(Double settlementMoney) {
        this.settlementMoney = settlementMoney;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIskp() {
        return iskp;
    }

    public void setIskp(Byte iskp) {
        this.iskp = iskp;
    }

    public String getExtractPiece() {
        return extractPiece;
    }

    public void setExtractPiece(String extractPiece) {
        this.extractPiece = extractPiece == null ? null : extractPiece.trim();
    }

    public String getExtractNum() {
        return extractNum;
    }

    public void setExtractNum(String extractNum) {
        this.extractNum = extractNum == null ? null : extractNum.trim();
    }

    public Date getOrderExpirationTime() {
        return orderExpirationTime;
    }

    public void setOrderExpirationTime(Date orderExpirationTime) {
        this.orderExpirationTime = orderExpirationTime;
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

    public ScShelfResource getScShelfResource() {
        return scshelfResource;
    }

    public void setScShelfResource(ScShelfResource scshelfResource) {
        this.scshelfResource = scshelfResource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", pmid=").append(pmid);
        sb.append(", purchaseMid=").append(purchaseMid);
        sb.append(", piece=").append(piece);
        sb.append(", num=").append(num);
        sb.append(", price=").append(price);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", settlementWeight=").append(settlementWeight);
        sb.append(", settlementMoney=").append(settlementMoney);
        sb.append(", status=").append(status);
        sb.append(", iskp=").append(iskp);
        sb.append(", extractPiece=").append(extractPiece);
        sb.append(", extractNum=").append(extractNum);
        sb.append(", orderExpirationTime=").append(orderExpirationTime);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", confirmUserId=").append(confirmUserId);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}