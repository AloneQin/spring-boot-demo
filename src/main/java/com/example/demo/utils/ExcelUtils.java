package com.example.demo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtils {

    public static void main(String[] args) throws IOException {


        ClassPathResource cpr = new ClassPathResource("templates" + File.separator + "demo.xlsx");
        ExcelReader reader = EasyExcel.read(cpr.getInputStream(), new AnalysisEventListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).build();


    }

}
