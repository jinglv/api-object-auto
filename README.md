# Java接口自动化测试框架

在使用接口测试工具时，常遇到的问题接口零散不便于管理，测试数据生成难，测试流程不易组成，测试结果单一不直观……很多的痛点，造成我们在手工测试接口的时候，大量的时间在一些繁琐的工作，因此，完善的接口自动化测试框架是我们提高测试效率的非常有效地解决方案。

在我遇到的接口测试工作中遇到的**痛点**：

- 有的接口传入参数非常多（遇到最多，有近一百多个字段），人工去做对比就非常耗时耗力（纯属体力活）
- 接口经常变更，如果拿JMeter、Postman这些接口工具进行管理，不易追溯接口变更哪些
- 异步接口，有些接口请求后，同步返回了接口结果，但是实际的逻辑处理还未完成，需要等待处理完成后才能进行下一步（如果是人工等的话，那么就会容易遗忘）
- 接口关联，开发同学更喜欢称为上下文，JMeter、Postman也是可以做到该功能，但是流程不够直观
- ……

相信我们在做接口测试的时候会遇到各种各样的痛点、问题吧，在这上面会花费非常多的时间，导致我们会测试时间紧张，又在紧急的交付周期内，不好做到充分的测试。因此，对于冒烟测试、回归测试都需要自动化的方案来帮我们节约人力、时间，在每次新的需求确保不改变原有的功能，让我们更好的关注变更的地方，确保按时交付的质量。

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

6. 统一技术栈，Hutool是一个非常好的集中式的工具包，例如：JSON处理、校验机制等，减少各种工具包的大量引用，避免在使用的时候混乱



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

import cn.hutool.core.map.MapUtil;
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
        if (MapUtil.isNotEmpty(headers)) {
            requestSpecification.headers(headers);
        }
        if (StrUtil.isNotBlank(body)) {
            requestSpecification.body(body);
        }
        // 将拼装好的接口进行请求
        return requestSpecification.request(method, path).then().log().all().extract().response();
    }
}
```

## Api Object类

根据接口文档，我们可以进接口的分类，接口对象类的编写

```java
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
    public static Response creatUserInfo(String userInfoBody, String token) {
        logger.info("创建用户信息ApiObject，请求主体：{},请求携带token：{}", userInfoBody, token);
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
        logger.info("查询所有用户信息ApiObject，请求携带token：{}", token);
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
        logger.info("通过userId查询用户信息ApiObject，请求UserId：{}，请求携带token：{}", userId, token);
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
        logger.info("通过userId更新用户信息ApiObject，请求UserId：{}，请求更新用户信息主体：{}，请求携带token：{}", userId, updateUserInfoBody, token);
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
        logger.info("通过userId删除用户信息ApiObject，请求UserId：{}，请求携带token：{}", userId, token);
        HEADER_MAP.put("token", token);
        return RequestUtils.run("delete", "/info/user/" + userId, "application/json", HEADER_MAP, null);
    }
}
```

## Api业务帮助类

该被测系统的鉴权是登录接口生成的token字符串传入到后续接口的headers中

```java
package com.api.test.helper;

import com.api.test.object.UserLoginApiObject;

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
```

## 测试任务类

在自动化测试执行完成后，我们需要对自动化测试过程中产生的数据进行清理，也是分两种情况：

1. 提供删除接口，执行完成后，调用接口清理即可，但删除接口也有两种情况：
   - 逻辑删除，实际是数据库修改操作，将数据标识符修改表示已删除
   - 物理删除，真实删除数据
2. 没有提供删除接口，需要直接操作数据库，注意：数据很重要，删除需谨慎

该被测系统中，已经提供了删除接口，查询所有用户信息，通过调用接口进行删除

```java
package com.api.test.task;

