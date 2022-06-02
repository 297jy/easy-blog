package com.zhuanyi.hexo.base.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Slf4j
public class JsonUtils {

    public static JSONObject readJsonObjectFromFile(String path) {
        List<String> lines = FileUtils.readAllLinesFromFile(path);
        if (CollectionUtils.isEmpty(lines)) {
            return new JSONObject();
        }
        String configJsonStr = StringUtils.join(lines, "");
        return JSONObject.parseObject(configJsonStr);
    }

    public static boolean writeJsonObjectToFile(String path, JSONObject data) {
        JSONObject configJson = readJsonObjectFromFile(path);
        if (configJson == null) {
            configJson = new JSONObject();
        }
        configJson.putAll(data);
        return FileUtils.writeContentToFile(path, configJson.toJSONString());
    }
}
