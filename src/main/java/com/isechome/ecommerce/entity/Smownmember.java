package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class Smownmember implements Serializable {
    private Integer id;

    private Integer applyuid;

    private Integer applymid;

    private String contactman;

    private String contacttel;

    private String mobile;

    private String email;

    private String applydesc;

    private Date applydate;

    private Integer reviewuid;

    private Integer reviewmid;

    private Date reviewdate;

    private String remark;

    private Byte status;

    private String manageuser;

    private Date managedate;

    private String managedesc;

    private Boolean dzstatus;

    private Byte level;

    private Double preferentialAmount;

    private SysAdminuser SysAdminuser;

    private SysCompany SysCompany;

    private Double credit_amount;

    private String con_file_url;


    public String getCon_file_url() {
        return con_file_url;
    }

    public void setCon_file_url(String con_file_url) {
        this.con_file_url = con_file_url;
    }

    public Double getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(Double credit_amount) {
        this.credit_amount = credit_amount;
    }

    public Integer getMember_type() {
        return member_type;
    }

    public void setMember_type(Integer member_type) {
        this.member_type = member_type;
    }

    private Integer member_type;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyuid() {
        return applyuid;
    }

    public void setApplyuid(Integer applyuid) {
        this.applyuid = applyuid;
    }

    public Integer getApplymid() {
        return applymid;
    }

    public void setApplymid(Integer applymid) {
        this.applymid = applymid;
    }

    public String getContactman() {
        return contactman;
    }

    public void setContactman(String contactman) {
        this.contactman = contactman == null ? null : contactman.trim();
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel == null ? null : contacttel.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getApplydesc() {
        return applydesc;
    }

    public void setApplydesc(String applydesc) {
        this.applydesc = applydesc == null ? null : applydesc.trim();
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public Integer getReviewuid() {
        return reviewuid;
    }

    public void setReviewuid(Integer reviewuid) {
        this.reviewuid = reviewuid;
    }

    public Integer getReviewmid() {
        return reviewmid;
    }

    public void setReviewmid(Integer reviewmid) {
        this.reviewmid = reviewmid;
    }

    public Date getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(Date reviewdate) {
        this.reviewdate = reviewdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getManageuser() {
        return manageuser;
    }

    public void setManageuser(String manageuser) {
        this.manageuser = manageuser == null ? null : manageuser.trim();
    }

    public Date getManagedate() {
        return managedate;
    }

    public void setManagedate(Date managedate) {
        this.managedate = managedate;
    }

    public String getManagedesc() {
        return managedesc;
    }

    public void setManagedesc(String managedesc) {
        this.managedesc = managedesc == null ? null : managedesc.trim();
    }

    public Boolean getDzstatus() {
        return dzstatus;
    }

    public void setDzstatus(Boolean dzstatus) {
        this.dzstatus = dzstatus;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Double getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(Double preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public SysAdminuser getSysAdminuser() {
        return SysAdminuser;
    }

    public void setSysAdminuser(SysAdminuser sysAdminuser) {
        SysAdminuser = sysAdminuser;
    }

    public SysCompany getSysCompany() {
        return SysCompany;
    }

    public void setSysCompany(SysCompany sysCompany) {
        SysCompany = sysCompany;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyuid=").append(applyuid);
        sb.append(", applymid=").append(applymid);
        sb.append(", contactman=").append(contactman);
        sb.append(", contacttel=").append(contacttel);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", applydesc=").append(applydesc);
        sb.append(", applydate=").append(applydate);
        sb.append(", reviewuid=").append(reviewuid);
        sb.append(", reviewmid=").append(reviewmid);
        sb.append(", reviewdate=").append(reviewdate);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", manageuser=").append(manageuser);
        sb.append(", managedate=").append(managedate);
        sb.append(", managedesc=").append(managedesc);
        sb.append(", dzstatus=").append(dzstatus);
        sb.append(", level=").append(level);
        sb.append(", preferentialAmount=").append(preferentialAmount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}