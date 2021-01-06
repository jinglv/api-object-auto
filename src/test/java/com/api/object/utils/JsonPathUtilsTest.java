package com.api.object.utils;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingLv
 * @date 2021/01/06
 */
class JsonPathUtilsTest {

    /**
     * $.store.book[*].author ——所有书的作者
     */
    @Test
    void modifyResult01() {
        Map<String, Object> content = new HashMap<>();
        content.put("$.store.book[*].author", "测试作者");
        String result = new JsonPathUtils("/json/book.json", content).modifyResult();
        System.out.println(JSONUtil.parseObj(result));
    }

    /**
     * $.store.*——store下所有的都进行修改，这里包括了bicycle和book。将store下的所有内容改为all change
     */
    @Test
    void modifyResult02() {
        Map<String, Object> content = new HashMap<>();
        content.put("$.store.*", "all change");
        String result = new JsonPathUtils("/json/book.json", content).modifyResult();
        System.out.println(JSONUtil.parseObj(result));
    }

    /**
     * $…book[0,1]——book下的第一个和第二个，将book下的第一个和第二个内容改为first two change
     */
    @Test
    void modifyResult03() {
        Map<String, Object> content = new HashMap<>();
        content.put("$..book[0,1]", "first two change");
        String result = new JsonPathUtils("/json/book.json", content).modifyResult();
        System.out.println(JSONUtil.parseObj(result));
    }
}