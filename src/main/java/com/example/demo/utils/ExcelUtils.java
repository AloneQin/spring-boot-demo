package com.example.demo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.DefaultStyle;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Excel 工具类 <br/>
 * 对 EasyExcel 的二次封装
 * 更多使用方式请查阅官网：<a href="https://www.yuque.com/easyexcel/doc/easyexcel">Easy Excel</a>
 */
@Slf4j
public class ExcelUtils {

    /**
     * 按 sheet 读取 Excel
     * @param inputStream 源文件输入流
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param sheetNo sheet 号，为 null 则默认取 0
     * @param entityListSupplier 实体集合提供者
     * @param <T> 实体泛型
     * @return 实体集合
     */
    public static <T> List<T> readExcelSheet(InputStream inputStream, Class<T> entityClass, Integer headRowNumber,
                                             Integer sheetNo, Supplier<List<T>> entityListSupplier) {
        List<T> entityList = entityListSupplier.get();
        ExcelReaderBuilder readerBuilder = buildReaderBuilder(inputStream, entityClass, entityList);
        readerBuilder = setHeadRowNumber(readerBuilder, headRowNumber);
        setSheet(readerBuilder, sheetNo).doRead();
        return entityList;
    }

    /**
     * 按 sheet 读取 Excel
     * @param file 源文件
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param sheetNo sheet 号，为 null 则默认取 0
     * @param entityListSupplier 实体集合提供者
     * @param <T> 实体泛型
     * @return 实体集合
     */
    public static <T> List<T> readExcelSheet(File file, Class<T> entityClass, Integer headRowNumber, Integer sheetNo,
                                             Supplier<List<T>> entityListSupplier) {
        List<T> entityList = entityListSupplier.get();
        ExcelReaderBuilder readerBuilder = buildReaderBuilder(file, entityClass, entityList);
        readerBuilder = setHeadRowNumber(readerBuilder, headRowNumber);
        setSheet(readerBuilder, sheetNo).doRead();
        return entityList;
    }

    /**
     * 按 sheet 读取 Excel
     * @param pathName 源文件全路径
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param sheetNo sheet 号，为 null 则默认取 0
     * @param entityListSupplier 实体集合提供者
     * @param <T> 实体泛型
     * @return 实体集合
     */
    public static <T> List<T> readExcelSheet(String pathName, Class<T> entityClass, Integer headRowNumber, Integer sheetNo,
                                             Supplier<List<T>> entityListSupplier) {
        List<T> entityList = entityListSupplier.get();
        ExcelReaderBuilder readerBuilder = buildReaderBuilder(pathName, entityClass, entityList);
        readerBuilder = setHeadRowNumber(readerBuilder, headRowNumber);
        setSheet(readerBuilder, sheetNo).doRead();
        return entityList;
    }

    /**
     * 按 sheet 分页读取 Excel
     * @param inputStream 源文件输入流
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param sheetNo sheet 号，为 null 则默认取 0
     * @param pageSize 每页大小
     * @param consumer 回调消费者
     * @param <T> 实体泛型
     */
    public static <T> void readExcelSheetByPage(InputStream inputStream, Class<T> entityClass, Integer headRowNumber,
                                                Integer sheetNo, Integer pageSize, Consumer<List<T>> consumer) {
        ExcelReaderBuilder readerBuilder = buildReaderBuilderByPage(inputStream, entityClass, pageSize, consumer);
        readerBuilder = setHeadRowNumber(readerBuilder, headRowNumber);
        setSheet(readerBuilder, sheetNo).doRead();
    }

    /**
     * 按 sheet 分页读取 Excel
     * @param file 源文件
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param sheetNo sheet 号，为 null 则默认取 0
     * @param pageSize 每页大小
     * @param consumer 回调消费者
     * @param <T> 实体泛型
     */
    public static <T> void readExcelSheetByPage(File file, Class<T> entityClass, Integer headRowNumber, Integer sheetNo,
                                                Integer pageSize, Consumer<List<T>> consumer) {
        ExcelReaderBuilder readerBuilder = buildReaderBuilderByPage(file, entityClass, pageSize, consumer);
        readerBuilder = setHeadRowNumber(readerBuilder, headRowNumber);
        setSheet(readerBuilder, sheetNo)
                .doRead();
    }

    /**
     * 按 sheet 分页读取 Excel
     * @param pathName 源文件路径
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param sheetNo sheet号，为 null 则默认取 0
     * @param pageSize 每页大小
     * @param consumer 回调消费者
     * @param <T> 实体泛型
     */
    public static <T> void readExcelSheetByPage(String pathName, Class<T> entityClass, Integer headRowNumber,
                                                Integer sheetNo, Integer pageSize, Consumer<List<T>> consumer) {
        ExcelReaderBuilder readerBuilder = buildReaderBuilderByPage(pathName, entityClass, pageSize, consumer);
        readerBuilder = setHeadRowNumber(readerBuilder, headRowNumber);
        setSheet(readerBuilder, sheetNo)
                .doRead();
    }

