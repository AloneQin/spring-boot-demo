package com.example.demo.utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 */
public class RandomUtils {

	private static Random random = new Random();

	private static String[] chars = new String[] {
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
	};
	
	/**
	 * 生成指定长度的随机数
	 * @param length 随机数长度
	 * @return 随机数
	 */
	public static String getRandom(int length) {
		String code = "";
		for (int i = 0; i < length; i++) {
			code += chars[random.nextInt(chars.length)];
		}
		return code;
	}

	/**
	 * 生成指定长度的随机数，多线程环境下使用
	 * @param length 随机数长度
	 * @return 随机数
	 */
	public static String getRandomConcurrent(int length) {
		String code = "";
		for (int i = 0; i < length; i++) {
			code += chars[random.nextInt(chars.length)];
		}
		return code;
	}
	
	/**
	 * 生成指定长度的数字随机数
	 * @param length 随机数长度
	 * @return 随机数
	 */
	public static String getNumRandom(int length) {
		String code = "";
		for(int i = 0; i < length; i++){
			code += ThreadLocalRandom.current().nextInt(10);
		}
		return code;
	}

	/**
	 * 生成指定长度的数字随机数，多线程环境下使用
	 * @param length 随机数长度
	 * @return 随机数
	 */
	public static String getNumRandomConcurrent(int length) {
		String code = "";
		for(int i = 0; i < length; i++){
			code += ThreadLocalRandom.current().nextInt(10);
		}
		return code;
	}
	
	/**
	 * 获取唯一的可辨识资讯 UUID
	 * UUID 为 128 位二进制，每 4 位二进制可转换为 16 进制，其添加了四个'-'，故总长为 36 位
	 * @param rmTag 是否删除'-'标记
	 * @return UUID
	 */
	public static String getUUID(boolean rmTag) {
		String uuid = UUID.randomUUID().toString();
		if (rmTag) {
			uuid = uuid.replace("-", "");
		}
		return uuid;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			System.out.println(getNumRandom(6) + " " + getUUID(false));
		}
	}
}
