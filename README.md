# Java接口自动化测试框架

在使用接口测试工具时，常遇到的问题接口零散不便于管理，测试数据生成难，测试流程不易组成，测试结果单一不直观……很多的痛点，造成我们在手工测试接口的时候，大量的时间在一些繁琐的工作，因此，完善的接口自动化测试框架是我们提高测试效率的非常有效地解决方案。

在我遇到的接口测试工作中遇到的**痛点**：

- 有的接口传入参数非常多（遇到最多，有近一百多个字段），人工去做对比就非常耗时耗力（纯属体力活）
- 接口经常变更，如果拿JMeter、Postman这些接口工具进行管理，不易追溯接口变更哪些
- 异步接口，有些接口请求后，同步返回了接口结果，但是实际的逻辑处理还未完成，需要等待处理完成后才能进行下一步（如果是人工等的话，那么就会容易遗忘）
- 接口关联，开发同学更喜欢称为上下文，或者流程编排吧，JMeter、Postman也是可以做到该功能，但是流程不够直观
- ……

相信我们在做接口测试的时候会遇到各种各样的痛点、问题吧，我们会在这上面花费非常多的时间，导致我们会测试时间紧张，又在紧急的交付周期内，不好做到充分的测试，对于冒烟测试、回归测试都需要自动化的方案来帮我们节约人力、时间，在每次新的需求确保不改变原有的功能，让我们更好的关注变更的地方，确保按时交付的质量。

本框架采用Java纯代码，以Api Object的思想（源于Selenium提出的Page Object的模式）将Api作为对象进行操作，封装工具类，例如数据自动生成类、HTTP请求工具类等……下面，我们就具体的介绍该接口自动化测试框架。

# 技术栈

- 开发语言：Java（版本：jdk1.8）
- 项目管理工具：Maven（版本：3.6.3）
- 单元测试框架：Junit5
- 接口请求框架：Rest-Assured
- 模板框架：Mustache
- 工具包类：Hutool
- 日志框架：slf4j-log4j12
- 测试报告框架：Allure2
- 数据生成框架faker、bank_card ：Python拥有的类库

# 设计框架规则

系统是长期进行的，接口测试框架也是根据系统应运而生的，因此我们长期在维护编写接口自动化代码的过程中，请务必遵守好以下规则，使得我们的代码清晰明了、代码编写风格统一、通用性强、可维护、可扩展等，下面我就介绍我在编写该框架遵守的规则：

1. 代码规范，本框架采用的是Java语言，因此遵守的是[阿里巴巴Java开发手册](https://github.com/alibaba/p3c)中定制的规则

   阿里巴巴Java开发手册中的规则很多，而且阿里巴巴也不定期的更新中，Idea工具中提供了“Alibaba Java Coding Guidelines”插件，在我们编写代码的过程中随时提醒我们的问题，并给出建议进行优化，[使用参考说明](https://www.cnblogs.com/bestzhang/p/util.html)

2. 代码格式，格式统一化，对以后阅读、维护代码是非常重要的

   - 源码文件必须为UTF-8编码
   - 代码缩进使用4个空格
   - 提交的代码必须要符合IDEA格式化规则（既可直接使用IDEA代码格式化功能）

   通过人工来校对代码格式化，难免会有遗漏，在我们常使用的Idea工具中就提供了非常好的插件“Save Actions”自动进行格式化处理，我们所做的就是编写完代码后保存即可。

   ![image-20200915140957575](https://gitee.com/JeanLv/study_image2/raw/master///image-20200915140957575.png)

3. 代码中注释说明，类、方法、成员变量都要添加注释说明，定义注释规则，在编写的过程中都要遵守该规则

   eg1:

   ```java
   		/**
        * Xxx Service 类
        *
        * @author xxx
        * @date 2019/11/21
        */
       public class XxxService {
       }
   ```

   eg2:

   ```java
   // 如果类没有类说明可以在@auther上边留一个空行
   /**
    *
    * @author xxx
    * @date 2019/11/21
    */
   public class XxxService {
   }
   ```

   IDEA里面可以创建相关的代码模板，只要新建类的时候IDEA会自动给加上

   IDEA模板配置 IntelliJ IDEA --> Preferences(快捷键：command + ,) --> Editor --> File and Code Templates --> Includes --> File Header

   ```java
       /**
        *
        *
        * @author xxx
        * @date ${YEAR}/${MONTH}/${DAY}
        */
   ```

   

4. 版本控制，Git分支规范、提交日志规范，可详见博客文章中介绍：https://jinglv.github.io/2020/12/12/git/

5. 系统日志记录清晰，方便调试



# 框架整体介绍

在市面上有很多不错的开源集成平台（例如：MeterSphere）设计的非常不错，但我们为什么在公司还要单独的开发框架或者平台呢？个人观点，不错的平台是融合了多种情况，平台本身具有限制，使用时也有很大学习成本，还需要将平台的功能将公司的系统进行融合，虽然不错但是不一定适合。框架在设计时，我们需要考虑被测系统的特点，根据特定进行**定制化**的框架设计，设计的框架职责单一，可以很好的融入到开发流程中，更方便地集成到CICD中。

被测系统：https://github.com/jinglv/spring-boot-restful-api

接口文档

![image-20210310224858514](https://gitee.com/JeanLv/study_image2/raw/master///image-20210310224858514.png)

## 框架的基础功能介绍

![image-20210310222647615](https://gitee.com/JeanLv/study_image2/raw/master///image-20210310222647615.png)

## 测试用例编写介绍

Java工程中是将Test测试用例，放到test/java目录下，与基础代码分离，便于维护，我们采用的是maven项目管理工具，更方便使用maven命令执行测试用例（注意：如果将测试用例放到src/java目录下，使用maven命令式不执行的）

![image-20210310223359876](https://gitee.com/JeanLv/study_image2/raw/master///image-20210310223359876.png)

测试用例编写时，采用Junit5、Allure2注解进行管理，Junit5、Allure2在本博客下有详细的介绍



# 框架中功能的详细介绍

## 请求发送工具类

接口测试最关键的是发送请求，在封装该工具类时，要清晰的知道发送接口需要什么，根据接口的需求进行定制服务开发

```java
package com.api.test.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Rest-Assured请求工具类封装
 *
 * @author jinglv
 * @date 2021/03/10
 */
public class RequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    private RequestUtils() {
    }

    /**
     * 接口请求方法
     *
     * @param method      请求方法
     * @param path        请求路径
     * @param contentType 请求文本类型
     * @param headers     请求头信息
     * @param body        请求主体
     * @return Response
     */
    public static Response run(String method, String path, String contentType, Map<String, Object> headers, String body) {
        logger.info("请求数据，请求方法:{},请求路径:{},请求文本类型:{},请求头信息:{},请求主体:{}", method, path, contentType, JSONUtil.parse(headers), JSONUtil.parse(body));
        RequestSpecification requestSpecification = given().log().all();
        if (StrUtil.isNotBlank(contentType)) {
            requestSpecification.contentType(contentType);
        }
        if (StrUtil.isNotBlank(contentType)) {
            requestSpecification.headers(headers);
        }
        if (StrUtil.isNotBlank(contentType)) {
            requestSpecification.body(body);
        }
        // 将拼装好的接口进行请求
        return requestSpecification.request(method, path).then().log().all().extract().response();
    }

}
```

