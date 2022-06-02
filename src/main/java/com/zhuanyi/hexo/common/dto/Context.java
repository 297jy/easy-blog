package com.zhuanyi.hexo.common.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Context {

    private String model;

    private String resource;

    private String action;

    private Map<String, Object> params;

    private Map<String, Object> cache;

    private Result<?> result;

}
