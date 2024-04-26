package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Optional 测试类
 */
public class OptionalTest {

    public static void main(String[] args) {
        Person person = new Person();

        // 用法演示
        optionalTest(person);

        System.out.println(getFirstName(person));
        System.out.println(getFirstNameByOptional(person));

        person = new Person("三", "张", new Ability("特长", List.of(new Skill("java1", "java is good"))));

        System.out.println(getDescription(person));
        System.out.println(getDescriptionByOptional1(person));
        System.out.println(getDescriptionByOptional2(person));
        System.out.println(getDescriptionByOptional3(person));
    }

    private static void optionalTest(Person person) {
        // 为空直接报错
        Optional.of(person);

        // 存在则返回 optional，否则报错
        Optional<Person> optional = Optional.ofNullable(person);

        // 存在则返回 optional，否则由函数产生
        System.out.println(optional.or(() -> Optional.of(new Person())).get());

        // 存在则返回，否则返回默认
        System.out.println(optional.orElse(new Person()));

        // 存在则返回，否则由函数产生
        System.out.println(optional.orElseGet(Person::new));

        // 存在则返回，否则抛异常
        System.out.println(optional.orElseThrow());

        // 存在则返回，否则由函数产生异常
        System.out.println(optional.orElseThrow(() -> new RuntimeException("Null Point Exception")));

        // 存在则做
        optional.ifPresent(System.out::println);

        // 存在则做，否则做
        optional.ifPresentOrElse(System.out::println, () -> System.out.println("default"));

        // map 函数
        optional.map(Person::getFirstName).orElse("UNKNOWN");

        // flapMap 函数, 返回的是 optional, 处理判断嵌套
        optional.flatMap(p -> Optional.ofNullable(p.getFirstName())).orElse("UNKNOWN");

        // filter 函数
        optional.filter(p -> "".equals(p.getFirstName())).orElse(new Person());
    }

    /**
     * 多重连环校验
     * @param person
     * @return
     */
    private static String getFirstName(Person person) {
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
        return name;
    }

    /**
     * 用 optional 简化多重连环校验
     * @param person
     * @return
     */
    private static String getFirstNameByOptional(Person person) {
        return Optional.ofNullable(person)
                .map(Person::getFirstName)
                .map(String::toUpperCase)
                .orElse("UNKNOWN");
    }

    /**
     * 复杂的多重连环校验
     * @param person
     * @return
     */
    private static String getDescription(Person person) {
        if (person != null && person.getAbility() != null && person.getAbility().getSkills() != null) {
            List<Skill> skills = person.getAbility().getSkills();
            for (Skill skill : skills) {
                if (skill != null && "java".equals(skill.getName())) {
                    return skill.getDescription();
                }
            }
        }
        return "UNKNOWN";
    }

    /**
     * 用 optional 简化多重连环校验，中途指定默认值，写法较复杂，不推荐
     * @deprecated
     * @param person
     * @return
     */
    private static String getDescriptionByOptional1(Person person) {
        return Optional.ofNullable(person)
                .map(Person::getAbility)
                .map(Ability::getSkills)
                .map(skills -> skills.stream().filter(skill -> "java".equals(skill.getName())).findFirst())
                .map(o -> o.orElse(new Skill()))
                .map(Skill::getDescription)
                .orElse("UNKNOWN");
    }

    /**
     * 用 optional 简化多重连环校验，中途指定默认值，写法较复杂，不推荐
     * @deprecated
     * @param person
     * @return
     */
    private static String getDescriptionByOptional2(Person person) {
        return Optional.ofNullable(person)
                .map(Person::getAbility)
                .map(Ability::getSkills)
                .orElse(new ArrayList<>())
                .stream()
                .filter(skill -> "java".equals(skill.getName()))
                .findFirst()
                .map(Skill::getDescription)
                .orElse("UNKNOWN");
    }

    /**
     * 用 optional 简化多重连环校验，使用 flapMap 函数，写法简单，推荐使用
     * @param person
     * @return
     */
    private static String getDescriptionByOptional3(Person person) {
        return Optional.ofNullable(person)
                .map(Person::getAbility)
                .map(Ability::getSkills)
                .flatMap(skills -> skills.stream().filter(skill -> "java".equals(skill.getName())).findFirst())
                .map(Skill::getDescription)
                .orElse("UNKNOWN");
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Person {
    private String firstName;
    private String lastName;
    private Ability ability;
}

@Data
@AllArgsConstructor
class Ability {
    private String type;
    private List<Skill> skills;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Skill {
    private String name;
    private String description;
}