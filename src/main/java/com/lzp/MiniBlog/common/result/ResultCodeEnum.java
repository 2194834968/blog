package com.lzp.MiniBlog.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(400, "失败"),

    //登陆系统
    USERNAME_EXIST(901,"该用户名已存在,用户名不允许重复"),
    LOGIN_ERROR(902,"用户名或密码错误"),

    NEED_USERNAME_OR_PASS(903,"未输入用户名或密码"),

    //用户系统
    QUERY_USER_ERROR(904,"获取用户信息失败,请刷新或重新进入页面"),
    TOKEN_UN_EXIST(905,"您尚未登陆,请登陆"),
    TOKEN_OUTTIME_OR_UN_EXIST(906,"您的登陆已过期或token无效"),

    ;

    private Integer code;
    private String message;

    public String getMsg() {
        return message;
    }

    public int getStatusCode() {
        return code;
    }

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
