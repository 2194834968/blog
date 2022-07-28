package com.lzp.MiniBlog.common.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<T>();
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    //操作成功
    public static<T> Result<T> ok(){
        return Result.ok(ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> ok(ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(resultCodeEnum);
        return result;
    }


    //操作失败
    public static<T> Result<T> fail(){
        return Result.fail(ResultCodeEnum.FAIL);
    }

    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(resultCodeEnum);
        return result;
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
