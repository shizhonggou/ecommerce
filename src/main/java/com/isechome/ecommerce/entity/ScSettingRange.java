package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class ScSettingRange implements Serializable {
    private Integer id;

    private String description;

    private Double jiajianjia;

    private String varietyName;

    private Byte kind;

    private Byte status;

    private Integer pmid;

    private Date date;

    private Byte reviewType;

    private Date createDate;

    private Integer adminid;

    private Date reviewDate;

    private Integer reviewId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Double getJiajianjia() {
        return jiajianjia;
    }

    public void setJiajianjia(Double jiajianjia) {
        this.jiajianjia = jiajianjia;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName == null ? null : varietyName.trim();
    }

    public Byte getKind() {
        return kind;
    }

    public void setKind(Byte kind) {
        this.kind = kind;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getReviewType() {
        return reviewType;
    }

    public void setReviewType(Byte reviewType) {
        this.reviewType = reviewType;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", description=").append(description);
        sb.append(", jiajianjia=").append(jiajianjia);
        sb.append(", varietyName=").append(varietyName);
        sb.append(", kind=").append(kind);
        sb.append(", status=").append(status);
        sb.append(", pmid=").append(pmid);
        sb.append(", date=").append(date);
        sb.append(", reviewType=").append(reviewType);
        sb.append(", createDate=").append(createDate);
        sb.append(", adminid=").append(adminid);
        sb.append(", reviewDate=").append(reviewDate);
        sb.append(", reviewId=").append(reviewId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}