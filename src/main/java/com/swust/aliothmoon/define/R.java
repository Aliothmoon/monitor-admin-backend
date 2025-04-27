package com.swust.aliothmoon.define;

import lombok.Data;
import lombok.experimental.Accessors;

import static com.swust.aliothmoon.define.Constants.OK;

@Data
@Accessors(chain = true,fluent = true)
public class R<T> {

    private T data;
    private int code;
    private String msg;

    public R() {
    }

    public R(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> R<T> ok() {
        return new R<>(null, OK, null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data, OK, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return new R<>(data, OK, msg);
    }

    public static <T> R<T> failed() {
        return new R<>(null, 1, null);
    }

    public static <T> R<T> failed(String msg) {
        return new R<T>(null, 1, msg);
    }

    public static <T> R<T> failed(T data) {
        return new R<>(data, 1, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return new R<>(data, 1, msg);
    }

}