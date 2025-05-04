package com.swust.aliothmoon.model.vo;

import lombok.Data;

/**
 * 通用返回结果类
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 数据
     */
    private T data;
    
    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage("操作成功");
        return result;
    }
    
    /**
     * 成功返回结果
     *
     * @param data 返回数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data 返回数据
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    /**
     * 失败返回结果
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(-1);
        result.setMessage("操作失败");
        return result;
    }
    
    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setCode(-1);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 失败返回结果
     *
     * @param code 错误码
     * @param message 提示信息
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
} 