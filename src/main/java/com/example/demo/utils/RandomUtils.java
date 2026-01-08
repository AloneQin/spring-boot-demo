package com.example.demo.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 */
public class RandomUtils {

	/**
	 * 全字符数组
	 */
	private static final Character[] chars = new Character[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};

	/**
	 * 不包含大写的字符数组（避免输入频繁切换大小写）
	 */
	private static final Character[] lowerCaseChars = Arrays.stream(chars).filter(c -> !Character.isUpperCase(c)).toArray(Character[]::new);


	/**
	 * 生成指定长度的随机数
	 * @param length 随机数长度
	 * @param onlyLowerCase 是否只使用小写字母
	 * @return 随机数
	 */
	public static String getRandom(int length, boolean onlyLowerCase) {
		StringBuilder code = new StringBuilder();
		// 安全插件一键修复：已完成,原代码:new Random(),修改代码
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			if (onlyLowerCase) {
				code.append(lowerCaseChars[random.nextInt(lowerCaseChars.length)]);
			} else {
				code.append(chars[random.nextInt(chars.length)]);
			}
		}
		return code.toString();
	}

	/**
	 * 生成指定长度的随机数，多线程环境下使用
	 * @param length 随机数长度
	 * @param onlyLowerCase 是否只使用小写字母
	 * @return 随机数
	 */
	public static String getRandomConcurrent(int length, boolean onlyLowerCase) {
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (onlyLowerCase) {
				code.append(lowerCaseChars[ThreadLocalRandom.current().nextInt(lowerCaseChars.length)]);
			} else {
				code.append(chars[ThreadLocalRandom.current().nextInt(chars.length)]);
			}
		}
		return code.toString();
	}
	
	/**
	 * 生成指定长度的数字随机数
	 * @param length 随机数长度
	 * @return 随机数
	 */
	public static String getNumRandom(int length) {
		StringBuilder code = new StringBuilder();
		// 安全插件一键修复：已完成,原代码:new Random(),修改代码
		SecureRandom random = new SecureRandom();
		for(int i = 0; i < length; i++){
			code.append(random.nextInt(10));
		}
		return code.toString();
	}

	/**
	 * 生成指定长度的数字随机数，多线程环境下使用
	 * @param length 随机数长度
	 * @return 随机数
	 */
	public static String getNumRandomConcurrent(int length) {
		StringBuilder code = new StringBuilder();
		for(int i = 0; i < length; i++){
			code.append(ThreadLocalRandom.current().nextInt(10));
		}
		return code.toString();
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

	/**
	 * 生成16位的 UUID
	 * 去除'-'标记，并只取偶数位，此做法可能破坏唯一性，仅在对精密度要求不高的场景使用
	 * @return UUID
	 */
	public static String getUUID16() {
		StringBuilder builder = new StringBuilder();
		String uuid = getUUID(true);
		String[] arr = uuid.split("");
		for (int i = 0; i < arr.length; i = i + 2) {
			builder.append(arr[i]);
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			System.out.println(getNumRandom(6) + " " + getUUID(true));
		}
		System.out.println(FastjsonUtils.toString(lowerCaseChars));
		System.out.println(getUUID16());
	}
}
