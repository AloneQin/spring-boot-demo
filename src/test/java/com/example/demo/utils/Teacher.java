package com.example.demo.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @ExcelProperty("姓名（姓+名）")
    private String name;

    @ExcelProperty("工号（唯一编号）")
    private String no;

//    @ExcelProperty(value = "工号（唯一编号）", converter = ExcelUtilsTest.DateTimeConverter.class)
    private Long birthday;

}
