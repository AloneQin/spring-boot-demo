package com.example.demo.utils;

import java.util.Objects;

/**
 * 十六进制字符串工具类
 */
public class HexStringUtils {

    /**
     * 字节数组转十六进制字符串
     * @param buff 字节数组
     * @return 十六进制字符串
     */
    private static String byte2HexStr(byte[] buff) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buff) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制转字节数组
     * @param hexStr 十六进制字符串
     * @return 字节数组
     */
    private static byte[] hexStr2Byte(String hexStr) {
        if (hexStr == null || hexStr.isEmpty()) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = ((byte)(high * 16 + low));
        }
        return result;
    }

    /**
     * 字符串转十六进制字符串
     * @param str 字符串
     * @return 十六进制字符串
     */
    private static String strToHexStr(String str) {
        return byte2HexStr(str.getBytes());
    }

    /**
     * 十六进制字符串转字符串
     * @param hexStr 十六进制字符串
     * @return 字符串
     */
    private static String hexStrToStr(String hexStr) {
        return new String(Objects.requireNonNull(hexStr2Byte(hexStr)));
    }

    public static void main(String[] args) {
        String str = "我于杀戮之中绽放，亦如黎明中的花朵";
        String hexStr = strToHexStr(str);
        str = hexStrToStr(hexStr);
        System.out.println("十六进制字符串：" + hexStr);
        System.out.println("原字符串：" + str);
    }
}
