package com.api.test.object;

import com.api.test.utils.RequestUtils;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户登录接口对象
 *
 * @author jingLv
 * @date 2021/01/05
 */
public class UserLoginApiObject {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginApiObject.class);

    /**
     * 用户登录
     *
     * @param userLoginBody 用户登录请求主体
     * @return response
     */
    public static Response userLogin(String userLoginBody) {
        logger.info("用户登录ApiObject，接口请求主体：{}", userLoginBody);
        return RequestUtils.run("get", "/user/login", "application/json", null, userLoginBody);
    }
}
