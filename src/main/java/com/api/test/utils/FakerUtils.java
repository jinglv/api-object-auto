package com.api.test.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

import static com.api.test.utils.RunPythonUtils.runPython;

/**
 * 测试数据生成数据类
 *
 * @author jingLv
 * @date 2020/07/27
 */
public class FakerUtils {

    private FakerUtils() {
    }

    /**
     * 获取当前时间戳
     *
     * @return 返回当前时间戳
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 生成唯一id
     *
     * @return 返回唯一id
     */
    public static String uniqueId() {
        return IdUtil.objectId();
    }

    /**
     * 随机获取指定长度的数字
     *
     * @param length 指定长度
     * @return 返回随机数
     */
    public static Integer getRandomInt(int length) {
        length = length - 1;
        return (int) ((Math.random() * 9 + 1) * Math.pow(10, (double) length));
    }

    /**
     * 随机获取指定长度的数字
     *
     * @param length 指定长度
     * @return 返回随机数
     */
    public static String getRandomStringNum(int length) {
        length = length - 1;
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, (double) length)));
    }

    /**
     * 根据指定的字符长度随机生成数据
     *
     * @param length 指定的字符长度
     * @return 返回随机生成的字符串
     */
    public static String randomNumber(Integer length) {
        StringBuilder val = new StringBuilder();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = RandomUtil.randomInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = RandomUtil.randomInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (RandomUtil.randomInt(26) + temp));
            } else {
                val.append(RandomUtil.randomInt(10));
            }
        }
        return val.toString();
    }

    /**
     * 随机生成银行卡号信息
     *
     * @return 返回银行卡号
     */
    public static Map<String, String> randomBankNo() {
        Map<String, String> bankInfo = new HashMap<>(16);
        String bankName = "招商银行";
        String bankNo = (String) runPython("src/main/resources/python/bank_random.py");
        bankInfo.put(bankName, bankNo);
        return bankInfo;
    }

    /**
     * 随机生成姓名、身份证号、邮箱、电话号码等信息
     *
     * @return 返回信息Map
     */
    public static Map<String, Object> randomFourInfo() {
        Map<String, Object> fourInfo = new HashMap<>(16);
        String info = (String) runPython("src/main/resources/python/four_info.py");
        Map<String, Object> maps = JSONUtil.parseObj(info);
        maps.forEach((k, v) -> fourInfo.put(String.valueOf(k), String.valueOf(v)));
        return fourInfo;
    }

}
