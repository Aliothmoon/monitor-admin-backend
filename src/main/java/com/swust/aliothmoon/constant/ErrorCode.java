package com.swust.aliothmoon.constant;

import com.swust.aliothmoon.define.R;

public enum ErrorCode {
    OK(0),
    UNEXPECTED(1),

    NO_AUTHORITY(1001),

    ERROR_PWD(2001),
    USER_NOT_EXIST(2002);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public <T> R<T> warp() {
        return new R<T>().code(code).msg(name());
    }

}
