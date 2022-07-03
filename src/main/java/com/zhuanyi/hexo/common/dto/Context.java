package com.zhuanyi.hexo.common.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Context {

    private String model;

    private List<String> resources;

    private String action;

    private Map<String, Object> params;

    private Map<String, Object> cache;

    private Result<Map<String, Object>> result;

}
