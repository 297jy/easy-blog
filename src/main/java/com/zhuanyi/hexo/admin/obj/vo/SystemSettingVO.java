package com.zhuanyi.hexo.admin.obj.vo;

import lombok.Data;

@Data
public class SystemSettingVO {

    private String username;

    private String password;

    private String tokenPath;

    private String authSecret;

    private Long tokenExpireSeconds;

    private Long autoSaveArticleTimeIntervalSeconds;

    private String avatar;

    private String introduction;

    private String hexoPath;

    private String hexoSourcePath;

    private String hexoRemoveSourcePath;

    private String hexoTmpSourcePath;

}
