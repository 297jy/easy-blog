package com.zhuanyi.hexo.base.enums;

public enum CompareOperatorEnum {
    IN("in"),
    LT("<"),
    GT(">"),
    EQ("="),
    LTE("<="),
    GTE(">=");

    private String code;

    CompareOperatorEnum(String code) {
        this.code = code;
    }
}
