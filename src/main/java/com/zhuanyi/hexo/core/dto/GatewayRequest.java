package com.zhuanyi.hexo.core.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class GatewayRequest {

    private JSONObject resource;

    private Map<String, Object> params;
}
