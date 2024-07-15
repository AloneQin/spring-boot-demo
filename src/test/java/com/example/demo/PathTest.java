package com.example.demo;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class PathTest {

    public static void main(String[] args) throws Exception {
        /**
         * 获取项目路径
         *
         * 本地开发时获取的是项目根目录，在 jar 包中获取的是 jar 包所在的上级目录
         *
         * 示例：
         * 在 IDEA 中：/Users/alone/work/dev/IdeaProjects/spring-boot-demo
         * 打成 jar 后：/Users/alone/work/dev/IdeaProjects/spring-boot-demo/target
         */
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);

        /**
         * 获取应用路径
         *
         * 本地开发时获取的是编译生成的 classes 路径，在 jar 包中获取的是 jar 包的路径
         *
         * 示例：
         * 在 IDEA 中：/Users/alone/work/dev/IdeaProjects/spring-boot-demo/target/classes
         * 打成 jar 后：/Users/alone/work/dev/IdeaProjects/spring-boot-demo/target/spring-boot-demo-1.0.0-SNAPSHOT.jar
         */
        ApplicationHome ah = new ApplicationHome(PathTest.class);
        System.out.println(ah.getSource().getPath());

        /**
         * 获取 resource 中的文件
         *
         * 本地开发时能获取具体的文件，打成 jar 包后只能用流获取
         */
        ClassPathResource cpr = new ClassPathResource("templates" + File.separator + "天净沙·秋思.txt");

        // 打成 jar 包后获取文件具体路径会报错
        //System.out.println(cpr.getFile().getAbsoluteFile());

        InputStreamReader isr = new InputStreamReader(cpr.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String len;
        while((len = br.readLine()) != null){
            System.out.println(len);
        }
        isr.close();
        br.close();

        /**
         * 使用 ResourceLoader 获取
         * 必须先借助 spring bean 注入 resourceLoader
         */
        //ResourceLoader resourceLoader = null;
        //Resource resource = resourceLoader.getResource("classpath:" + "xxx");

        String path = ResourceUtils.getURL("classpath:aaa").getPath();
        System.out.println(path);
    }
}
