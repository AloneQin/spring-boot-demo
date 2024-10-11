package com.example.demo.controller;

import com.example.demo.common.interceptor.WebMvcConfig;
import com.example.demo.common.minio.MinioManager;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.model.vo.DemoVO;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.ResponseUtils;
import io.minio.ObjectWriteResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 控制器中常用的参数获取方式及写法
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/rest")
public class MyRestController {

    private final MinioManager minioManager;

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
     * 请求地址拼接参数和表单格式，可以传对象，仅限非常简单的参数，特殊符号均需要转码，不支持复杂对象
     * 建议用转义后的JSON字符串
     * @param demoVO 对象
     */
    @GetMapping("/getPara5")
    public DemoVO getPara5(DemoVO demoVO, String json) {
        log.info("getPara5, json: {}", URLDecoder.decode(json, StandardCharsets.UTF_8));
        return demoVO;
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
        try {
            file.getInputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("file name: {}", file.getOriginalFilename());
        log.info("file size: {}", file.getSize());
        return "SUCCESS";
    }

    /**
     * 文件上传到 Minio，并指定上传文件时的参数名
     */
    @PostMapping("/fileUploadToMinio")
    public ObjectWriteResponse fileUploadToMinio(@RequestParam("file") MultipartFile file) {
        return minioManager.uploadFile(file, file.getOriginalFilename(), file.getContentType());
    }

    /**
     * 获取 Minio 文件地址
     * @param objectName 对象名称
     * @param expires 过期时间，单位秒
     * @return 文件地址
     */
    @GetMapping("/getFileUrlByMinio")
    public String getFileUrlByMinio(String objectName, Integer expires) {
        return minioManager.getPresignedObjectUrl(objectName, expires);
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
        String fileName = "";
        for (int i = 0; i < fileList.size(); i++) {
            fileName += fileList.get(i).getOriginalFilename();
            if (i != fileList.size() - 1) {
                fileName += ",";
            }
        }

        return fileName;
    }

    /**
     * 同时上传文件与对象
     * @param demoVO 对象
     * @param file 文件
     */
    @PostMapping("/uploadFileAndObj")
    public void uploadFileAndObj(DemoVO demoVO, @RequestParam("file") MultipartFile file) {
        log.info("demoVO: {}, fileName: {}", demoVO, file.getOriginalFilename());
    }

    /**
     * 文件下载-附件形式
     * Content-Type:application/octet-stream 直接以附件形式进行下载
     */
    @GetMapping("/fileDownload")
    public void fileDownload(HttpServletResponse response) throws Exception {
        Path path = Paths.get("D:/work/dev/test/logo.png");
        File file = path.toFile();
        AssertUtils.isTrue(file.exists(), ReturnCodeEnum.FAIL, () -> log.warn("file not exists: {}", file.getPath()));
        ResponseUtils.fileDownloadByAttachment(new FileInputStream(file), file.getName(), response);
    }

    /**
     * 文件下载-直显方式
     * 不设置 Content-Type 浏览器支持的格式都会被直接预览，不支持的格式将会进行下载（但可能会导致文件格式出错）
     * 建议只对浏览器支持的常规格式采用此种下载方式，不支持的文件采用附件下载
     * 如果具有本地文件系统，推荐使用静态资源映射的方式进行展示，浏览器支持的文件会被直接预览，浏览器不支持的文件自动以附件的形式进行下载，且不会出现格式紊乱，详见 {@link WebMvcConfig#addResourceHandlers(ResourceHandlerRegistry)}
     */
    @GetMapping("/fileDownload2")
    public void fileDownload2(HttpServletResponse response, @RequestParam("fileName") String fileName) throws Exception {
        Path path = Paths.get("D:/work/dev/test/" + fileName);
        File file = path.toFile();
        AssertUtils.isTrue(file.exists(), ReturnCodeEnum.FAIL, () -> log.warn("file not exists: {}", file.getPath()));
//        ResponseUtils.outputStreamToResponse(new FileInputStream(file), response);
        ResponseUtils.fileDownload(new FileInputStream(file), fileName, response);
    }

    /**
     * 文件下载-文本方式
     * Content-Type:text/html 浏览器将会将流转换成文本内容进行显示，不会进行下载
     */
    @GetMapping("/fileDownload3")
    public Resource fileDownload3() {
        URL url = getClass().getResource("/images/logo.png");
        Resource resource = new UrlResource(url);
        return resource;
    }

}
