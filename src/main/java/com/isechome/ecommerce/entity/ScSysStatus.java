package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class ScSysStatus implements Serializable {
    private Integer id;

    private Integer pmid;

    private Date date;

    private Byte trantype;

    private Double excessiveStop;

    private Date creatTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getTrantype() {
        return trantype;
    }

    public void setTrantype(Byte trantype) {
        this.trantype = trantype;
    }

    public Double getExcessiveStop() {
        return excessiveStop;
    }

    public void setExcessiveStop(Double excessiveStop) {
        this.excessiveStop = excessiveStop;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pmid=").append(pmid);
        sb.append(", date=").append(date);
        sb.append(", trantype=").append(trantype);
        sb.append(", excessiveStop=").append(excessiveStop);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}