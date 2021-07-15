package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class ResourceSales implements Serializable {
    private Integer id;

    private String varietyname;

    private Byte vid;

    private String material;

    private String spec;

    private String originCode;

    private String cangku;

    private Double piece;

    private Double writePiece;

    private Double soldNum;

    private Double num;

    private Double writeNum;

    private Double price;

    private Date salesStartTime;

    private Date salesEndTime;

    private Byte status;

    private Byte whereFrom;

    private Integer salesMid;

    private Date creatTime;

    private Integer createUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVarietyname() {
        return varietyname;
    }

    public void setVarietyname(String varietyname) {
        this.varietyname = varietyname == null ? null : varietyname.trim();
    }

    public Byte getVid() {
        return vid;
    }

    public void setVid(Byte vid) {
        this.vid = vid;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode == null ? null : originCode.trim();
    }

    public String getCangku() {
        return cangku;
    }

    public void setCangku(String cangku) {
        this.cangku = cangku == null ? null : cangku.trim();
    }

    public Double getPiece() {
        return piece;
    }

    public void setPiece(Double piece) {
        this.piece = piece;
    }

    public Double getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Double soldNum) {
        this.soldNum = soldNum;
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

    public Date getSalesStartTime() {
        return salesStartTime;
    }

    public void setSalesStartTime(Date salesStartTime) {
        this.salesStartTime = salesStartTime;
    }

    public Date getSalesEndTime() {
        return salesEndTime;
    }

    public void setSalesEndTime(Date salesEndTime) {
        this.salesEndTime = salesEndTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getWhereFrom() {
        return whereFrom;
    }

    public void setWhereFrom(Byte whereFrom) {
        this.whereFrom = whereFrom;
    }

    public Integer getSalesMid() {
        return salesMid;
    }

    public void setSalesMid(Integer salesMid) {
        this.salesMid = salesMid;
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

    public Double getWritePiece() {
        return writePiece;
    }

    public void setWritePiece(Double writePiece) {
        this.writePiece = writePiece;
    }

    public Double getWriteNum() {
        return writeNum;
    }

    public void setWriteNum(Double writeNum) {
        this.writeNum = writeNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", varietyname=").append(varietyname);
        sb.append(", vid=").append(vid);
        sb.append(", material=").append(material);
        sb.append(", spec=").append(spec);
        sb.append(", originCode=").append(originCode);
        sb.append(", cangku=").append(cangku);
        sb.append(", piece=").append(piece);
        sb.append(", soldNum=").append(soldNum);
        sb.append(", num=").append(num);
        sb.append(", price=").append(price);
        sb.append(", salesStartTime=").append(salesStartTime);
        sb.append(", salesEndTime=").append(salesEndTime);
        sb.append(", status=").append(status);
        sb.append(", whereFrom=").append(whereFrom);
        sb.append(", salesMid=").append(salesMid);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}