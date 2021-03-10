package com.api.test.object;

import cn.hutool.json.JSONUtil;
import com.api.test.helper.TokenHelper;
import com.api.test.model.RestfulModel;
import com.api.test.utils.PropertiesUtils;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 相同的数据，接口进行并发测试
 *
 * @author jingLv
 * @date 2021/01/05
 */
@Epic("创建用户信息接口的并发测试")
@Feature("使用Junit5进行对创建用户信息接口进行并行测试")
@Owner("小晶晶")
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
        String propFileName = "project.properties";
        Properties prop = PropertiesUtils.loadFromEnvProperties(propFileName);
        RestAssured.baseURI = prop.getProperty("test_url");
        token = TokenHelper.getToken();
        logger.info("获取接口token：{}", token);
    }

    /**
     * 创建用户信息
     */
    //@RepeatedTest(3)
    @Execution(CONCURRENT)
    @Story("创建用户信息信息接口")
    @Step("新建用户信息")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "create", type = "user")
    @Issues({@Issue("bug10234"), @Issue("bug10235")})
    @Description("创建用户信息")
    @DisplayName("创建用户信息")
    void creatUserInfo() {
        RestfulModel restfulModel = new RestfulModel();
        restfulModel.setBody(userInfoBody);
        logger.info("创建用户信息请求body：{}", JSONUtil.parse(userInfoBody));
        Response createUserInfoResponse = UserInfoApiObject.creatUserInfo(userInfoBody, token);
        getRequestAndRespondBody(restfulModel, createUserInfoResponse);
        logger.info("创建用户信息接口返回信息：{}", createUserInfoResponse.getBody().prettyPrint());
        logger.info("创建用户信息测试结果断言");
        // 使⽤软断⾔，即使⼀个断⾔失败，仍会进⾏进⾏余下的断⾔，然后统⼀输出所有断⾔结果
        assertAll("创建用户信息测试结果断言",
                () -> assertEquals(createUserInfoResponse.path("code"), "00000"),
                () -> assertEquals(createUserInfoResponse.path("message"), "成功")

        );
    }


    @Attachment("请求信息")
    public static String requestBody(RestfulModel restfulModel) {
        //报告展现请求信息
        return restfulModel.toString();
    }

    @Attachment("响应结果")
    public static String responseBody(Response response) {
        return JSONUtil.toJsonPrettyStr(response.asString());
    }

    public static void getRequestAndRespondBody(RestfulModel restfulModel, Response response) {
        requestBody(restfulModel);
        responseBody(response);
    }
}
