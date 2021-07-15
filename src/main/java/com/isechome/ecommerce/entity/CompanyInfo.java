package com.isechome.ecommerce.entity;

import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

public class CompanyInfo implements Serializable {
    private Integer id;

    private String comName;

    private String comNameShort;

    private String comDesc;

    private String addressState;

    private String addressCity;

    private String address;

    private String enclosureUrl;

    private Integer linkUserId;

    private Date createDate;

    private Integer createUserId;

    private Byte whereFrom;

    private String externalUniqueness;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComNameShort() {
        return comNameShort;
    }

    public void setComNameShort(String comNameShort) {
        this.comNameShort = comNameShort == null ? null : comNameShort.trim();
    }

    public String getComDesc() {
        return comDesc;
    }

    public void setComDesc(String comDesc) {
        this.comDesc = comDesc == null ? null : comDesc.trim();
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState == null ? null : addressState.trim();
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity == null ? null : addressCity.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEnclosureUrl() {
        return enclosureUrl;
    }

    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl == null ? null : enclosureUrl.trim();
    }

    public Integer getLinkUserId() {
        return linkUserId;
    }

    public void setLinkUserId(Integer linkUserId) {
        this.linkUserId = linkUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Byte getWhereFrom() {
        return whereFrom;
    }

    public void setWhereFrom(Byte whereFrom) {
        this.whereFrom = whereFrom;
    }

    public String getExternalUniqueness() {
        return externalUniqueness;
    }

    public void setExternalUniqueness(String externalUniqueness) {
        this.externalUniqueness = externalUniqueness == null ? null : externalUniqueness.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", comName=").append(comName);
        sb.append(", comNameShort=").append(comNameShort);
        sb.append(", comDesc=").append(comDesc);
        sb.append(", addressState=").append(addressState);
        sb.append(", addressCity=").append(addressCity);
        sb.append(", address=").append(address);
        sb.append(", enclosureUrl=").append(enclosureUrl);
        sb.append(", linkUserId=").append(linkUserId);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", whereFrom=").append(whereFrom);
        sb.append(", externalUniqueness=").append(externalUniqueness);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}