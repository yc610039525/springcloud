package com.github.caoyeung.common.core;

import com.alibaba.fastjson.JSON;
import com.github.caoyeung.common.core.enums.HttpStatus;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result setCode(HttpStatus resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
