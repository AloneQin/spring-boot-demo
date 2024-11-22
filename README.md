# Spring Boot Demo

[TOC]

包含`SpringBoot`项目多场景下常用使用姿势，供日常参考查阅。

## 项目结构
```
spring-boot-demo
    |-src
        |-main
            |-java
                |-com.example.demo
                    |-common                                --通用包
                        |-config                            --配置类
                        |-exception                         --自定义异常
                        |-filter                            --过滤器
                        |-interceptor                       --拦截器
                        |-lisenser                          --监听器
                            |-event                         --自定义事件
                        |-metadata                          --元数据
                            |-constant                      --常量包
                            |-enums                         --枚举包
                        |-response                          --自义定响应
                        |-task                              --定时周期任务
                    |-controller                            --控制层
                    |-dao                                   --数据访问层
                        |-mapper                            --Mapper（CURD）
                        |-wrapper                           --对Mapper的二次封装
                    |-model                                 --模型层
                        |-dto                               --数据传输对象
                        |-po                                --持久化对象
                        |-vo                                --视图对象
                    |-service                               --服务层
                    |-utils                                 --工具包
                    --SpringBootDemoApplication.java        --启动类
            |-resource                                      --资源文件夹
                |-asciidoc                                  --文档文件夹
                |-generator                                 --MyBatis生成器配置        
                |-mapper                                    --MyBatis XML
                |-scripts                                   --脚本文件夹
                |-sql                                       --sql文件夹
                |-static                                    --静态资源文件夹
                |-templates                                 --动态资源文件夹
                --application.properties                    --启动配置
        |-test                                              --测试包
    |-target                                                --编译输出
    --pom.xml                                               --Mavne配置
    --README.md                                             --快速开始
```

### 分层及调用关系
调用顺序由上及下，禁止逆序调用，`Service`层允许平级调用。
```
                    Controller --> VO
                        |
                        |
 Other Service  -->  Service   --> DTO
                        |
                        |
                       DAO     --> PO
```

## 过滤器
参考`com.example.demo.common.filter`

1. 实现`javax.servlet.Filter`接口；
2. 建议在配置类中编码配置过滤器，见`FilterConfig`。

## 修改请求内容
参考`TestFilter`、`TestController#testRequestWrapper`

自定义`HttpServletRequestWrapper`修改请求头或参数列表。

## 拦截器
参考`com.example.demo.common.interceptor`

1. 实现`HandlerInterceptor`接口；
2. 自定义配置`WebMvcConfigurerAdapter`注册拦截器。

## 监听器&事件驱动
参考`com.example.demo.common.lisenser`

实现`ApplicationListener`获取监听事件。

## 接口参数获取
参考`RestController`

* 普通参数获取使用形参方式或`@PathParam`注解；
* `JSON`参数获取使用`@RequestBody`；
* `URL`参数获取使用`@PathVariable`；
* 请求头参数获取使用`@RequestHeader`；
* 单文件上传与多文件上传；
* 文件下载。

## 资源路径获取
参考`PathTest`

* 项目路径获取；
* 应用路径获取；
* 对`resources`目录资源操作。

总结：获取`resources`文件通过流获取，`jar`包中不能直接操作文件，项目运行时尽量避免将外部文件储存到项目目录之中。

## 统一返回结构
参考`com.example.demo.common.response`、`CommonReturnController`

目的：只要请求到达服务，不管之后是成功还是失败，都返回同一返回结构，方便调用方获取解析。

1. 定义同一返回结构：`DefaultResponse`；
2. 定义返回码枚举，集中管理所有返回码：`ReturnCodeEnum`；
3. 定义子类返回码，方便在子服务中创建子返回码：`MyReturnCode`；
4. 定义自动封装接口结果注解，格式化返回`JSON`数据：`@ResultFormat`。

## 异常处理
参考`com.example.demo.common.exception`、`CommonReturnController`

