package com.example.demo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.DefaultStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ExcelUtilsTest {

    /**
     * 1. 必须显式指明表头类型，否则出现转型错误
     * 2. 实体类必须是标准的 Java Bean，否则无法构建实体类实例
     * 3. 默认按列顺序和实体顺序赋值，或使用 @ExcelProperty、@ExcelIgnore 注解自行进行属性映射
     */
    public static void main(String[] args) throws IOException {
        List<Student> studentList = null;
        ClassPathResource cpr = new ClassPathResource("templates" + File.separator + "demo.xlsx");

        // 读取 sheet
        studentList = ExcelUtils.readExcelSheet(cpr.getInputStream(), Student.class, 1, 2, ArrayList::new);
        log.info("Read:" + FastjsonUtils.toString(studentList));

        // 分页读取 sheet
        ExcelUtils.readExcelSheetByPage(cpr.getInputStream(), Student.class, 1, 0, 1, entityList -> {
            System.out.println("Read Page: " + FastjsonUtils.toString(entityList));
        });

        // 读取全部
        studentList = ExcelUtils.readExcelAll(cpr.getInputStream(), Student.class, 1, ArrayList::new);
        System.out.println("Read All: " + FastjsonUtils.toString(studentList));

        // 分页读取全部
        ExcelUtils.readExcelAllByPage(cpr.getInputStream(), Student.class, 1, 1, entityList -> {
            log.info("Read All Page: " + FastjsonUtils.toString(entityList));
        });

        System.out.println(FastjsonUtils.toStringFormat(getHead()));
    }

    private static List<List<String>> getHead() {
        return Arrays.asList(
                List.of("title11", "title12"),
                List.of("title21", ""),
                List.of("title31", "title32")
        );
    }

    private static List<List<String>> getData() {
        return Arrays.asList(
                Arrays.asList("1", "2", "3", "4"),
                Arrays.asList("5", "6"),
                Arrays.asList("7", "8", "9")
        );
    }

    private static List<Student> getStudents() {
        return Arrays.asList(
                new Student("jack", "2004-4-16", "男", 175.0f),
                new Student("tom", "2004-1-16", "男", 170.0f)
        );
    }

    private static List<Teacher> getTeachers() {
        return Arrays.asList(
                new Teacher("jack", "100101100101100101100101100101100101"),
                new Teacher("tom", "100102")
        );
    }

    @Test
    public void writeExcel1() {
        String path = StringUtils.padding("./target/{}", "test.xlsx");
        ExcelUtils.writeExcel(path, null, "老师信息", Teacher.class, getTeachers());
    }

    @Test
    public void writeExcel2() {
        String path = StringUtils.padding("./target/{}", "test2.xlsx");
        // 宽度自适应
        ExcelUtils.writeExcel(path, null, "老师信息", Teacher.class, getTeachers(), new LongestMatchColumnWidthStyleStrategy());
    }

    @Test
    public void writeExcel3() {
        String path = StringUtils.padding("./target/{}", "test3.xlsx");
        ExcelUtils.writeExcel(path, null,"sheet", getHead(), getData());
    }

    @Test
    public void writeExcel4() {
        // 设置表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        // 设置内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        String path = StringUtils.padding("./target/{}", "test4.xlsx");
        ExcelUtils.writeExcel(path, null, "sheet", getHead(), getData(), horizontalCellStyleStrategy);
    }

    /**
     * 持续写
     */
    @Test
    public void writeExcel5() {
        String path = StringUtils.padding("./target/{}", "test5.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(path).build();
        for (int i = 0; i < 5; i++) {
            WriteSheet writeSheet = EasyExcel.writerSheet(i,"sheet" + i).head(getHead()).build();
            excelWriter.write(getData(), writeSheet);
        }
        excelWriter.finish();
    }
}