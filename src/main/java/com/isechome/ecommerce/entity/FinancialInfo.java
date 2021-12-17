package com.isechome.ecommerce.entity;

import java.util.Date;

public class FinancialInfo {
    private Integer id;
    private Integer bill;
    private Integer bill_type;
    private String accept_bank;
    private Integer accept_date;
    private Double money;
    private Integer company_id;
    private String company_name;
    private Integer user_id;
    private String input_user;
    private Date input_date;
    private String remark;
    private Integer pmid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Integer getBill_type() {
        return bill_type;
    }

    public void setBill_type(Integer bill_type) {
        this.bill_type = bill_type;
    }

    public String getAccept_bank() {
        return accept_bank;
    }

    public void setAccept_bank(String accept_bank) {
        this.accept_bank = accept_bank;
    }

    public Integer getAccept_date() {
        return accept_date;
    }

    public void setAccept_date(Integer accept_date) {
        this.accept_date = accept_date;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getInput_user() {
        return input_user;
    }

    public void setInput_user(String input_user) {
        this.input_user = input_user;
    }

    public Date getInput_date() {
        return input_date;
    }

    public void setInput_date(Date input_date) {
        this.input_date = input_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

}
