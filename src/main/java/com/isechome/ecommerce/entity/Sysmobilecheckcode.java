package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class Sysmobilecheckcode implements Serializable {
    private Integer id;

    private Integer mid;

    private String signcs;

    private String mobile;

    private String checkcode;

    private Boolean checktype;

    private Date createdate;

    private String createuser;

    private Byte isused;

    private String useduser;

    private Date useddate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getSigncs() {
        return signcs;
    }

    public void setSigncs(String signcs) {
        this.signcs = signcs == null ? null : signcs.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode == null ? null : checkcode.trim();
    }

    public Boolean getChecktype() {
        return checktype;
    }

    public void setChecktype(Boolean checktype) {
        this.checktype = checktype;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Byte getIsused() {
        return isused;
    }

    public void setIsused(Byte isused) {
        this.isused = isused;
    }

    public String getUseduser() {
        return useduser;
    }

    public void setUseduser(String useduser) {
        this.useduser = useduser == null ? null : useduser.trim();
    }

    public Date getUseddate() {
        return useddate;
    }

    public void setUseddate(Date useddate) {
        this.useddate = useddate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", signcs=").append(signcs);
        sb.append(", mobile=").append(mobile);
        sb.append(", checkcode=").append(checkcode);
        sb.append(", checktype=").append(checktype);
        sb.append(", createdate=").append(createdate);
        sb.append(", createuser=").append(createuser);
        sb.append(", isused=").append(isused);
        sb.append(", useduser=").append(useduser);
        sb.append(", useddate=").append(useddate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}