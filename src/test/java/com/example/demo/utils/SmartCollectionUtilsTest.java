package com.example.demo.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

class SmartCollectionUtilsTest {

    public static void main(String[] args) {
        // 初始化 集合
        List<String> list1 = Collections.emptyList();
        List<String> list2 = Collections.singletonList("1");
        List<String> list3 = Arrays.asList("1", "2");
        List<String> list4 = List.of("1", "2");
        // 允许修改元素
        List<String> list5 = Stream.of("1", "2").collect(Collectors.toList());

        Set<String> set1 = Collections.emptySet();
        Set<String> set2 = Collections.singleton("1");
        Set<String> set3 = Set.of("1", "2");
        // 允许修改元素
        Set<String> set4 = Stream.of("1", "2").collect(Collectors.toSet());

        // 初始化 Map
        Map<String,  String> map1 = Collections.emptyMap();
        Map<String,  String> map2 = Collections.singletonMap("1", "1");
        Map<String,  String> map3 = Map.of("1", "1", "2", "2");
        Map<String, String> map4 = Map.ofEntries(entry("1", "1"), entry("2", "2"));
        // 匿名内部类方式不推荐
        Map<String,  String> map5 = new HashMap<>() {
            {
                put("1", "1");
                put("2", "2");
            }
        };


        // 集合转数组
        String[] array = list2.toArray(new String[]{});

        // 数组转集合
        List<String> list6 = Arrays.stream(array).collect(Collectors.toList());

        // 集合转 Map

        // Map 转集合

        // 集合去重

        System.out.println();
    }

    public static void anonymousInnerClasses() {
        Map<String,  String> map = new HashMap<>() {
            {
                put("1", "2");
            }
        };
        System.out.println(map);
    }

    public void test() {
        anonymousInnerClasses();

    }
}