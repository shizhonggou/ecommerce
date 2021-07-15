package com.isechome.ecommerce.entity;

public class LoginMessage {
    private String status;
    private String compabb;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompabb() {
        return compabb;
    }

    public void setCompabb(String compabb) {
        this.compabb = compabb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", status=").append(status);
        sb.append(", compabb=").append(compabb);
        sb.append("]");
        return sb.toString();
    }

}