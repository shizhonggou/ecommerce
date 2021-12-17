package com.isechome.ecommerce.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.util.Date;

public class LogisticsPurchase implements Serializable {
    private Integer id;

    private Integer orderDetailid;

    private String orderId;

    private Integer resourceId;
    
    @ExcelProperty("提单号")
    private String extractId;

    private Byte type;

    private Byte kind;

    private Double distributeNum;

    @ExcelProperty("数量/(t)")
    private Double actualNum;

    private Double num;

    private Double price;

    private String depository;

    private Double purchaseAmount;

    private Integer purchaseUserId;

    private Date purchaseTime;

    private String supplierName;

    private Integer supplierMid;

    private Integer auditUserId;

    private Date auditTime;

    private Integer createUserId;

    private Date creatTime;


    private ScShelfResource scshelfResource;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderDetailid() {
        return orderDetailid;
    }

    public void setOrderDetailid(Integer orderDetailid) {
        this.orderDetailid = orderDetailid;
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

    public String getExtractId() {
        return extractId;
    }

    public void setExtractId(String extractId) {
        this.extractId = extractId == null ? null : extractId.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getKind() {
        return kind;
    }

    public void setKind(Byte kind) {
        this.kind = kind;
    }

    public Double getDistributeNum() {
        return distributeNum;
    }

    public void setDistributeNum(Double distributeNum) {
        this.distributeNum = distributeNum;
    }

    public Double getActualNum() {
        return actualNum;
    }

    public void setActualNum(Double actualNum) {
        this.actualNum = actualNum;
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

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
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

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
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
        sb.append(", orderDetailid=").append(orderDetailid);
        sb.append(", orderId=").append(orderId);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", extractId=").append(extractId);
        sb.append(", type=").append(type);
        sb.append(", kind=").append(kind);
        sb.append(", distributeNum=").append(distributeNum);
        sb.append(", actualNum=").append(actualNum);
        sb.append(", num=").append(num);
        sb.append(", price=").append(price);
        sb.append(", depository=").append(depository);
        sb.append(", purchaseAmount=").append(purchaseAmount);
        sb.append(", purchaseUserId=").append(purchaseUserId);
        sb.append(", purchaseTime=").append(purchaseTime);
        sb.append(", supplierName=").append(supplierName);
        sb.append(", supplierMid=").append(supplierMid);
        sb.append(", auditUserId=").append(auditUserId);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}