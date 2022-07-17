package com.zhuanyi.hexo.admin.obj.form;

import lombok.Data;

@Data
public class SystemSettingForm {

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
