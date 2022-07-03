package com.zhuanyi.hexo.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResourceRequest {
    // 资源名称，后面会映射成表名
    private String name;
    // 每页展示数量
    private int pageSize = 20;
    // 页号
    private int pageNo = 1;
    // 排序规则
    private String orderRule;
    // 主键key
    private String primeKey;
    // 是否开启模糊查询
    private String openLike;
    // 需要查询的列，为空则查询全部的列
    private List<String> selectCols = new ArrayList<>();
    // 查找、更新、插入的键值对
    private Map<String, Object> colValue = new HashMap<>();
    // 额外业务数据
    private Map<String, Object> extra = new HashMap<>();
}
