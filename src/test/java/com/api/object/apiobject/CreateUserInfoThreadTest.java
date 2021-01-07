package com.api.object.apiobject;

import cn.hutool.json.JSONUtil;
import com.api.object.helper.TokenHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 相同的数据，接口进行并发测试
 *
 * @author jingLv
 * @date 2021/01/05
 */
class CreateUserInfoThreadTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserInfoThreadTest.class);

    private static String token;
    private static final String userInfoBody = "{\n" +
            "    \"userId\":1,\n" +
            "    \"userName\":\"小红\",\n" +
            "    \"email\":\"xiaohong@qq.com\",\n" +
            "    \"phone\":\"18623456543\",\n" +
            "    \"friends\":[\n" +
            "        {\n" +
            "            \"userId\":11,\n" +
            "            \"userName\":\"小红喵\",\n" +
            "            \"email\":\"xiaohongmiao@qq.com\",\n" +
            "            \"phone\":\"18623456555\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"userId\":12,\n" +
            "            \"userName\":\"小红旺\",\n" +
            "            \"email\":\"xiaohongwang@qq.com\",\n" +
            "            \"phone\":\"18623456566\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:8886/v1";
        token = TokenHelper.getToken();
        logger.info("获取接口token：{}", token);
    }

    /**
     * 创建用户信息
     */
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    @DisplayName("创建用户信息")
    void creatUserInfo() {
        logger.info("创建用户信息请求body：{}", JSONUtil.parse(userInfoBody));
        Response createUserInfoResponse = UserInfoApiObject.creatUserInfo(userInfoBody, token);
        logger.info("创建用户信息接口返回信息：{}", createUserInfoResponse.getBody().prettyPrint());
        logger.info("创建用户信息测试结果断言");
        // 使⽤软断⾔，即使⼀个断⾔失败，仍会进⾏进⾏余下的断⾔，然后统⼀输出所有断⾔结果
        assertAll("创建用户信息测试结果断言",
                () -> assertEquals(createUserInfoResponse.path("code"), "00000"),
                () -> assertEquals(createUserInfoResponse.path("message"), "成功")

        );
    }
}
