package com.example.demo.controller;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 控制器中常用的参数获取方式及写法
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    /**
     * 形参（根据参数名称）获取参数，最常用的参数获取方式
     * 优点：使用简单，不需要额外注解
     * 场景：获取 Params 或 Body 中的参数，但不适用获取 json 中的参数（即不适用于 Content-Type=application/json），常用来获取少量参数
     */
    @PostMapping("/getPara")
    public String getPara(String a, String b) {
        return a + b;
    }

    /**
     * @PathParam 注解与形参获取类似，只不过需要显示声明，唯一的区别在于按注解值进行映射，故参数名称可以随意命名
     */
    @PostMapping("/getPara2")
    public String getPara2(@PathParam("a") String a2, @PathParam("b") String b2) {
        return a2 + b2;
    }

    /**
     * @RequestBody 用于获取 Content-Type 为 application/json, application/xml 等格式的数据，通常用来获取 json
     * 所有的对象都推荐用 application/json 格式进行传递，养成良好习惯
     */
    @PostMapping("/getPara3")
    public String getPara3(@RequestBody Map<String, String> map) {
        String a = map.get("a");
        String b = map.get("b");

        return a + b;
    }

    /**
     * @PathVariable 获取 url 中的参数
     */
    @GetMapping("/getPara4/{a}/{b}")
    public String getPara4(@PathVariable("a") String a, @PathVariable("b") String b) {
        return a + b;
    }

    /**
     * @RequestHeader 获取请求头中的参数
     */
    @GetMapping("/getHeader")
    public String getHeader(@RequestHeader("token") String token) {
        return token;
    }

    /**
     * 文件上传，并指定上传文件时的参数名
     */
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("savePath") String savePath) {
        log.info("file name: {}", file.getOriginalFilename());
        log.info("file size: {}", file.getSize());

        Path path = Paths.get(savePath + file.getOriginalFilename());

        try {
            // 文件拷贝
            file.transferTo(path);
            return "SUCCESS";
        } catch (IOException e) {
            log.error("文件上传失败: ", e);
            return "FAIL";
        }
    }

    /**
     * 多文件上传，限制个数
     */
    @PostMapping("/multiFileUpload")
    public String multiFileUpload(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        return file1.getOriginalFilename() + "," + file2.getOriginalFilename();
    }

    /**
     * 多文件上传，不限制个数，所有文件参数的参数名必须是 file
     */
    @PostMapping("/multiFileUpload2")
    public String multiFileUpload2(@RequestParam("file") List<MultipartFile> fileList) {
        String fileName = null;
        for (int i = 0; i < fileList.size(); i++) {
            fileName += fileList.get(i).getOriginalFilename();
            if (i != fileList.size() - 1) {
                fileName += ",";
            }
        }

        return fileName;
    }

    /**
     * 文件下载
     */
    @GetMapping("/fileDownload")
    public void fileDownload(HttpServletResponse response) throws Exception {
        Path path = Paths.get("/Users/alone/work/test/logo.png");
        File file = path.toFile();
        if (!file.exists()) {
            log.error("file not exists: {}", file.getPath());
            throw new BaseException(ReturnCodeEnum.FAIL);

        }

        ResponseUtils.fileDownload(new FileInputStream(file), file.getName(), response);
    }
}
