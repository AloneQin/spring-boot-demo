package com.example.demo.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;

/**
 * 自定义性别别名转换器
 */
public class CustomGenderAliasConverter implements Converter<String> {

    @Override
    public String convertToJavaData(ReadConverterContext<?> context) throws Exception {
        String gender = context.getReadCellData().getStringValue();
        if ("男".equals(gender)) {
            return "小哥哥";
        } else if ("女".equals(gender)) {
            return "小姐姐";
        }
        return gender;
    }

}
