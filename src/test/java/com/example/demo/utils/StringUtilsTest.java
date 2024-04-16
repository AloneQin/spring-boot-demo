package com.example.demo.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void padding() {
        String s = "{}你好，你的账号为{}，祝您工作愉快！";
        System.out.println(StringUtils.padding(s));
        System.out.println(StringUtils.padding(s, "123"));
        System.out.println(StringUtils.padding(s, "123", "456"));
        System.out.println(StringUtils.padding(s, "123", "456", "789"));
    }
}