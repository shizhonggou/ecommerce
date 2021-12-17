package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class ScShoppingCart implements Serializable{
    private Integer id;

    private Integer userid;

    private Integer pmid;

    private Integer purchase_mid;

    private Date create_date;

    private Date update_time;

    private Integer status;

    private Integer is_split_lading;

    private Integer resources_id;

    private double weight;

    private double weight2;

    private double price;

    private double bargain;

    private Integer ticket;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public Integer getPmid() {
        return pmid;
    }
    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }
    public Integer getPurchase_mid() {
        return purchase_mid;
    }
    public void setPurchase_mid(Integer purchase_mid) {
        this.purchase_mid = purchase_mid;
    }
    public Date getCreate_date() {
        return create_date;
    }
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
    public Date getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getIs_split_lading() {
        return is_split_lading;
    }
    public void setIs_split_lading(Integer is_split_lading) {
        this.is_split_lading = is_split_lading;
    }
    public Integer getResources_id() {
        return resources_id;
    }
    public void setResources_id(Integer resources_id) {
        this.resources_id = resources_id;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getWeight2() {
        return weight2;
    }
    public void setWeight2(double weight2) {
        this.weight2 = weight2;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getBargain() {
        return bargain;
    }
    public void setBargain(double bargain) {
        this.bargain = bargain;
    }
    public Integer getTicket() {
        return ticket;
    }
    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }
    @Override
    public String toString() {
        return "ScShoppingCart [bargain=" + bargain + ", create_date=" + create_date + ", id=" + id
                + ", is_split_lading=" + is_split_lading + ", pmid=" + pmid + ", price=" + price + ", purchase_mid="
                + purchase_mid + ", resources_id=" + resources_id + ", status=" + status + ", ticket=" + ticket
                + ", update_time=" + update_time + ", userid=" + userid + ", weight=" + weight + ", weight2=" + weight2
                + "]";
    }
    
}
