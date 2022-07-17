package com.zhuanyi.hexo.admin.obj.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SystemSettingDTO {

    @JSONField(name = "username")
    private String username;

    private String password;

    @JSONField(name = "token_path")
    private String tokenPath;

    @JSONField(name = "auth_secret")
    private String authSecret;

    @JSONField(name = "token_expire_seconds")
    private Long tokenExpireSeconds;

    @JSONField(name = "auto_save_article_time_interval_seconds")
    private Long autoSaveArticleTimeIntervalSeconds;

    private String avatar;

    private String introduction;

    private String hexoPath;

    @JSONField(name = "hexo_source_path")
    private String hexoSourcePath;

    @JSONField(name = "hexo_remove_source_path")
    private String hexoRemoveSourcePath;

    @JSONField(name = "hexo_tmp_source_path")
    private String hexoTmpSourcePath;
}
