package com.example.demo.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * 时间工具类
 */
public class LocalDateTimeUtils {

    public static final String Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 清除时间字符串中的 "T"、"Z" 字符
     * @param input 输入字符串
     * @return 返回字符串
     */
    public static String clean(String input) {
        return input.replaceAll("T", " ").replaceAll("Z", " ");
    }

    /**
     * {@link LocalDateTime} 转 {@link String}，默认格式 {@link LocalDateTimeUtils#Y_M_D_H_M_S_S}
     * @param localDateTime java8日期
     * @return 日期字符串
     */
    public static String localDateTime2Str(LocalDateTime localDateTime) {
        return localDateTime2Str(localDateTime, null);
    }

    /**
     * {@link LocalDateTime} 转 {@link String}
     * @param localDateTime java8日期
     * @param pattern 日期格式
     * @return
     */
    public static String localDateTime2Str(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = null;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D_H_M_S_S);
        }
        return localDateTime.format(formatter);
    }

    /**
     * {@link String} 转 {@link LocalDateTime}，默认格式 {@link LocalDateTimeUtils#Y_M_D_H_M_S_S}
     * @param dateStr 日期字符串
     * @return java8日期
     */
    public static LocalDateTime str2LocalDateTime(String dateStr) {
        return str2LocalDateTime(dateStr, null);
    }

    /**
     * {@link String} 转 {@link LocalDateTime}
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return java8日期
     */
    public static LocalDateTime str2LocalDateTime(String dateStr, String pattern) {
        DateTimeFormatter formatter = null;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D_H_M_S_S);
        }
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * {@link LocalDateTime} 转 时间戳
     * @param localDateTime java8日期
     * @return 时间戳
     */
    public static Long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间戳 转 {@link LocalDateTime}
     * @param timestamp 时间戳
     * @return java8日期
     */
    public static LocalDateTime timestamp2LocalDateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
    }

    /**
     * {@link LocalDateTime} 转 {@link Date}
     * @param localDateTime java8日期
     * @return 日期
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
    }

    /**
     * {@link Date} 转 {@link LocalDateTime}
     * @param date 日期
     * @return java8日期
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDateTime();
    }
}
