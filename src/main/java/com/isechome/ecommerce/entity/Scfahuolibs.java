package com.isechome.ecommerce.entity;

import java.io.Serializable;

public class Scfahuolibs implements Serializable {
    private Integer id;

    private Integer mid;

    private String cangkuid;

    private String cangkuname;

    private String yunfei;

    private String remark;

    private String daozhanCangkuName;

    private String daozhanma;

    private Integer pmid;

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

    public String getCangkuid() {
        return cangkuid;
    }

    public void setCangkuid(String cangkuid) {
        this.cangkuid = cangkuid;
    }

    public String getCangkuname() {
        return cangkuname;
    }

    public void setCangkuname(String cangkuname) {
        this.cangkuname = cangkuname == null ? null : cangkuname.trim();
    }

    public String getYunfei() {
        return yunfei;
    }

    public void setYunfei(String yunfei) {
        this.yunfei = yunfei == null ? null : yunfei.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDaozhanCangkuName() {
        return daozhanCangkuName;
    }

    public void setDaozhanCangkuName(String daozhanCangkuName) {
        this.daozhanCangkuName = daozhanCangkuName == null ? null : daozhanCangkuName.trim();
    }

    public String getDaozhanma() {
        return daozhanma;
    }

    public void setDaozhanma(String daozhanma) {
        this.daozhanma = daozhanma == null ? null : daozhanma.trim();
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", cangkuid=").append(cangkuid);
        sb.append(", cangkuname=").append(cangkuname);
        sb.append(", yunfei=").append(yunfei);
        sb.append(", remark=").append(remark);
        sb.append(", daozhanCangkuName=").append(daozhanCangkuName);
        sb.append(", daozhanma=").append(daozhanma);
        sb.append(", pmid=").append(pmid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}