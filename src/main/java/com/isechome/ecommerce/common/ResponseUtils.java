package com.isechome.ecommerce.common;
import com.isechome.ecommerce.security.LoginResponse;

public class ResponseUtils {
    public static LoginResponse SUCCESS() {
        return new LoginResponse(200, "SUCCESS", null);
    }

    public static LoginResponse SUCCESS(Object bean) {
        return new LoginResponse(200, "SUCCESS", bean);
    }

    public static LoginResponse SUCCESS(Object bean, long total) {
        return new LoginResponse(total, bean);
    }

    public static LoginResponse FAIL() {
        return new LoginResponse(400, "FAIL", null);
    }

    public static LoginResponse FAIL(String msg) {
        return new LoginResponse(400, msg, null);
    }

    public static LoginResponse build(Integer status, String msg) {
        return new LoginResponse(status, msg, null);
    }

    public static LoginResponse build(Object bean) {
        return new LoginResponse(200, "SUCCESS", bean);
    }

    public static LoginResponse build(Integer status, String msg, Object bean) {
        return new LoginResponse(status, msg, bean);
    }

    /**
     * 身份失效
     *
     * @return
     */
    public static LoginResponse invalid() {
        return new LoginResponse(801, "登录信息失效，请重新登录", null);
    }
}
