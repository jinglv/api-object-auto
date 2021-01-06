package com.api.object.utils;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingLv
 * @date 2021/01/06
 */
class MustacheUtilsTest {

    @Test
    void execute01() {
        Map<String, Object> map = new HashMap<>();
        map.put("author", "mustacheAuthor");
        map.put("price", 56.8f);
        String s = new MustacheUtils("mustache/book.mustache", map).execute();
        System.out.println(JSONUtil.parse(s));
    }
}