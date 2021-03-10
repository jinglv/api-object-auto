package com.api.test.model;

import lombok.Data;

import java.util.HashMap;

/**
 * 接口请求参数实体
 *
 * @author jingLv
 * @date 2021/01/11
 */
@Data
public class RestfulModel {
    /**
     * 请求接口url
     */
    public String url;
    /**
     * 请求接口方法
     */
    public String method;
    /**
     * 请求接口头信息
     */
    public HashMap<String, Object> header = new HashMap<>();
    /**
     * 请求接口参数：key-value
     */
    public HashMap<String, Object> query = new HashMap<>();
    /**
     * 请求接口参数：path中拼接参数
     */
    public HashMap<String, Object> pathQuery = new HashMap<>();
    /**
     * 请求接口参数：主体
     */
    public String body;
}
