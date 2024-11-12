package com.example.demo.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 本地日期时间工具类
 */
public class LocalDateTimeUtils {

    /**
     * 默认时区偏移量：Asia/Shanghai
     */
    public static final String DEFAULT_OFFSET_ID = "+8";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String H_M_S = "HH:mm:ss";
    public static final String H_M_S_S = "HH:mm:ss.SSS";
    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Y_M_D_H_M_S_S_S = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    /**
     * 清除时间字符串中的 "T"、"Z" 字符
     * @param input 输入字符串
     * @return 返回字符串
     */
    public static String clean(String input) {
        return input.replaceAll("T", " ").replaceAll("Z", " ");
    }

    /**
     * {@link LocalDateTime} 转 {@link String}，默认格式 {@link LocalDateTimeUtils#Y_M_D_H_M_S}
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
        DateTimeFormatter formatter;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D_H_M_S);
        }
        return localDateTime.format(formatter);
    }

    /**
     * {@link String} 转 {@link LocalDateTime}，默认格式 {@link LocalDateTimeUtils#Y_M_D_H_M_S}
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
        DateTimeFormatter formatter;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D_H_M_S);
        }
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * {@link LocalDateTime} 转 时间戳
     * @param localDateTime 本地日期时间
     * @return 时间戳
     */
    public static Long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 毫秒时间戳 转 {@link LocalDateTime}
     * @param timestamp 时间戳
     * @return 本地日期时间
     */
    public static LocalDateTime timestamp2LocalDateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * {@link LocalDateTime} 转 毫秒时间戳
     * @param localDateTime 本地日期时间
     * @return 毫秒时间戳
     */
    public static Long LocalDateTime2timestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of(DEFAULT_OFFSET_ID)).toEpochMilli();
    }

    /**
     * 毫秒时间戳 转 {@link LocalDate}
     * @param timestamp 时间戳
     * @return 本地日期
     */
    public static LocalDate timestamp2LocalDate(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * {@link LocalDate} 转 毫秒时间戳
     * @param localDate 本地日期
     * @return 毫秒时间戳
     */
    public static Long LocalDate2timestamp(LocalDate localDate) {
        return LocalDateTime2timestamp(localDate.atStartOfDay());
    }

    /**
     * {@link LocalDateTime} 转 {@link Date}
     * @param localDateTime 本地日期时间
     * @return 日期
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@link LocalDate} 转 {@link Date}
     * @param localDate 本地日期
     * @return 日期
     */
    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@link Date} 转 {@link LocalDateTime}
     * @param date 日期
     * @return 本地日期时间
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * {@link Date} 转 {@link LocalDate}
     * @param date 日期
     * @return 本地日期
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * {@link Date} 转 {@link LocalTime}
     * @param date 日期
     * @return 本地时间
     */
    public static LocalTime date2LocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
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
        DateTimeFormatter formatter;
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
    public static LocalDate str2LocalDate(String dateStr) {
        return str2LocalDate(dateStr, null);
    }

    /**
     * {@link String} 转 {@link LocalDate}
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 本地日期
     */
    public static LocalDate str2LocalDate(String dateStr, String pattern) {
        DateTimeFormatter formatter;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(Y_M_D);
        }
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * {@link LocalTime} 转 {@link String}，默认格式 {@link LocalDateTimeUtils#H_M_S}
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
        DateTimeFormatter formatter;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(H_M_S);
        }
        return localTime.format(formatter);
    }

    /**
     * {@link String} 转 {@link LocalTime}，默认格式 {@link LocalDateTimeUtils#H_M_S}
     * @param dateStr 时间字符串
     * @return 本地时间
     */
    public static LocalTime str2LocalTime(String dateStr) {
        return str2LocalTime(dateStr, null);
    }

    /**
     * {@link String} 转 {@link LocalTime}
     * @param dateStr 时间字符串
     * @param pattern 时间格式
     * @return 本地时间
     */
    public static LocalTime str2LocalTime(String dateStr, String pattern) {
        DateTimeFormatter formatter;
        if (Objects.nonNull(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(H_M_S);
        }
        return LocalTime.parse(dateStr, formatter);
    }

    /**
     * 获取两个日期之间的日期天数
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期天数集合
     */
    public static List<String> getDaysBetween(LocalDate start, LocalDate end) {
        long days = ChronoUnit.DAYS.between(start, end);
        List<String> dayList = new ArrayList<>((int) days);
        for (int i = 0; i <= days; i++) {
            LocalDate localDate = start.plusDays(i);
            dayList.add(localDate.format(DateTimeFormatter.ISO_DATE));
        }
        return dayList;
    }

    public static void main(String[] args) {
        System.out.println(date2LocalDate(new Date()));
        System.out.println(localDate2Date(LocalDate.now()));
        LocalDateTime now = LocalDateTime.now();
        System.out.println(LocalDateTime2timestamp(LocalDateTime.now()));
        System.out.println(localDateTime2Date(now).getTime());
        getDaysBetween(LocalDate.now(), LocalDate.now().plusDays(5)).forEach(System.out::println);
    }
}
