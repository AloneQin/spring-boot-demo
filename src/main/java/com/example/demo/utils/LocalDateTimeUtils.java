package com.example.demo.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * 本地日期时间工具类
 */
public class LocalDateTimeUtils {

    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String H_M_S_S = "HH:mm:ss.SSS";
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
     * @param localDateTime 本地日期时间
     * @return 日期时间字符串
     */
    public static String localDateTime2Str(LocalDateTime localDateTime) {
        return localDateTime2Str(localDateTime, null);
    }

    /**
     * {@link LocalDateTime} 转 {@link String}
     * @param localDateTime 本地日期时间
     * @param pattern 日期时间格式
     * @return 日期时间字符串
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
     * @param dateStr 日期时间字符串
     * @return 本地日期时间
     */
    public static LocalDateTime str2LocalDateTime(String dateStr) {
        return str2LocalDateTime(dateStr, null);
    }

    /**
     * {@link String} 转 {@link LocalDateTime}
     * @param dateStr 日期时间字符串
     * @param pattern 日期时间格式
     * @return 本地日期时间
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
     * @param localDateTime 本地日期时间
     * @return 时间戳
     */
    public static Long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间戳 转 {@link LocalDateTime}
     * @param timestamp 时间戳
     * @return 本地日期时间
     */
    public static LocalDateTime timestamp2LocalDateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
    }

    /**
     * {@link LocalDateTime} 转 {@link Date}
     * @param localDateTime 本地日期时间
     * @return 日期
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
    }

    /**
     * {@link Date} 转 {@link LocalDateTime}
     * @param date 日期
     * @return 本地日期时间
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDateTime();
    }

    /**
     * {@link LocalDate} 转 {@link String}，默认格式 {@link LocalDateTimeUtils#Y_M_D}
     * @param localDate 本地日期
     * @return 日期字符串
     */
    public static String localDate2Str(LocalDate localDate) {
        return localDate2Str(localDate, null);
    }

    /**
     * {@link LocalDate} 转 {@link String}
     * @param localDate 本地日期
     * @param pattern 日期时间格式
     * @return 日期字符串
     */
    public static String localDate2Str(LocalDate localDate, String pattern) {
        DateTimeFormatter formatter = null;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D);
        }
        return localDate.format(formatter);
    }

    /**
     * {@link String} 转 {@link LocalDate}，默认格式 {@link LocalDateTimeUtils#Y_M_D}
     * @param dateStr 日期字符串
     * @return 本地日期
     */
    public static LocalDate str2localDate(String dateStr) {
        return str2localDate(dateStr);
    }

    /**
     * {@link String} 转 {@link LocalDate}
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 本地日期
     */
    public static LocalDate str2localDate(String dateStr, String pattern) {
        DateTimeFormatter formatter = null;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D);
        }
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * {@link LocalTime} 转 {@link String}，默认格式 {@link LocalDateTimeUtils#H_M_S_S}
     * @param localTime 本地时间
     * @return 时间字符串
     */
    public static String localTime2Str(LocalTime localTime) {
        return localTime2Str(localTime, null);
    }

    /**
     * {@link LocalTime} 转 {@link String}
     * @param localTime 本地时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String localTime2Str(LocalTime localTime, String pattern) {
        DateTimeFormatter formatter = null;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(H_M_S_S);
        }
        return localTime.format(formatter);
    }

    /**
     * {@link String} 转 {@link LocalTime}，默认格式 {@link LocalDateTimeUtils#H_M_S_S}
     * @param dateStr 时间字符串
     * @return 本地时间
     */
    public static LocalTime str2localTime(String dateStr) {
        return str2localTime(dateStr, null);
    }

    /**
     * {@link String} 转 {@link LocalTime}
     * @param dateStr 时间字符串
     * @param pattern 时间格式
     * @return 本地时间
     */
    public static LocalTime str2localTime(String dateStr, String pattern) {
        DateTimeFormatter formatter = null;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(H_M_S_S);
        }
        return LocalTime.parse(dateStr, formatter);
    }
}
