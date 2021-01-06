package com.api.object.utils;

import cn.hutool.json.JSONUtil;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author jingLv
 * @date 2021/01/06
 */
public class JsonPathUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonPathUtils.class);

    /**
     * 文件地址
     */
    private final String path;
    /**
     * jsonpath对应修改的内容
     */
    private final Map<String, Object> content;

    public JsonPathUtils(String path, Map<String, Object> content) {
        this.path = path;
        this.content = content;
    }

    /**
     * 解析json，使用jsonpath语法获取值进行替换
     *
     * @return 返回已替换的json
     */
    public String modifyResult() {
        logger.info("读取JSON文件:{}，替换的内容:{}", this.path, JSONUtil.parse(content));
        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream(this.path));
        for (Map.Entry<String, Object> entry : this.content.entrySet()) {
            documentContext.set(entry.getKey(), entry.getValue());
        }
        return documentContext.jsonString();
    }
}