    /**
     * 不分 sheet 读取 Excel
     * @param inputStream 源文件输入流
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param entityListSupplier 实体集合提供者
     * @param <T> 实体泛型
     * @return 实体集合
     */
    public static <T> List<T> readExcelAll(InputStream inputStream, Class<T> entityClass, Integer headRowNumber,
                                           Supplier<List<T>> entityListSupplier) {
        List<T> entityList = entityListSupplier.get();
        ExcelReaderBuilder readerBuilder = buildReaderBuilder(inputStream, entityClass, entityList);
        setHeadRowNumber(readerBuilder, headRowNumber).doReadAll();
        return entityList;
    }

    /**
     * 不分 sheet 读取 Excel
     * @param file 源文件
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param entityListSupplier 实体集合提供者
     * @param <T> 实体泛型
     * @return 实体集合
     */
    public static <T> List<T> readExcelAll(File file, Class<T> entityClass, Integer headRowNumber,
                                           Supplier<List<T>> entityListSupplier) {
        List<T> entityList = entityListSupplier.get();
        ExcelReaderBuilder readerBuilder = buildReaderBuilder(file, entityClass, entityList);
        setHeadRowNumber(readerBuilder, headRowNumber).doReadAll();
        return entityList;
    }

    /**
     * 不分 sheet 读取 Excel
     * @param pathName 源文件路径
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param entityListSupplier 实体集合提供者
     * @param <T> 实体泛型
     * @return 实体集合
     */
    public static <T> List<T> readExcelAll(String pathName, Class<T> entityClass, Integer headRowNumber,
                                           Supplier<List<T>> entityListSupplier) {
        List<T> entityList = entityListSupplier.get();
        ExcelReaderBuilder readerBuilder = buildReaderBuilder(pathName, entityClass, entityList);
        setHeadRowNumber(readerBuilder, headRowNumber).doReadAll();
        return entityList;
    }

    /**
     * 不分 sheet 分页读取 Excel
     * @param inputStream 源文件输入流
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param pageSize 每页大小
     * @param consumer 实体集合消费者
     * @param <T> 实体泛型
     */
    public static <T> void readExcelAllByPage(InputStream inputStream, Class<T> entityClass, Integer headRowNumber,
                                              Integer pageSize, Consumer<List<T>> consumer) {
        ExcelReaderBuilder readerBuilder = buildReaderBuilderByPage(inputStream, entityClass, pageSize, consumer);
        setHeadRowNumber(readerBuilder, headRowNumber)
                .doReadAll();
    }

    /**
     * 不分 sheet 分页读取 Excel
     * @param file 源文件
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param pageSize 每页大小
     * @param consumer 实体集合消费者
     * @param <T> 实体泛型
     */
    public static <T> void readExcelAllByPage(File file, Class<T> entityClass, Integer headRowNumber, Integer pageSize,
                                              Consumer<List<T>> consumer) {
        ExcelReaderBuilder readerBuilder = buildReaderBuilderByPage(file, entityClass, pageSize, consumer);
        setHeadRowNumber(readerBuilder, headRowNumber)
                .doReadAll();
    }

    /**
     * 不分 sheet 分页读取 Excel
     * @param pathName 源文件路径
     * @param entityClass 实体类型
     * @param headRowNumber 表头行号，为 null 则默认取 1
     * @param pageSize 每页大小
     * @param consumer 实体集合消费者
     * @param <T> 实体泛型
     */
    public static <T> void readExcelAllByPage(String pathName, Class<T> entityClass, Integer headRowNumber,
                                              Integer pageSize, Consumer<List<T>> consumer) {
        ExcelReaderBuilder readerBuilder = buildReaderBuilderByPage(pathName, entityClass, pageSize, consumer);
        setHeadRowNumber(readerBuilder, headRowNumber)
                .doReadAll();
    }

    private static <T> ExcelReaderBuilder buildReaderBuilder(InputStream inputStream, Class<T> entityClass, List<T> entityList) {
        return EasyExcel.read(inputStream, entityClass, buildReadListener(entityList));
    }

    private static <T> ExcelReaderBuilder buildReaderBuilder(File file, Class<T> entityClass, List<T> entityList) {
        return EasyExcel.read(file, entityClass, buildReadListener(entityList));
    }

    private static <T> ExcelReaderBuilder buildReaderBuilder(String pathName, Class<T> entityClass, List<T> entityList) {
        return EasyExcel.read(pathName, entityClass, buildReadListener(entityList));
    }