import com.api.test.object.UserInfoApiObject;
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
```

## 数据生成类

### Java中调用Python脚本

Faker和Mock技术在我们做自动化测试是非常实用的，测试数据伪造和测试结果挡板，让自动化测试流程不受外界干扰，在调研Java和Python语言中，本身就会提供些类库，支持该功能，但经过调研后，发现Python提供bank_card和faker的两个类库非常好用，因此在Java的自动化框架中集成了Python脚本的执行。

- Java中调用Python工具类

  ```java
  package com.api.test.utils;
  
  import java.io.BufferedReader;
  import java.io.IOException;
  import java.io.InputStreamReader;
  
  /**
   * @author jingLv
   * @date 2020/09/23
   */
  public class RunPythonUtils {
  
      public RunPythonUtils() {
      }
  
      /**
       * Python执行方法
       *
       * @param path Python代码地址
       * @return 返回执行结果
       */
      public static Object runPython(String path) {
          Process proc;
          try {
              proc = Runtime.getRuntime().exec("python3 " + path);
              BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
              String line;
              while ((line = in.readLine()) != null) {
                  return line;
              }
              in.close();
              proc.waitFor();
          } catch (IOException | InterruptedException e) {
              e.printStackTrace();
          }
          return null;
      }
  }
  ```

  该方式是推荐执行方式，但是该方式有个极大的缺陷，就是Python脚本执行异常，是捕捉不到的

- Python脚本

  ```python
  from faker import Faker
  
  fake = Faker(locale='zh_cn')  # 指定fake 所属地
  
  
  def four_info():
      """手机号码、姓名、邮箱、身份证号码"""
      mobile = fake.phone_number()
      name = fake.name()
      email = f'{mobile}@qq.com'
      id_card = fake.ssn()
  
      info_dict = {"name": name, "mobile": mobile, "id_card": id_card, "email": email}
  
      return info_dict
  
  print(four_info())
  ```

  ```python
  import random
  import ssl
  from bank_card import BankCard
  
  # ssl取消全局验证
  ssl._create_default_https_context = ssl._create_unverified_context
  
  
  def bank_random():
      """生成银行卡 校验版"""
      while True:
          bank = '62148301' + '%s' % (random.randint(0, int(8 * '9')))
          if len(bank) == 16:
              banks = BankCard(bank)
              if banks.to_dict().get("validated") is True:
                  break
              else:
                  continue
  
      return bank
  
  
  print(bank_random())
  ```

  注意：本地的python需要安装bank_card、faker类库，pip安装即可，`pip3 install bank_card`和`pip3 install faker`

### 数据生成工具类

```java
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
```

## 模板生成指定测试数据

详情请看博客文章：https://jinglv.github.io/2021/01/17/api/template/



# 测试用例编写

测试用例编写及管理，采用Junit5和Allure2两个框架

- Junit5博客文章详解：https://jinglv.github.io/2021/01/17/java/xunit/junit5/testcase/
- Allure2博客文章详解：https://jinglv.github.io/2021/01/17/api/report/allure/

该被测系统，编写完成的框架基础类，进行测试用例的编写，编写测试用例，也要遵守如下的规则：

1. Junit5注解使用，执行前需要初始化，执行后需要进行清理，完成用例的辅助功能
2. 每个测试用例，职责单一性，测试一个点，如果测试用例有关联功能，需要将功能编写公共方法，测试用例编写时串起来
3. 断言机制，调用接口完成后需要进行断言，断言主要从接口返回的code、message及接口返回的关键信息，来判断用例执行的成功与失败
4. Allure2注解使用，便于输出的测试报告，方便于我们查看

```java
package com.api.test.object;

import cn.hutool.json.JSONUtil;
import com.api.test.helper.TokenHelper;
import com.api.test.model.UserInfoModel;
import com.api.test.task.EvnTask;
import com.api.test.utils.FakerUtils;
import com.api.test.utils.MustacheUtils;
import com.api.test.utils.PropertiesUtils;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

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
        String propFileName = "project.properties";
        Properties prop = PropertiesUtils.loadFromEnvProperties(propFileName);
        RestAssured.baseURI = prop.getProperty("test_url");
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
                "    \"email\":\"xiaohonghong@qq.com\",\n" +
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
```



# 用例执行及报告生成

本地执行命令：`mvn clean test`

```bash
bogon:api-object-auto jinglv$ mvn clean test
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for com.api.test:api-object-auto:jar:1.0-SNAPSHOT
[WARNING] 'dependencies.dependency.version' for org.junit.jupiter:junit-jupiter:jar is either LATEST or RELEASE (both of them are being deprecated) @ line 41, column 22
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO] 
[INFO] --------------------< com.api.test:api-object-auto >--------------------
[INFO] Building api-object-auto 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ api-object-auto ---
[INFO] Deleting /Users/jinglv/IdeaProjects/api-object-auto/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ api-object-auto ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 9 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ api-object-auto ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 12 source files to /Users/jinglv/IdeaProjects/api-object-auto/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ api-object-auto ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/jinglv/IdeaProjects/api-object-auto/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ api-object-auto ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 4 source files to /Users/jinglv/IdeaProjects/api-object-auto/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ api-object-auto ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
……
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  14.633 s
[INFO] Finished at: 2021-03-11T13:27:26+08:00
[INFO] ------------------------------------------------------------------------
```



本地生成Allure测试报告：`allure serve allure-results`

```bash
bogon:api-object-auto jinglv$ allure serve target/allure-results/
Generating report to temp directory...
Report successfully generated to /var/folders/4t/_zr69rv96_n09j994m_z0dbc0000gn/T/8855401238572421013/allure-report
Starting web server...
2021-03-11 13:32:46.293:INFO::main: Logging initialized @1913ms to org.eclipse.jetty.util.log.StdErrLog
Server started at <http://192.168.1.14:64194/>. Press <Ctrl+C> to exit

