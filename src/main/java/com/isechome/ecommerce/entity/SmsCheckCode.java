package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class SmsCheckCode implements Serializable {
    private Integer id;

    private String mobile;

    private String check_code;

    private Date creat_time;

    private Date expire_date;

    private Byte is_valid;

    private Byte type;

    private String ip;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCheckcode() {
        return check_code;
    }

    public void setCheckcode(String check_code) {
        this.check_code = check_code == null ? null : check_code.trim();
    }

    public Date getCreattime() {
        return creat_time;
    }

    public void setCreattime(Date creat_time) {
        this.creat_time = creat_time;
    }

    public Date getExpiredate() {
        return expire_date;
    }

    public void setExpiredate(Date expire_date) {
        this.expire_date = expire_date;
    }

    public Byte getIsvalid() {
        return is_valid;
    }

    public void setIsvalid(Byte is_valid) {
        this.is_valid = is_valid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", check_code=").append(check_code);
        sb.append(", creat_time=").append(creat_time);
        sb.append(", expire_date=").append(expire_date);
        sb.append(", is_valid=").append(is_valid);
        sb.append(", type=").append(type);
        sb.append(", ip=").append(ip);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}