    private static <T> ExcelReaderBuilder buildReaderBuilderByPage(InputStream inputStream, Class<T> entityClass, Integer pageSize, Consumer<List<T>> consumer) {
        PageReadListener<T> pageReadListener = new PageReadListener<>(consumer);
        pageReadListener.BATCH_COUNT = pageSize;
        return EasyExcel.read(inputStream, entityClass, pageReadListener);
    }

    private static <T> ExcelReaderBuilder buildReaderBuilderByPage(File file, Class<T> entityClass, Integer pageSize, Consumer<List<T>> consumer) {
        PageReadListener<T> pageReadListener = new PageReadListener<>(consumer);
        pageReadListener.BATCH_COUNT = pageSize;
        return EasyExcel.read(file, entityClass, pageReadListener);
    }

    private static <T> ExcelReaderBuilder buildReaderBuilderByPage(String pathName, Class<T> entityClass, Integer pageSize, Consumer<List<T>> consumer) {
        PageReadListener<T> pageReadListener = new PageReadListener<>(consumer);
        pageReadListener.BATCH_COUNT = pageSize;
        return EasyExcel.read(pathName, entityClass, pageReadListener);
    }

    private static <T> ExcelReaderSheetBuilder setSheet(ExcelReaderBuilder readerBuilder, Integer sheetNo) {
        // 指明 sheet，默认取首 sheet（sheetNo = 0）
        if (Objects.nonNull(sheetNo)) {
            return readerBuilder.sheet(sheetNo);
        } else {
            return readerBuilder.sheet();
        }
    }

    private static ExcelReaderBuilder setHeadRowNumber(ExcelReaderBuilder readerBuilder, Integer headRowNumber) {
        // 指明表头行号，默认取首行（headRowNumber = 1）
        if (Objects.nonNull(headRowNumber)) {
            readerBuilder = readerBuilder.headRowNumber(headRowNumber);
        }
        return readerBuilder;
    }

