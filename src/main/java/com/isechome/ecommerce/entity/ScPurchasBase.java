package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;


public class ScPurchasBase implements Serializable {
    private Integer id;

    private Date date;

    private Integer resourceId;

    private Byte type;

    private Integer kind;

    private Double distributeNum;

    private Double purchaseNum;

    private Double price;

    private String depository;

    private Double purchaseAmount;

    private Integer purchaseUserId;

    private String supplierName;

    private Integer supplierMid;

    private Date purchaseTime;

    private Integer auditUserId;

    private Date auditTime;

    private Integer createUserId;

    private Date createTime;

    private ScShelfResource  scshelfResource;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Double getDistributeNum() {
        return distributeNum == null ? 0.0 : distributeNum;
    }

    public void setDistributeNum(Double distributeNum) {
        this.distributeNum = distributeNum;
    }

    public Double getPurchaseNum() {
        return purchaseNum == null ? 0.0 : purchaseNum;
    }

    public void setPurchaseNum(Double purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDepository() {
        return depository;
    }

    public void setDepository(String depository) {
        this.depository = depository == null ? null : depository.trim();
    }

    public Double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Integer getPurchaseUserId() {
        return purchaseUserId;
    }

    public void setPurchaseUserId(Integer purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Integer getSupplierMid() {
        return supplierMid;
    }

    public void setSupplierMid(Integer supplierMid) {
        this.supplierMid = supplierMid;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", date=").append(date);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", type=").append(type);
        sb.append(", kind=").append(kind);
        sb.append(", distributeNum=").append(distributeNum);
        sb.append(", purchaseNum=").append(purchaseNum);
        sb.append(", price=").append(price);
        sb.append(", depository=").append(depository);
        sb.append(", purchaseAmount=").append(purchaseAmount);
        sb.append(", purchaseUserId=").append(purchaseUserId);
        sb.append(", supplierName=").append(supplierName);
        sb.append(", supplierMid=").append(supplierMid);
        sb.append(", purchaseTime=").append(purchaseTime);
        sb.append(", auditUserId=").append(auditUserId);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}