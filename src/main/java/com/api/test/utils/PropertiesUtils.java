package com.api.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties配置文件读取
 *
 * @author jinglv
 * @date 2021/03/10
 */
public class PropertiesUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private PropertiesUtils() {
    }

    /**
     * 读取properties配置文件
     *
     * @param propFileName 配置文件名
     * @return 返回配置文件内容
     */
    public static Properties loadFromEnvProperties(String propFileName) {
        Properties prop = new Properties();
        // 读入envProperties属性文件
        try {
            ClassLoader classLoader = PropertiesUtils.class.getClassLoader();
            InputStream in = classLoader.getResourceAsStream(propFileName);
            // 加载属性列表
            prop.load(in);
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            logger.error("配置文件加载失败，请检查 " + propFileName + "文件是否存在！");
        }
        return prop;
    }
}
