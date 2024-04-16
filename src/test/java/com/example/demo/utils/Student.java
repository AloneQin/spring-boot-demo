package com.example.demo.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;

    @DateTimeFormat("yyyy-MM-dd")
    private String birthday;

    @ExcelProperty(converter = CustomGenderAliasConverter.class)
    private String gender;

    private Float height;

}
