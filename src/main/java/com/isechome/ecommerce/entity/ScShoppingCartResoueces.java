package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class ScShoppingCartResoueces implements Serializable{
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

    private String variety_name;

    private Integer v_id;

    private String material;

    private String specification;

    private String factory;

    private String warehouse;

    private Double d_weight;

    private Double z_weight;

    private Double sold_num;

    private Double num;

    public Double getZ_weight() {
        return z_weight;
    }
    public void setZ_weight(Double z_weight) {
        this.z_weight = z_weight;
    }
    public Double getD_weight() {
        return d_weight;
    }
    public void setD_weight(Double d_weight) {
        this.d_weight = d_weight;
    }
    public String getVariety_name() {
        return variety_name;
    }
    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }
    public Integer getV_id() {
        return v_id;
    }
    public void setV_id(Integer v_id) {
        this.v_id = v_id;
    }
    public String getSpecification() {
        return specification;
    }
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    public String getFactory() {
        return factory;
    }
    public void setFactory(String factory) {
        this.factory = factory;
    }
    public String getWarehouse() {
        return warehouse;
    }
    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Double getSold_num() {
        return sold_num;
    }
    public void setSold_num(Double sold_num) {
        this.sold_num = sold_num;
    }
    public Double getNum() {
        return num;
    }
    public void setNum(Double num) {
        this.num = num;
    }
   
    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
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
        return "ScShoppingCartResoueces [bargain=" + bargain + ", create_date=" + create_date + ", factory=" + factory
                + ", id=" + id + ", is_split_lading=" + is_split_lading + ", material=" + material + ", num=" + num
                + ", pmid=" + pmid + ", price=" + price + ", purchase_mid=" + purchase_mid + ", resources_id="
                + resources_id + ", sold_num=" + sold_num + ", specification=" + specification + ", status=" + status
                + ", ticket=" + ticket + ", update_time=" + update_time + ", userid=" + userid + ", v_id=" + v_id
                + ", variety_name=" + variety_name + ", warehouse=" + warehouse + ", weight=" + weight + ", weight2="
                + weight2 + "]";
    }
}
