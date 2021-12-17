/*
 * @Author: lina
 * @Date: 2021-08-24 15:51:01
 * @LastEditTime: 2021-08-24 15:53:26
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\entity\ReturnObj.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.entity;

import java.io.Serializable;

public class ReturnObj implements Serializable {

    private String success;

    
    private String message;
    
    private static final long serialVersionUID = 1L;
    
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", success=").append(success);
        sb.append(", message=").append(message);
        sb.append("]");
        return sb.toString();
    }
}