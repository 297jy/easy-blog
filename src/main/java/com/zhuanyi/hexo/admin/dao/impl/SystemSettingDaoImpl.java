package com.zhuanyi.hexo.admin.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhuanyi.hexo.admin.config.SystemConfig;
import com.zhuanyi.hexo.admin.dao.SystemSettingDao;
import com.zhuanyi.hexo.admin.obj.dto.SystemSettingDTO;
import com.zhuanyi.hexo.base.constant.SystemConfigConstant;
import com.zhuanyi.hexo.base.utils.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("defaultSystemSettingDao")
public class SystemSettingDaoImpl implements SystemSettingDao {

    @Resource
    private SystemConfig systemConfig;

    @Override
    public SystemConfig getSystemSetting() {
        return systemConfig;
    }

    @Override
    public boolean updateSystemSetting(SystemSettingDTO systemSettingDTO) {
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(systemSettingDTO));
        if(JsonUtils.writeJsonObjectToFile(systemConfig.getSystemConfigPath(), data)){
            BeanUtils.copyProperties(systemSettingDTO, systemConfig);
            return true;
        }
        return false;
    }
}
