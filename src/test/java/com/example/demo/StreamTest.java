package com.example.demo;

import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartCollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<User> list = Arrays.asList(
                new User(1, "aaa", "root"),
                new User(2, "abc", "normal"),
                new User(3, "bbb", "root"),
                new User(4, "abb", "normal"),
                new User(5, "acc", "root")
        );

        forEach(list);
        filter(list);
        anyMatch(list);
        allMatch(list);
        sorted(list);
        map(list);
        flatMap(list);
        Set<User> userSet = listToSet(list);
        Map<Integer, User> userMap = listToMap(list);
        mapToList(userMap);
        mapToSet(userMap);
        setToList(userSet);
        setToMap(userSet);
        groupBy(list);
    }

    public static void forEach(List<User> list) {
        list.forEach(user -> System.out.println("forEach: " + FastjsonUtils.toString(user)));
    }

    public static void filter(List<User> list) {
        List<User> userList = list.stream()
                .filter(user -> "root".equals(user.getType()))
                .collect(Collectors.toList());
        System.out.println("filter: " + FastjsonUtils.toString(userList));
    }

    public static void anyMatch(List<User> list) {
        boolean anyMatch = list.stream().anyMatch(user -> "bbb".equals(user.getName()));
        System.out.println("anyMatch: " + anyMatch);
    }

    public static void allMatch(List<User> list) {
        boolean allMatch = list.stream().allMatch(user -> "root".equals(user.getName()));
        System.out.println("allMatch: " + allMatch);
    }

    public static void sorted(List<User> list) {
        List<User> userList = list.stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .collect(Collectors.toList());
        System.out.println("sorted: " + FastjsonUtils.toString(userList));
    }

    public static void map(List<User> list) {
        List<String> nameList = list.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        System.out.println("map: " + FastjsonUtils.toString(nameList));
    }

    public static void flatMap(List<User> list) {
        List<String> nameCharList = list.stream()
                .flatMap(user -> Arrays.stream(user.getName().split("")))
                .collect(Collectors.toList());
        System.out.println("flatMap: " + FastjsonUtils.toString(nameCharList));
    }

    public static Set<User> listToSet(List<User> list) {
        Set<User> userSet = list.stream().collect(Collectors.toSet());
        System.out.println("listToSet: " + FastjsonUtils.toString(userSet));
        return userSet;
    }

    public static Map<Integer, User> listToMap(List<User> list) {
        Map<Integer, User> userMap = list.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        System.out.println("listToMap: " + FastjsonUtils.toString(userMap));
        return userMap;
    }

    public static void mapToList(Map<Integer, User> userMap) {
        List<User> userList = userMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        System.out.println("mapToList: " + FastjsonUtils.toString(userList));
    }

    public static void mapToSet(Map<Integer, User> userMap) {
        Set<User> userSet = userMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
        System.out.println("mapToSet: " + FastjsonUtils.toString(userSet));
    }

    public static void setToList(Set<User> userSet) {
        List<User> userList = userSet.stream().collect(Collectors.toList());
        System.out.println("setToList: " + FastjsonUtils.toString(userList));
    }

    public static void setToMap(Set<User> userSet) {
        Map<Integer, User> userMap = userSet.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        System.out.println("setToMap: " + FastjsonUtils.toString(userMap));
    }

    public static void groupBy(List<User> list) {
        Map<String, List<User>> typeMap = list.stream().collect(Collectors.groupingBy(User::getType));
        System.out.println("groupBy: " + FastjsonUtils.toString(typeMap));
    }
}

@Data
@AllArgsConstructor
class User {
    private int id;
    private String name;
    private String type;
}