package com.api.object.apiobject;

import cn.hutool.json.JSONUtil;
import com.api.object.apimodel.UserInfoModel;
import com.api.object.helper.TokenHelper;
import com.api.object.task.EvnTask;
import com.api.object.utils.FakerUtils;
import com.api.object.utils.MustacheUtils;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 用户信息接口测试用例
 *
 * @author jingLv
 * @date 2021/01/05
 */
@Epic("用户信息接口测试")
@Feature("使用Junit5进行对进用户信息接口行流程测试")
@Owner("小晶晶")
class UserInfoApiObjectTest {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoApiObjectTest.class);

    private static String token;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://8.140.112.109:8988/v1";
        token = TokenHelper.getToken();
        logger.info("获取接口token：{}", token);
    }

    /**
     * 保证case的独立性，每个case前后都进行清理
     */
    @BeforeEach
    @AfterEach
    void evnClear() {
        EvnTask.evnClear(token);
    }

    /**
     * 创建用户信息
     */
    @Test
    @Step("新建用户信息")
    @DisplayName("创建用户信息")
    @Story("创建用户信息")
    @Description("创建用户信息")
    void creatUserInfo() {
        logger.info("创建用户信息请求body：{}", JSONUtil.parse(userInfoData()));
        Response createUserInfoResponse = UserInfoApiObject.creatUserInfo(userInfoData(), token);
        logger.info("创建用户信息接口返回信息：{}", createUserInfoResponse.getBody().prettyPrint());
        logger.info("创建用户信息测试结果断言");
        // 使⽤软断⾔，即使⼀个断⾔失败，仍会进⾏进⾏余下的断⾔，然后统⼀输出所有断⾔结果
        assertAll("创建用户信息测试结果断言",
                () -> assertEquals(createUserInfoResponse.path("code"), "00000"),
                () -> assertEquals(createUserInfoResponse.path("message"), "成功")

        );
    }

    /**
     * 查询所有用户信息
     */
    @Test
    @Step("查询所有用户信息")
    @DisplayName("查询所有用户信息")
    @Story("查询所有用户信息")
    @Description("查询所有用户信息")
    void findAllUserInfo() {
        // 创建用户
        UserInfoApiObject.creatUserInfo(userInfoData(), token);
        logger.info("查询所有用户信息");
        // 查询所有用户信息
        Response findAllUserInfoResponse = UserInfoApiObject.findAllUserInfo(token);
        logger.info("查询所有用户信息接口返回信息：{}", findAllUserInfoResponse.getBody().prettyPrint());
        assertAll("查询所有用户信息测试结果断言",
                () -> assertEquals(findAllUserInfoResponse.path("code"), "00000"),
                () -> assertEquals(findAllUserInfoResponse.path("message"), "成功")
        );
    }

    @Test
    @Step("通过UserId查询用户信息")
    @DisplayName("通过UserId查询用户信息")
    @Story("通过UserId查询用户信息")
    @Description("通过UserId查询用户信息")
    void findUserInfoByUserId() {
        // 创建用户
        UserInfoApiObject.creatUserInfo(userInfoData(), token);
        // 查询所有用户信息
        Response findAllUserInfoResponse = UserInfoApiObject.findAllUserInfo(token);
        logger.info("通过UserId查询用户信息");
        // 获取查询返回的userId
        Integer userId = findAllUserInfoResponse.path("data[0].userId");
        logger.info("获取到的UserId：{}", userId);
        Response findAllUserInfoForUserIdResponse = UserInfoApiObject.findUserInfoByUserId(userId, token);
        logger.info("通过UserId查询用户信息接口返回信息：{}", findAllUserInfoForUserIdResponse.getBody().prettyPrint());
        assertAll("通过UserId查询用户信测试结果断言",
                () -> assertEquals(findAllUserInfoForUserIdResponse.path("code"), "00000"),
                () -> assertEquals(findAllUserInfoForUserIdResponse.path("message"), "成功")
        );
    }

    @Test
    @Step("通过UserId更新用户信息")
    @DisplayName("通过UserId更新用户信息")
    @Story("通过UserId更新用户信息")
    @Description("通过UserId更新用户信息")
    void updateUserInfoByUserId() {
        // 创建用户
        UserInfoApiObject.creatUserInfo(userInfoData(), token);
        // 查询所有用户信息
        Response findAllUserInfoResponse = UserInfoApiObject.findAllUserInfo(token);
        // 获取查询返回的userId
        Integer userId = findAllUserInfoResponse.path("data[0].userId");
        logger.info("获取到的UserId：{}", userId);
        String updateUserInfoBody = "{\n" +
                "    \"userId\":" + userId + ",\n" +
                "    \"userName\":\"小红红\",\n" +
                "    \"email\":\"xiaohong红@qq.com\",\n" +
                "    \"phone\":\"18623456543\"\n" +
                "}";
        logger.info("更新用户信息请求body：{}", JSONUtil.parse(updateUserInfoBody));
        logger.info("通过UserId更新用户信息");
        // 更新用户信息
        Response updateUserInfoForUserIdResponse = UserInfoApiObject.updateUserInfoByUserId(userId, updateUserInfoBody, token);
        logger.info("通过UserId更新用户信息接口返回信息：{}", updateUserInfoForUserIdResponse.getBody().prettyPrint());
        assertAll("通过UserId更新用户信息",
                () -> assertEquals(updateUserInfoForUserIdResponse.path("code"), "00000"),
                () -> assertEquals(updateUserInfoForUserIdResponse.path("message"), "成功")
        );
    }

    @Test
    @Step("通过UserId删除用户信息")
    @DisplayName("通过UserId删除用户信息")
    @Story("通过UserId删除用户信息")
    @Description("通过UserId删除用户信息")
    void deleteUserInfoByUserId() {
        // 创建用户
        UserInfoApiObject.creatUserInfo(userInfoData(), token);
        // 查询所有用户信息
        Response findAllUserInfoResponse = UserInfoApiObject.findAllUserInfo(token);
        // 获取查询返回的userId
        Integer userId = findAllUserInfoResponse.path("data[0].userId");
        logger.info("获取到的UserId：{}", userId);
        logger.info("通过UserId删除用户信息");
        // 删除指定用户信息
        Response deleteUserInfoForUserIdResponse = UserInfoApiObject.deleteUserInfoByUserId(userId, token);
        logger.info("通过UserId删除用户信息：{}", deleteUserInfoForUserIdResponse.getBody().prettyPrint());
        assertAll("通过UserId删除用户信息测试结果断言",
                () -> assertEquals(deleteUserInfoForUserIdResponse.path("code"), "00000"),
                () -> assertEquals(deleteUserInfoForUserIdResponse.path("message"), "成功")
        );
    }

    public static String userInfoData() {
        UserInfoModel userInfoModel = new UserInfoModel(FakerUtils.getRandomInt(2),
                String.valueOf(FakerUtils.randomFourInfo().get("name")),
                String.valueOf(FakerUtils.randomFourInfo().get("email")),
                String.valueOf(FakerUtils.randomFourInfo().get("mobile")));
        return new MustacheUtils("mustache/userInfo.mustache", userInfoModel).execute();
    }
}