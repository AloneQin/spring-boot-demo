package com.example.demo.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * https://mp.baomidou.com/guide/generator-new.html#%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8
 */
@SpringBootTest
public class MyBatisPlusGeneratorUtils {

    public static void main(String[] args) {
        FastAutoGenerator
                        // 数据库连接方式
                .create("jdbc:mysql://localhost:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true",
                        // 用户名
                        "root",
                        // 数据库密码
                        "mysql#root")
                // 全局配置
                .globalConfig((builder) -> builder
                        // 设置作者
                        .author("alone")
                        // 覆盖已生成文件
                        .fileOverride()
                        // 文件输出目录
                        .outputDir(System.getProperty("user.dir") + "/src/main/java/"))
                // 包配置
                .packageConfig((builder) -> builder
                        // 设置父包名
                        .parent("com.example.demo")
                        // 父包模块名
//                        .moduleName("")
                        // Entity 包名
                        .entity("domain.po")
                        // Mapper 包名
                        .mapper("dao.mapper")
                        // Mapper XML 包名
                        .xml("dao.mapper")
                        // Service 包名
                        .service("dao.wrapper")
                        // Service Impl 包名
                        .serviceImpl("dao.wrapper.impl")
                        // Controller 包名（多余文件，需手动删除）
                        .controller("")
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        // 需要生成的表名
                        .addInclude("phone")
                        // 去除表前缀
                        .addTablePrefix("")
                        // 去除表后缀
                        .addTableSuffix("")
                        // 跳过视图
                        .enableSkipView()
                        // 启用 schema
//                        .enableSchema()
                        // 实体策略配置
                        .entityBuilder()
                                // 禁用生成 serialVersionUID
                                .disableSerialVersionUID()
                                // 开启生成实体时生成字段注解
                                .enableTableFieldAnnotation()
                                // 开启 lombok 模型
                                .enableLombok()
                                // 格式化文件名称
                                .formatFileName("%sPO")
                        // service 策略配置
                        .serviceBuilder()
                                .formatServiceFileName("%sDAO")
                                .formatServiceImplFileName("%sDAOImpl")
                )
                .execute();
        System.out.println("success!");
    }
}
