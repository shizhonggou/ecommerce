package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScShelfResource implements Serializable {
    private Integer id;

    private Integer wId;

    private Integer vid;

    private String varietyName;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    private String material;

    private String specification;

    private Double hd;

    private Double kd;

    private Double cd;

    private Double xd;

    private String factory;

    private String warehouse;

    private Double price;

    private Double num;

    private Double soldNum;

    private Date date;

    private Integer status;

    private Integer type;

    private Integer systemType;

    private Integer pmid;

    private Date createTime;

    // 超量单
    private List<ScOrderListDetail> scOrderListDetail;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getwId() {
        return wId;
    }

    public void setwId(Integer wId) {
        this.wId = wId;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName == null ? null : varietyName.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public Double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public Double getKd() {
        return kd;
    }

    public void setKd(Double kd) {
        this.kd = kd;
    }

    public Double getCd() {
        return cd;
    }

    public void setCd(Double cd) {
        this.cd = cd;
    }

    public Double getXd() {
        return xd;
    }

    public void setXd(Double xd) {
        this.xd = xd;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse == null ? null : warehouse.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Double soldNum) {
        this.soldNum = soldNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ScOrderListDetail> getScOrderListDetail() {
        return scOrderListDetail;
    }

    public void setScOrderListDetail(List<ScOrderListDetail> scOrderListDetail) {
        this.scOrderListDetail = scOrderListDetail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", wId=").append(wId);
        sb.append(", varietyName=").append(varietyName);
        sb.append(", material=").append(material);
        sb.append(", specification=").append(specification);
        sb.append(", hd=").append(hd);
        sb.append(", kd=").append(kd);
        sb.append(", cd=").append(cd);
        sb.append(", xd=").append(xd);
        sb.append(", factory=").append(factory);
        sb.append(", warehouse=").append(warehouse);
        sb.append(", price=").append(price);
        sb.append(", num=").append(num);
        sb.append(", soldNum=").append(soldNum);
        sb.append(", date=").append(date);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", systemType=").append(systemType);
        sb.append(", pmid=").append(pmid);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}