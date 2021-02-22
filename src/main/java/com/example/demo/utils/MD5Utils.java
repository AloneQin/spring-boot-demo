package com.example.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;

/**
 * md5 工具类
 */
public class MD5Utils {

	private static char hexDigits[] = { 
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	/**
	 * 获取字符串的 md5 值
	 * @param s 字符串
	 * @return md5
	 * @throws Exception
	 */
	public static String md5(String s) throws Exception {
		byte[] strTemp = s.getBytes();
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		
		return new String(str);
	}

	/**
	 * 获取文件的 md5 值
	 * @param file 待检验文件
	 * @return md5
	 * @throws Exception
	 */
	public static String md5(File file) throws Exception {
		String md5;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = fis.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, length);
			}

			md5 = bufferToHex(md.digest());
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		
		return md5;
	}
	
	/**
	 * 字节数组转换16进制字符串
	 */
	public static String bufferToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexDigits[(bytes[i] & 0xf0) >>> 4]);
			sb.append(hexDigits[bytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(md5("hello, jay"));
		System.out.println(md5(new File("/Users/alone/work/test/1.png")));
	}
}
