package com.zhuanyi.hexo.common.dto;

import lombok.Getter;

/**
 * 返回结果实体类
 *
 * @author Li Jinhui
 * @update 2019/1/7
 * @since 2018/12/6
 */
@Getter
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result<T> setResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public Result<T> success() {
        return setResult(200, "Success", null);
    }

    public Result<T> success(T data) {
        return setResult(20000, "Success", data);
    }

    public Result<T> fail(T data, String message) {
        return setResult(400, message, data);
    }

    public Result<T> fail(String message) {
        return setResult(400, message, null);
    }

    public Result<T> fail() {
        return setResult(400, "", null);
    }

    public Result<T> fail(T data, String message, int code) {
        return setResult(code, message, data);
    }

    public Result<T> fail(String message, int code) {
        return setResult(code, message, null);
    }
}