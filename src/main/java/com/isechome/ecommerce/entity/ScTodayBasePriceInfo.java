package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class ScTodayBasePriceInfo implements Serializable {
    private Integer id;

    private Byte type;

    private Double price;

    private Double reviewPrice;

    private Integer pmid;

    private Byte status;

    private Byte reviewStatus;

    private Date date;

    private Date createDate;

    private Integer adminid;

    private Date reviewDate;

    private Integer reviewId;

    private String adminName;

    private String reviewName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getReviewPrice() {
        return reviewPrice;
    }

    public void setReviewPrice(Double reviewPrice) {
        this.reviewPrice = reviewPrice;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Byte reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName == null ? null : reviewName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", reviewPrice=").append(reviewPrice);
        sb.append(", pmid=").append(pmid);
        sb.append(", status=").append(status);
        sb.append(", reviewStatus=").append(reviewStatus);
        sb.append(", date=").append(date);
        sb.append(", createDate=").append(createDate);
        sb.append(", adminid=").append(adminid);
        sb.append(", reviewDate=").append(reviewDate);
        sb.append(", reviewId=").append(reviewId);
        sb.append(", adminName=").append(adminName);
        sb.append(", reviewName=").append(reviewName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}