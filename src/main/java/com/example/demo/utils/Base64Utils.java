package com.example.demo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * base64 编码解码工具类
 * @see Base64
 */
public class Base64Utils {

    private static final String CHARSET = "UTF-8";

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 编码
     * @param text 字符串
     * @return base64 编码后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String encode(String text) throws UnsupportedEncodingException {
        return encoder.encodeToString(text.getBytes(CHARSET));
    }

    /**
     * 解码
     * @param base64Text base64 编码的字符串
     * @return 字符串
     * @throws UnsupportedEncodingException
     */
    public static String decode(String base64Text) throws UnsupportedEncodingException {
        return new String(decoder.decode(base64Text), CHARSET);
    }
}
