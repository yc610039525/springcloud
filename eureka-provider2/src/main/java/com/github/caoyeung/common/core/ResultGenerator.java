package com.github.caoyeung.common.core;

import com.github.caoyeung.common.core.enums.HttpStatus;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(HttpStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(HttpStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(HttpStatus.FAIL)
                .setMessage(message);
    }
}
