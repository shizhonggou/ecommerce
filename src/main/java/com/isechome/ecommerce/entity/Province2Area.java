package com.isechome.ecommerce.entity;

import java.io.Serializable;

public class Province2Area implements Serializable {
    private Integer id;

    private Integer proviceid;

    private String provicename;

    private String area;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProviceid() {
        return proviceid;
    }

    public void setProviceid(Integer proviceid) {
        this.proviceid = proviceid;
    }

    public String getProvicename() {
        return provicename;
    }

    public void setProvicename(String provicename) {
        this.provicename = provicename == null ? null : provicename.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", proviceid=").append(proviceid);
        sb.append(", provicename=").append(provicename);
        sb.append(", area=").append(area);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}