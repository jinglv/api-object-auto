package com.api.test.utils;

import cn.hutool.json.JSONUtil;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 根据对应的对象和模板生成对应的数据
 *
 * @author jingLv
 * @date 2020/04/15
 */
public class MustacheUtils {

    private static final Logger logger = LoggerFactory.getLogger(MustacheUtils.class);

    /**
     * mustache文件路径，在resources目录下
     */
    private final String path;
    /**
     * 模板内容对应的对象 apimodel下
     */
    private final Object model;

    public MustacheUtils(String path, Object model) {
        this.path = path;
        this.model = model;
    }

    /**
     * 根据模板及模板对象，指定的值进行替换
     *
     * @return 返回已替换的内容
     */
    public String execute() {
        logger.info("读取模板文件:{}，替换的内容:{}", this.path, JSONUtil.parse(model));
        StringWriter writer = new StringWriter();

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(this.path);
        try {
            mustache.execute(writer, this.model).flush();
        } catch (IOException e) {
            logger.error("模板内容解析异常：{}", e.getMessage());
        }
        return writer.toString();
    }
}
