package com.example.demo.utils;

import org.junit.jupiter.api.Test;

class SmartStringUtilsTest {

    @Test
    void padding() {
        String s = "{}你好，你的账号为{}，祝您工作愉快！";
        System.out.println(SmartStringUtils.format(s));
        System.out.println(SmartStringUtils.format(s, "123"));
        System.out.println(SmartStringUtils.format(s, "123", "456"));
        System.out.println(SmartStringUtils.format(s, "123", "456", "789"));
    }
}