package com.example.demo;

import lombok.Data;

import java.util.Optional;

/**
 * Optional 测试类
 */
public class OptionalTest {

    public static void main(String[] args) {
        Person person = new Person();
        Optional<Person> personOpt = Optional.ofNullable(person);

        // 存在则做
        if (person != null) {
            System.out.println(person);
        }
        personOpt.ifPresent(System.out::println);

        // 存在则返回，否则返回默认
        Person returnObj = null;
        if (person != null) {
            returnObj = person;
        } else {
            returnObj = new Person();
        }
        returnObj = personOpt.orElse(new Person());

        // 存在则返回，否则由函数产生
        if (person != null) {
            returnObj = person;
        } else {
            returnObj = getDefault();
        }
        returnObj = personOpt.orElseGet(OptionalTest::getDefault);

        // 多重连环校验
        String name = null;
        if (person != null) {
            String firstName = person.getFirstName();
            if (firstName != null) {
                name = firstName.toUpperCase();
            } else {
                name = "UNKNOWN";
            }
        } else {
            name = "UNKNOWN";
        }
        name = personOpt
                .map(Person::getFirstName)
                .map(String::toUpperCase)
                .orElse("UNKNOWN");
    }

    public static Person getDefault() {
        return new Person();
    }
}

@Data
class Person {
    private String firstName;
    private String lastName;
}