目的：为了保证统一返回结构，需要对异常进行全局处理，保证在任何情况下都封装成指定结构。

1. 自定义全局异常；
2. 定义全局异常处理控制器；

不使用`@ControllerAdvice`做全局异常处理，因为其只能处理`Controller`级别的异常，即请求必须进入控制器中的方法出现异常才能被捕获。
当异常不发生在控制器内，比如出现在过滤器、拦截器等地方或是一些特殊异常，无法被`@ControllerAdvice`捕获，故选择`ErrorController`做全局异常处理，**真·全局处理**。

为帮助调试，设定调试模式开关，在开关打开时，会将异常堆栈转码后返回给前端，便于快速定位问题，参考`SystemProperties`。

## 参数校验
参考`com.example.demo.controller`、`CommonReturnController`

借助`spring-boot-starter-validation`做参数校验，当提供的注解不满足时，可自行编码校验，参考`com.example.demo.controller.CommonReturnController#customValidated()`

## 数据访问
借助`MyBatis-Plus`完成`ORM`，详见：[官网](https://baomidou.com/)

* 代码生成器：`MyBatisPlusGeneratorUtils`；
* 基本操作：`PhoneService`；

## 日期转换
使用`LocalDateTime`取代`Date`，详见`LocalDateTimeSerializerConfig`。

## 重试机制
参考`com.example.demo.controller#testRetry`

## 异步调用
参考`com.example.demo.juc.CompletableFutureTest`

## 定时任务
参考`com.example.demo.common.task`

## 接口文档
### 文档管理
参考`SwaggerConfig`、`SwaggerController`

借助`Swagger`进行接口文档管理。

### 文档序列化
`Maven`引入`swagger2markup-maven-plugin`、`asciidoctor-maven-plugin`插件。

1. 运行`swagger2markup-maven-plugin`插件生成`ascii`格式文档；
2. 运行`asciidoctor-maven-plugin`插件将`ascii`文档转换为`html`；
3. 借助`Chrome`将`html`转换为`PDF`，打印 -> 另存为PDF -> 保存。

## 常用工具类
参考`com.example.demo.utils`

* `Json`转换；
* `Http`调用；
* 日期转换；
* 对象拷贝
* ...

## 业务接口示例
参考`PhoneController`

## 项目发布
参考`src/main/resources/scripts`

相关脚本功能如下：
* `update.sh`：借助`git`拉取最新代码并打包；
* `start.sh`：启动项目；
* `stop.sh`：停止项目；
* `restart.sh`：重启项目；
* `deploy.sh`：发布项目（通常运行此脚本即可）。

模拟`linux`运行环境目录结构：
```
+-app                     --应用存放根目录
  --deploy.sh    
  --start.sh
  --start.sh
  --stop.sh
  --update.sh
  |-spring-boot-demo      --项目目录
    ...                 
  --spring-boot-demo.jar  --可执行jar
  --service.log           --服务日志
```

模拟项目发布流程：
1. 服务器安装`git`；
2. 从远端克隆项目到`app`目录；
3. 执行`deploy.sh`脚本。

**TODO: 后续考虑借助`docker`完成项目发布**

## 瘦身包
通过将依赖抽取到外部可打瘦身包，避免每次打的`jar`包过大，详见`pom.xml`插件`maven-dependency-plugin`相关配置。

## 远程调试
遵循JVM规范进行远程调试，在启动命令中添加`agentlib`命令。

如`java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 "-Dloader.path=./lib" -jar spring-boot-demo.jar`

## 其他
字符图案生成网站：http://patorjk.com/

* Alpha(自动大写、立体、大)
* Isometric1（自动大写、立体、中）
* Isometric3（自动大写、立体、中）
* Isometric4（自动大写、立体、中）
* BlurVision ASCII（自动大写、层次感、中）
* ANSI Regular（自动大写、清晰、小）
* Doom（清晰、小）
* Ogre（清晰、小）
* Standard（清晰、小）
* Slant（清晰、斜体、小）