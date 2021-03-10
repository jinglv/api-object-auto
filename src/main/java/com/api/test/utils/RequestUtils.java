package com.api.test.utils;

import cn.hutool.json.JSONUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Rest-Assured请求工具类封装
 *
 * @author jinglv
 * @date 2021/03/10
 */
public class RequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    private RequestUtils() {
    }

    /**
     * 接口请求方法
     *
     * @param method      请求方法
     * @param path        请求路径
     * @param contentType 请求文本类型
     * @param headers     请求头信息
     * @param body        请求主体
     * @return Response
     */
    public static Response run(String method, String path, String contentType, Map<String, Object> headers, String body) {
        logger.info("请求数据，请求方法:{},请求路径:{},请求文本类型:{},请求头信息:{},请求主体:{}", method, path, contentType, JSONUtil.parse(headers), JSONUtil.parse(body));
        RequestSpecification requestSpecification = given().log().all();
        if (contentType != null) {
            requestSpecification.contentType(contentType);
        }
        if (headers != null) {
            requestSpecification.headers(headers);
        }
        if (body != null) {
            requestSpecification.body(body);
        }
        // 将拼装好的接口进行请求
        return requestSpecification.request(method, path).then().log().all().extract().response();
    }

}
