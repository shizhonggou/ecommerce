package com.isechome.ecommerce.entity;

import java.io.Serializable;

public class ScPurchasDetail implements Serializable {
    private Integer id;

    private Integer baseId;

    private String orderId;

    private String extractId;

    private Double purchaseNum;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getExtractId() {
        return extractId;
    }

    public void setExtractId(String extractId) {
        this.extractId = extractId == null ? null : extractId.trim();
    }

    public Double getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Double purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", baseId=").append(baseId);
        sb.append(", orderId=").append(orderId);
        sb.append(", extractId=").append(extractId);
        sb.append(", purchaseNum=").append(purchaseNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}