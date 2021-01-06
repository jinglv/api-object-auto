package com.api.object.task;

import com.api.object.apiobject.UserInfoApiObject;
import io.restassured.response.Response;

import java.util.ArrayList;

/**
 * 环境管理任务
 *
 * @author jingLv
 * @date 2021/01/05
 */
public class EvnTask {

    private EvnTask() {
    }

    /**
     * 测试环境运行清理
     *
     * @param token 接口token
     */
    public static void evnClear(String token) {
        // 查询所有的用户信息
        Response findAllUserInfoResponse = UserInfoApiObject.findAllUserInfo(token);
        ArrayList<Integer> userIdList = findAllUserInfoResponse.path("data.userId");
        for (Integer userId : userIdList) {
            // 循环遍历根据userId删除用户信息
            UserInfoApiObject.deleteUserInfoByUserId(userId, token);
        }
    }
}
