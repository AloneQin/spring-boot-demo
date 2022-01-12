package com.example.demo.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * swagger 配置类
 * swagger2 主页：http://${IP}:${PORT}/${CONTEXT_PATH}/swagger-ui/index.html
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * API 扫描包名
     */
    private static final String API_BASE_PACKAGE = "com.example.demo.controller";

    /**
     * 允许展示 API 文档的接口列表
     *
     * ant 风格：
     * ? : 匹配一个字符
     * * : 匹配多个字符
     * **: 匹配多层路径
     *
     */
    private static final List<String> ALLOW_PATH_LIST = Arrays.asList(
            "/swagger/**",
            "/phone/**"
    );

    @Bean
    public Docket createRestApi() {
        AntPathMatcher matcher = new AntPathMatcher();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(API_BASE_PACKAGE))
                .paths((realPath) -> {
                    for (String regex : ALLOW_PATH_LIST) {
                        if (matcher.match(regex, realPath)) {
                            return true;
                        }
                    }

                    return false;
                })
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-boot-demo")
                .description("接口API文档")
                .version("1.0.0")
                .build();
    }
}