```

启动成功，本地默认浏览器自动打开生成的测试报告

![image-20210311133355162](https://gitee.com/JeanLv/study_image2/raw/master///image-20210311133355162.png)



# 总结

已完成基本的自动化测试框架的介绍，遇到实际不同的问题，需要针对性、定制化的解决，本篇也只是提供一种方案，下面讲讲本人的经验和看法。

最早在学习搭建自动化测试框架的时候，很多教程都是数据驱动模式，将测试数据存放到Excel中，使用Excel管理接口相关的内容及数据，这种方式本人在尝试过后，发现非常的不变，Excel其实是不便于管理接口相关数据的，如果接口的入参数据多，接口用例多，在使用Excel管理时，长时间维护后，Excel会变得非常臃肿，不直观，甚至感觉凌乱。而且还有一个问题，Excel是Windows专属的格式，虽然也支持了MacOS版，但Linux还是不支持的，跨平台非常差。后来也有人使用csv这种格式管理，但是还是没能解决长期维护后，文件多，文件内容庞大、凌乱。

测试数据、用例数据，不便于管理一直是一个问题，即使采用目前市面上禅道、JIRA这些管理工具，使用起来还是有些那么的不习惯和固有功能的限制，这也衍生出来，对测试平台化的开发，针对公司的需求、痛点提供解决方案。这是一个好的想法，但是紧接着问题来了，平台化的东西，可不是编写一个框架那么容易，平台化也意味着产品化，有明确的需求、系统设计，涉及很多的东西。不仅我所在的公司，还有和朋友聊天，很多都在推行这个方案，测试提出来的，那么也是由测试来完成啊，因此就推动测试进行开发，可是这是在保证系统交付完成的前提下，进行的额外工作，又由于测试的开发水平差异，那么可想而知，预期结果没有那么好。或者说，专门成立一个团队，开发水平都不错的测试进行平台开发，但是这由面临一个问题，这些测试不了解测试过程中的痛点，也不能很好的开发针对性的功能。

平台化的需求，个人认为也是很有必要的，将测试相关数据通过系统管理，数据库保存，直观的可视化展示，对我们地工作提供极大的方便，测试在参与过程中，也能很好的掌握开发技能，快速编写工具来完成提高工作效率。

以上的看法，我认为在公司发展一定的规模下势在必行，毕竟系统会越来越复杂、越来越庞大，如果只是人工接入，工期长、事情繁琐、还容易出错。但是，做这些我们还是首先确保系统的质量，自动化框架如何确保系统质量呢？这就是我们常提到的覆盖率，覆盖率分为需求覆盖率和代码覆盖率，那我们如何保证呢？详细请看博客文章：https://jinglv.github.io/2021/03/08/test/left/actual/

本篇介绍的接口测试框架是API Object + TestCase方式，纯Java代码开发，优势是在于以API为核心对象，通过操作API的请求，参数化，并发等方式测试，保证接口测试各个场景的覆盖。之后结合可持续集成，完成对代码覆盖率查看，清晰准确的定位哪些行没有覆盖，可针对于无法使用自动化测试的地方，进行手动测试。这也是以后精准化测试流程趋势的提前。

目前还有一种流行的自动化框架搭建的方式，使用yaml/yml文件进行数据管理，比较流行的框架httprunner，就是基于yaml/yml文件管理，这是方式很容易联想到流程编排，例如我在学习Docker中的容器编排，还有微服务管理中的编排，也是非常好的一种方案，相比这种方案，我们通过管理yaml/yml编排流程，可快速的进行冒烟回归测试，及各场景的数据生成，相比于本篇介绍的方式来讲，各有优势，我们可按需选择。

基于yaml/yml接口自动化测试框架：https://github.com/jinglv/api-object-framework