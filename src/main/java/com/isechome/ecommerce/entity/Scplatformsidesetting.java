package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class Scplatformsidesetting implements Serializable {
    private Integer id;

    private Integer pmid;

    private String token;

    private Byte status;

    private String logoUrl;

    private String description;

    private String backgroundType;

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

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType == null ? null : backgroundType.trim();
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
        sb.append(", pmid=").append(pmid);
        sb.append(", token=").append(token);
        sb.append(", status=").append(status);
        sb.append(", logoUrl=").append(logoUrl);
        sb.append(", description=").append(description);
        sb.append(", backgroundType=").append(backgroundType);
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