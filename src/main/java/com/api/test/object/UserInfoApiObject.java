package com.api.test.object;

import com.api.test.utils.RequestUtils;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息接口对象
 *
 * @author jingLv
 * @date 2021/01/05
 */
public class UserInfoApiObject {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoApiObject.class);
    private static final Map<String, Object> HEADER_MAP = new HashMap<>();

    /**
     * 创建用户信息
     *
     * @param userInfoBody 用户信息请求主体
     * @param token        接口token
     * @return response
     */
    public static Response createUserInfo(String userInfoBody, String token) {
        HEADER_MAP.put("token", token);
        return RequestUtils.run("post", "/info/user", "application/json", HEADER_MAP, userInfoBody);
    }

    /**
     * 查询所有用户信息
     *
     * @param token 接口token
     * @return response
     */
    public static Response findAllUserInfo(String token) {
        HEADER_MAP.put("token", token);
        return RequestUtils.run("get", "/info/user", "application/json", HEADER_MAP, null);
    }

    /**
     * 通过userId查询用户信息
     *
     * @param userId 用户id
     * @param token  接口token
     * @return response
     */
    public static Response findUserInfoByUserId(Integer userId, String token) {
        HEADER_MAP.put("token", token);
        return RequestUtils.run("get", "/info/user/" + userId, "application/json", HEADER_MAP, null);
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
        HEADER_MAP.put("token", token);
        return RequestUtils.run("put", "/info/user/" + userId, "application/json", HEADER_MAP, updateUserInfoBody);
    }

    /**
     * 通过userId删除用户信息
     *
     * @param userId 用户id
     * @param token  接口token
     * @return response
     */
    public static Response deleteUserInfoByUserId(Integer userId, String token) {
        HEADER_MAP.put("token", token);
        return RequestUtils.run("delete", "/info/user/" + userId, "application/json", HEADER_MAP, null);
    }
}
