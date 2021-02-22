# Spring Boot Demo

[TOC]

包含`SpringBoot`项目常用使用姿势，供日常参考查阅。

## 过滤器
参考`com.example.demo.common.filter`

1. 实现`javax.servlet.Filter`接口；
2. 使用`@WebFilter`注解；
3. 过滤器上使用`@Component`注解加入容器（不建议在启动类上使用`@ServletComponentScan`注解，`@Order`将失效）；
4. 使用`@Order`注解设定过滤器顺序（数值越小，越先执行）。

## 修改请求
参考`TestFilter`、`TestController#testRequestWrapper`

自定义`HttpServletRequestWrapper`修改请求头或参数列表。

## 拦截器
参考`com.example.demo.common.interceptor`

1. 实现`HandlerInterceptor`接口；
2. 自定义配置`WebMvcConfigurerAdapter`注册拦截器。

## 监听器
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

1. 定义同一返回结构；
2. 定义返回码枚举，集中管理所有返回码；

## 异常处理
参考`com.example.demo.common.exception`、`CommonReturnController`

目的：为了保证统一返回结构，需要对异常进行全局处理，保证在任何情况下都封装成指定结构。

1. 自定义全局异常；
2. 定义全局异常处理控制器；

不使用`@ControllerAdvice`做全局异常处理，因为其只能处理`Controller`级别的异常，即请求必须进入控制器中的方法出现异常才能被捕获。
当异常不发生在控制器内，比如出现在过滤器、拦截器等地方或是一些特殊异常，无法被`@ControllerAdvice`捕获，故选择`ErrorController`做全局异常处理，**真·全局处理**。

## 参数校验
参考`com.example.demo.controller`、`CommonReturnController`

借助`spring-boot-starter-validation`做参数校验，当提供的注解不满足时，可自行编码校验，参考`com.example.demo.controller.CommonReturnController#customValidated()`

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

## 业务接口示例
参考`PhoneController`

