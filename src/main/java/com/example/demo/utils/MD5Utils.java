package com.example.demo.utils;

import lombok.Cleanup;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.security.MessageDigest;

/**
 * md5 工具类
 */
public class MD5Utils {

	private static final char[] hexDigits = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	/**
	 * 获取字符串的 md5 值
	 * @param s 字符串
	 * @return md5
	 * @throws Exception 异常
	 */
	public static String md5(String s) throws Exception {
		byte[] strTemp = s.getBytes();
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		char str[] = new char[md.length * 2];
		int k = 0;
        for (byte b : md) {
            str[k++] = hexDigits[b >>> 4 & 0xf];
            str[k++] = hexDigits[b & 0xf];
        }
		return new String(str);
	}

	/**
	 * 获取文件的 md5 值
	 * @param file 待检验文件
	 * @return md5
	 * @throws Exception 异常
	 */
	public static String md5OfFile(File file) throws Exception {
		@Cleanup FileInputStream fis = new FileInputStream(file);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer, 0, 1024)) != -1) {
			md.update(buffer, 0, length);
		}
		return bufferToHex(md.digest());
	}

	/**
	 * 获取文件的 md5 值
	 * @param path 文件路径
	 * @return md5
	 */
	public static String md5OfFile(String path) {
        try {
            return DigestUtils.md5Hex(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("get file md5 error", e);
        }
    }


	/**
	 * 获取输入流的 md5 值
	 * @param inputStream 输入流
	 * @return md5
	 */
	public static String md5OfInputStream(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("get file md5 error", e);
		}
	}
	
	/**
	 * 字节数组转换16进制字符串
	 */
	public static String bufferToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(hexDigits[(aByte & 0xf0) >>> 4]);
            sb.append(hexDigits[aByte & 0x0f]);
        }
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(md5("hello, jay"));
		// 3f3b8b4daa2017adb92d7993a7adc320
		System.out.println(md5OfFile(new File("D:/work/dev/test/test2-1.pptx")));
	}
}
