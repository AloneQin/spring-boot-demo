package com.example.demo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
class ExcelUtilsTest {

    /**
     1. 必须显式指明表头类型，否则出现转型错误
     2. 实体类必须是标准的 Java Bean，否则无法构建实体类实例
     3. 默认按列顺序和实体顺序赋值，或使用 @ExcelProperty、@ExcelIgnore 注解自行进行属性映射
     */
    public static void main(String[] args) throws IOException {
        List<Student> studentList = null;
        ClassPathResource cpr = new ClassPathResource("templates" + File.separator + "demo.xlsx");

        // 读取 sheet
        studentList = ExcelUtils.readExcelSheet(cpr.getInputStream(), Student.class, 1, 2,  ArrayList::new);
        log.info(FastjsonUtils.toString(studentList));

        // 分页读取 sheet
        ExcelUtils.readExcelSheetByPage(cpr.getInputStream(), Student.class, 1, 0, 1, entityList -> {
            System.out.println("读取完成: " + FastjsonUtils.toString(entityList));
        });

        // 读取全部
        studentList = ExcelUtils.readExcelAll(cpr.getInputStream(), Student.class, 1, ArrayList::new);
        System.out.println(FastjsonUtils.toString(studentList));

        // 分页读取全部
        ExcelUtils.readExcelAllByPage(cpr.getInputStream(), Student.class, 1, 1, entityList -> {
            log.info("读取完成: " + FastjsonUtils.toString(entityList));
        });

        // 写 Excel
        String path = "/Users/alone/work/test/test.xlsx";
        List<Teacher> teacherList = Arrays.asList(
                new Teacher("jack", 100101),
                new Teacher("tom", 100102)
        );
        ExcelUtils.writeExcel(path, "老师信息", Teacher.class, teacherList);


        List<List<String>> head = new ArrayList<>();
        head.add(Arrays.asList("1"));
        head.add(Arrays.asList("2"));
        head.add(Arrays.asList("3"));

        ExcelWriter excelWriter = EasyExcel.write(path).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("sheet").head(head).build();
        excelWriter.write(head, writeSheet);
        excelWriter.finish();
    }
}