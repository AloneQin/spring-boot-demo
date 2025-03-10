package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class FastjsonUtilsTest {

    public static void main(String[] args) {
        System.out.println("8d86aa34196990a".length());
        System.out.println("624e25894487b972".length());


//        // 对象转字符串
//        List<Book> bookList = Arrays.asList(
//                new Book("A", 10.00, null),
//                new Book("B", 8.00, "")
//        );
//        String s = FastjsonUtils.toString(bookList);
//        System.out.println("s: " + s);
//
//        // 对象转字符串并格式化
//        String s2 = FastjsonUtils.toStringFormat(bookList);
//        System.out.println("s2: " + s2);
//
//        // 对象转字符串保留为 null 的属性
//        String s3 = FastjsonUtils.toStringKeepNull(bookList);
//        System.out.println("s3: " + s3);
//
//        // 字符串转对象
//        String s4 = "{\"name\":\"A\",\"price\":10.0,\"size\":1000}";
//        Book book = FastjsonUtils.toObject(s4, Book.class);
//        System.out.println(book);
//
//        // 字符串转数组
//        String s5 = "[{\"name\":\"A\",\"price\":10.0,\"size\":1000},{\"name\":\"B\",\"price\":8.0,\"size\":800}]";
//        List<Book> bookList2 = FastjsonUtils.toArray(s5, Book.class);
//        System.out.println(bookList2);
//
//        List<Map<String, Object>> list = FastjsonUtils.toObject(s5, new TypeReference<>() {});
//        System.out.println(list);
//
//        // 字符串转复杂对象
//        String s6 = "{\"a\":[{\"name\":\"A\",\"price\":10.0,\"press\":null},{\"name\":\"B\",\"price\":8.0,\"press\":\"\"}]}";
//        Map<String, List<Book>> map = FastjsonUtils.toObject(s6, new TypeReference<>() {});
//        System.out.println(map);
//
//        String string = FastjsonUtils.toString(null);
//        System.out.println(string);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Book {

    private String name;

    private Double price;

    private String press;
}