package com.api.object.apiobject;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

/**
 * 用户信息接口对象
 *
 * @author jingLv
 * @date 2021/01/05
 */
public class UserInfoApiObject {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoApiObject.class);

    /**
     * 创建用户信息
     *
     * @param userInfoBody 用户信息请求主体
     * @param token        接口token
     * @return response
     */
    public static Response creatUserInfo(String userInfoBody, String token) {
        logger.info("创建用户信息ApiObject，请求主体：{},请求携带token：{}", userInfoBody, token);
        return given()
                .log().all()
                .when()
                .contentType("application/json")
                .header("token", token)
                .body(userInfoBody)
                .post("/info/user")
                .then()
                .log().body()
                .extract()
                .response();
    }

    /**
     * 查询所有用户信息
     *
     * @param token 接口token
     * @return response
     */
    public static Response findAllUserInfo(String token) {
        logger.info("查询所有用户信息ApiObject，请求携带token：{}", token);
        return given()
                .when()
                .log().all()
                .contentType("application/json")
                .header("token", token)
                .get("/info/user")
                .then()
                .log().body()
                .extract()
                .response();
    }

    /**
     * 通过userId查询用户信息
     *
     * @param userId 用户id
     * @param token  接口token
     * @return response
     */
    public static Response findUserInfoByUserId(Integer userId, String token) {
        logger.info("通过userId查询用户信息ApiObject，请求UserId：{}，请求携带token：{}", userId, token);
        return given()
                .when()
                .log().all()
                .contentType("application/json")
                .header("token", token)
                .get("/info/user/" + userId)
                .then()
                .log().body()
                .extract()
                .response();
    }

    /**
     * 通过userId更新用户信息
     *
     * @param userId             用户id
     * @param updateUserInfoBody 更新用户信息
     * @param token              接口token
     * @return response
     */
    public static Response updateUserInfoByUserId(Integer userId, String updateUserInfoBody, String token) {
        logger.info("通过userId更新用户信息ApiObject，请求UserId：{}，请求更新用户信息主体：{}，请求携带token：{}", userId, updateUserInfoBody, token);
        return given()
                .when()
                .log().all()
                .contentType("application/json")
                .header("token", token)
                .body(updateUserInfoBody)
                .put("/info/user/" + userId)
                .then()
                .log().body()
                .extract()
                .response();
    }

    /**
     * 通过userId删除用户信息
     *
     * @param userId 用户id
     * @param token  接口token
     * @return response
     */
    public static Response deleteUserInfoByUserId(Integer userId, String token) {
        logger.info("通过userId删除用户信息ApiObject，请求UserId：{}，请求携带token：{}", userId, token);
        return given()
                .when()
                .log().all()
                .contentType("application/json")
                .header("token", token)
                .delete("/info/user/" + userId)
                .then()
                .log().body()
                .extract()
                .response();
    }
}
