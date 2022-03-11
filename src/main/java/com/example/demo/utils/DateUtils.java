package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

	public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
	public static final String Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String E_M_D_H_M_S_Z_Y= "EEE MMM dd HH:mm:ss zzz yyyy";

	/**
	 * 日期转字符串
	 * @param date 	需要转换的日期 {@link java.util.Date}
	 * @return 		字符串格式 {@link java.lang.String}
	 */
	public static String date2Str(Date date) {
		return date2Str(date, Y_M_D_H_M_S);
	}

	/**
	 * 日期转字符串
	 * @param date		需要转换的日期 {@link java.util.Date}
	 * @param pattern	转换格式 like: yyyy-MM-dd HH:mm:ss.SSS
	 * @return			字符串格式 {@link java.lang.String}
	 */
	public static String date2Str(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 字符串转日期
	 * @param date 	需要转换的字符串 {@link java.lang.String}
	 * @return 		日期格式 {@link java.util.Date}
	 * @throws ParseException
	 */
	public static Date str2Date(String date) throws ParseException {
		return str2Date(date, Y_M_D_H_M_S);
	}

	/**
	 * 字符串转日期
	 * @param date		需要转换的字符串 {@link java.lang.String}
	 * @param pattern	转换格式 like: yyyy-MM-dd HH:mm:ss.SSS
	 * @return			日期格式 {@link java.util.Date}
	 * @throws ParseException 
	 */
	public static Date str2Date(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	public static void main(String[] args) throws ParseException {
		String str = date2Str(new Date(), Y_M_D_H_M_S_S);
		System.out.println("date2Str: " + str);
		System.out.println("str2Date: " + str2Date(str, Y_M_D_H_M_S_S));
	}
}
