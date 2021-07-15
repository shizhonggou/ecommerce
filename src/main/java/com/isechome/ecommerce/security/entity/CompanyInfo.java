package com.isechome.ecommerce.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompanyInfo implements Serializable {

    private Integer id; //会员id

    private String com_name; //公司名称

    private String com_name_short; //公司简称

    private String com_desc; //公司描述

    private String address_state; //省份

    private String address_city; //城市

    private String address; //详细地址

    private String enclosure_url; //附件地址

    private Integer link_user_id; //主联系人id

    private Date create_date; // 创建时间

    private Integer create_user_id; //创建人id

    private Short where_from; //来源

    private String external_uniqueness; //外部唯一码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getCom_name_short() {
        return com_name_short;
    }

    public void setCom_name_short(String com_name_short) {
        this.com_name_short = com_name_short;
    }

    public String getCom_desc() {
        return com_desc;
    }

    public void setCom_desc(String com_desc) {
        this.com_desc = com_desc;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnclosure_url() {
        return enclosure_url;
    }

    public void setEnclosure_url(String enclosure_url) {
        this.enclosure_url = enclosure_url;
    }

    public Integer getLink_user_id() {
        return link_user_id;
    }

    public void setLink_user_id(Integer link_user_id) {
        this.link_user_id = link_user_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Integer getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(Integer create_user_id) {
        this.create_user_id = create_user_id;
    }

    public Short getWhere_from() {
        return where_from;
    }

    public void setWhere_from(Short where_from) {
        this.where_from = where_from;
    }

    public String getExternal_uniqueness() {
        return external_uniqueness;
    }

    public void setExternal_uniqueness(String external_uniqueness) {
        this.external_uniqueness = external_uniqueness;
    }
}
