package com.api.object.helper;

import com.api.object.apiobject.UserLoginApiObject;

/**
 * 封装获取token帮助类
 *
 * @author jingLv
 * @date 2021/01/05
 */
public class TokenHelper {

    private TokenHelper() {
    }

    /**
     * 获取token
     *
     * @return string
     */
    public static String getToken() {
        // 一般登录认证的账号和密码是固定的，可以在代码中写死或者写到配置文件中
        String loginBody = "{\n" +
                "    \"username\":\"xiaohong\",\n" +
                "    \"password\":\"123123\"\n" +
                "}";
        return UserLoginApiObject.userLogin(loginBody).path("data.token");
    }
}
