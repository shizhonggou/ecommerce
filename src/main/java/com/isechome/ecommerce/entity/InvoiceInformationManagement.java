package com.isechome.ecommerce.entity;

import java.io.Serializable;

public class InvoiceInformationManagement implements Serializable {
    private Integer id;

    private Integer mid;

    private String rise;

    private String taxnum;

    private String addressMail;

    private String addressCompany;

    private String openBank;

    private String account;

    private String phone;

    private String isdefault;

    private String invoiceCode;
    
    private String remark;

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

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise == null ? null : rise.trim();
    }

    public String getTaxnum() {
        return taxnum;
    }

    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum == null ? null : taxnum.trim();
    }

    public String getAddressMail() {
        return addressMail;
    }

    public void setAddressMail(String addressMail) {
        this.addressMail = addressMail == null ? null : addressMail.trim();
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank == null ? null : openBank.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    

    public String getAddressCompany() {
        return addressCompany;
    }

    public void setAddressCompany(String addressCompany) {
        this.addressCompany = addressCompany;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", rise=").append(rise);
        sb.append(", taxnum=").append(taxnum);
        sb.append(", addressMail=").append(addressMail);
        sb.append(", openBank=").append(openBank);
        sb.append(", account=").append(account);
        sb.append(", phone=").append(phone);
        sb.append(", isdefault=").append(isdefault);
        sb.append(", invoiceCode=").append(invoiceCode);
        sb.append(", remark=").append(remark);
        sb.append(", pmid=").append(pmid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}