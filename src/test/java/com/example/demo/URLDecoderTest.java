package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class URLDecoderTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "%5B%7B%22fieldName%22%3A%221780125223320948736%22%2C%22queryType%22%3A4%2C%22queryCondition%22%3A1%7D%5D";
        String decode = URLDecoder.decode(url, StandardCharsets.UTF_8);
        System.out.println(decode);
        decode = URLDecoder.decode(decode, StandardCharsets.UTF_8);
        System.out.println(decode);

        decode = URLDecoder.decode(url, "ISO-8859-1");
        System.out.println(decode);
    }
}