    private static <T> ReadListener buildReadListener(List<T> entityList) {
        return new ReadListener<T>() {
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                entityList.add(t);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                log.debug("read completed, total rows: {}", entityList.size());
            }
        };
    }

    /**
     * 写 Excel
     * @param outputStream 目标输出流
     * @param sheetName sheet 名称
     * @param entityClass 实体类型
     * @param data 数据源集合
     */
    public static <T> void writeExcel(OutputStream outputStream, String sheetName, Class<T> entityClass, Collection<T> data) {
        EasyExcel.write(outputStream, entityClass)
                .sheet(sheetName)
                .doWrite(data);
    }

    /**
     * 写 Excel
     * @param outputStream 目标输出流
     * @param sheetName sheet 名称
     * @param entityClass 实体类型
     * @param data 数据源集合
     * @param writeHandler 写处理器
     */
    public static <T> void writeExcel(OutputStream outputStream, String sheetName, Class<T> entityClass, Collection<T> data, WriteHandler writeHandler) {
        EasyExcel.write(outputStream, entityClass)
                .sheet(sheetName)
                .registerWriteHandler(writeHandler == null ? new DefaultStyle() {} : writeHandler)
                .doWrite(data);
    }

    /**
     * 写 Excel
     * @param file 目标文件
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param entityClass 实体类型
     * @param data 数据源集合
     */
    public static <T> void writeExcel(File file, Integer sheetNo, String sheetName, Class<T> entityClass, Collection<T> data) {
        writeExcel(file, sheetNo, sheetName, entityClass, data, null);
    }

    /**
     * 写 Excel
     * @param file 目标文件
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param entityClass 实体类型
     * @param data 数据源集合
     * @param writeHandler 写处理器
     */
    public static <T> void writeExcel(File file, Integer sheetNo, String sheetName, Class<T> entityClass, Collection<T> data, WriteHandler writeHandler) {
        EasyExcel.write(file, entityClass)
                .sheet(sheetName)
                .registerWriteHandler(writeHandler == null ? new DefaultStyle() {} : writeHandler)
                .doWrite(data);
    }

    /**
     * 写 Excel
     * @param pathName 目标输出流
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param entityClass 实体类型
     * @param data 数据源集合
     */
    public static <T> void writeExcel(String pathName, Integer sheetNo, String sheetName, Class<T> entityClass, Collection<T> data) {
        writeExcel(pathName, sheetNo, sheetName, entityClass, data, null);
    }

    /**
     * 写 Excel
     * @param pathName 目标输出流
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param entityClass 实体类型
     * @param data 数据源集合
     * @param writeHandler 写处理器
     */
    public static <T> void writeExcel(String pathName, Integer sheetNo, String sheetName, Class<T> entityClass, Collection<T> data, WriteHandler writeHandler) {
        EasyExcel.write(pathName, entityClass)
                .sheet(sheetName)
                .registerWriteHandler(writeHandler == null ? new DefaultStyle() {} : writeHandler)
                .doWrite(data);
    }


    /**
     * 写 Excel <br/>
     *
     * head e.g.: [["headCol1"],["headCol2"],["headCol3"]] <br/>
     * data e.g.: [["dataCol11","dataCol12","dataCol13"],["dataCol21","dataCol22","dataCol23"]]
     *
     * @param outputStream 目标输出流
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param head 表头：外层为列，内层为行，内层元素个数必须相等
     * @param data 数据源集合：外层为行，内层为列
     */
    public static <T> void writeExcel(OutputStream outputStream, Integer sheetNo, String sheetName, List<List<String>> head, List<List<String>> data) {
        writeExcel(outputStream, sheetNo, sheetName, head, data, null);
    }

    /**
     * 写 Excel <br/>
     *
     * head e.g.: [["headCol1"],["headCol2"],["headCol3"]] <br/>
     * data e.g.: [["dataCol11","dataCol12","dataCol13"],["dataCol21","dataCol22","dataCol23"]]
     *
     * @param outputStream 目标输出流
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param head 表头：外层为列，内层为行，内层元素个数必须相等
     * @param data 数据源集合：外层为行，内层为列
     * @param writeHandler 写处理器
     */
    public static <T> void writeExcel(OutputStream outputStream, Integer sheetNo, String sheetName, List<List<String>> head, List<List<String>> data, WriteHandler writeHandler) {
        EasyExcel.write(outputStream)
                .head(head)
                .sheet(sheetNo, sheetName)
                .registerWriteHandler(writeHandler == null ? new DefaultStyle() {} : writeHandler)
                .doWrite(data);
    }

    /**
     * 写 Excel <br/>
     *
     * head e.g.: [["headCol1"],["headCol2"],["headCol3"]] <br/>
     * data e.g.: [["dataCol11","dataCol12","dataCol13"],["dataCol21","dataCol22","dataCol23"]]
     *
     * @param file 目标文件
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param head 表头：外层为列，内层为行，内层元素个数必须相等
     * @param data 数据源集合：外层为行，内层为列
     */
    public static void writeExcel(File file, Integer sheetNo, String sheetName, List<List<String>> head, List<List<String>> data) {
        writeExcel(file, sheetNo, sheetName, head, data, null);
    }

    /**
     * 写 Excel <br/>
     *
     * head e.g.: [["headCol1"],["headCol2"],["headCol3"]] <br/>
     * data e.g.: [["dataCol11","dataCol12","dataCol13"],["dataCol21","dataCol22","dataCol23"]]
     *
     * @param file 目标文件
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param head 表头：外层为列，内层为行，内层元素个数必须相等
     * @param data 数据源集合：外层为行，内层为列
     * @param writeHandler 写处理器
     */
    public static void writeExcel(File file, Integer sheetNo, String sheetName, List<List<String>> head, List<List<String>> data, WriteHandler writeHandler) {
        EasyExcel.write(file)
                .head(head)
                .sheet(sheetNo, sheetName)
                .registerWriteHandler(writeHandler == null ? new DefaultStyle() {} : writeHandler)
                .doWrite(data);
    }

    /**
     * 写 Excel <br/>
     *
     * head e.g.: [["headCol1"],["headCol2"],["headCol3"]] <br/>
     * data e.g.: [["dataCol11","dataCol12","dataCol13"],["dataCol21","dataCol22","dataCol23"]]
     *
     * @param pathName 目标输出流
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param head 表头：外层为列，内层为行，内层元素个数必须相等
     * @param data 数据源集合：外层为行，内层为列
     */
    public static void writeExcel(String pathName, Integer sheetNo, String sheetName, List<List<String>> head, List<List<String>> data) {
        writeExcel(pathName, sheetNo, sheetName, head, data, null);
    }

    /**
     * 写 Excel <br/>
     *
     * head e.g.: [["headCol1"],["headCol2"],["headCol3"]] <br/>
     * data e.g.: [["dataCol11","dataCol12","dataCol13"],["dataCol21","dataCol22","dataCol23"]]
     *
     * @param pathName 目标输出流
     * @param sheetNo sheet 序号
     * @param sheetName sheet 名称
     * @param head 表头：外层为列，内层为行，内层元素个数必须相等
     * @param data 数据源集合：外层为行，内层为列
     * @param writeHandler 写处理器
     */
    public static void writeExcel(String pathName, Integer sheetNo, String sheetName, List<List<String>> head, List<List<String>> data, WriteHandler writeHandler) {
        EasyExcel.write(pathName)
                .head(head)
                .sheet(sheetNo, sheetName)
                .registerWriteHandler(writeHandler == null ? new DefaultStyle() {} : writeHandler)
                .doWrite(data);
    }
}