package com.zhuanyi.hexo.admin.config;

import com.alibaba.fastjson.JSONObject;
import com.zhuanyi.hexo.base.constant.SystemConfigConstant;
import com.zhuanyi.hexo.base.utils.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Data
@Slf4j
public class SystemConfig {

    @Value("${systemConfigPath}")
    private String systemConfigPath = "";

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

    @PostConstruct
    private void init() {
        JSONObject configJsonObject = JsonUtils.readJsonObjectFromFile(systemConfigPath);
        username = configJsonObject.getString(SystemConfigConstant.USERNAME_KEY);
        password = configJsonObject.getString(SystemConfigConstant.PASSWORD_KEY);
        tokenPath = configJsonObject.getString(SystemConfigConstant.TOKEN_PATH_KEY);
        authSecret = configJsonObject.getString(SystemConfigConstant.AUTH_SECRET_KEY);
        tokenExpireSeconds = configJsonObject.getLong(SystemConfigConstant.TOKEN_EXPIRE_SECONDS_KEY);
        autoSaveArticleTimeIntervalSeconds = configJsonObject.getLong(SystemConfigConstant.AUTO_SAVE_ARTICLE_TIME_INTERVAL_SECONDS_KEY);
        avatar = configJsonObject.getString(SystemConfigConstant.AVATAR_KEY);
        introduction = configJsonObject.getString(SystemConfigConstant.INTRODUCTION_KEY);
        hexoSourcePath = configJsonObject.getString(SystemConfigConstant.HEXO_SOURCE_PATH_KEY);
        hexoRemoveSourcePath = configJsonObject.getString(SystemConfigConstant.HEXO_REMOVE_SOURCE_PATH_KEY);
        hexoTmpSourcePath = configJsonObject.getString(SystemConfigConstant.HEXO_TMP_SOURCE_PATH_KEY);
    }
}
