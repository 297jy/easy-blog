package com.zhuanyi.hexo.base.utils;

import com.zhuanyi.hexo.common.dto.Result;

public class ResultUtils {

    public static Result success(Object data) {
        Result result = new Result();
        return result.success(data);
    }

    public static Result success() {
        Result result = new Result();
        return result.success();
    }

    public static Result error(int code, String message) {
        Result result = new Result();
        return result.fail(message, code);
    }

    public static Result error() {
        Result result = new Result();
        return result.fail();
    }
}
