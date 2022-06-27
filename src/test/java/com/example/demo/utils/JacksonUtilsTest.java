package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class JacksonUtilsTest {

    public static void main(String[] args) throws JsonProcessingException {
        // 对象转字符串
        List<Book> bookList = Arrays.asList(
                new Book("A", 10.00, null),
                new Book("B", 8.00, "")
        );
        String s = JacksonUtils.toString(bookList);
        System.out.println("s: " + s);

        // 对象转字符串并格式化
        String s2 = JacksonUtils.toStringFormat(bookList);
        System.out.println("s2: " + s2);

        // 对象转字符串保留为 null 的属性
        String s3 = JacksonUtils.toStringExistNull(bookList);
        System.out.println("s3: " + s3);

        // 字符串转对象
        String s4 = "{\"name\":\"A\",\"price\":10.0,\"size\":1000}";
        Book book = JacksonUtils.toObject(s4, Book.class);
        System.out.println(book);

        // 字符串转数组
        String s5 = "[{\"name\":\"A\",\"price\":10.0,\"size\":1000},{\"name\":\"B\",\"price\":8.0,\"size\":800}]";
        List<Book> bookList2 = JacksonUtils.toObject(s5, new TypeReference<>() {});
        System.out.println(bookList2);

        // 字符串转复杂对象
        String s6 = "{\"a\":[{\"name\":\"A\",\"price\":10.0,\"press\":null},{\"name\":\"B\",\"price\":8.0,\"press\":\"\"}]}";
        Map<String, List<Book>> map = JacksonUtils.toObject(s6, new TypeReference<>() {});
        System.out.println(map);
    }

}