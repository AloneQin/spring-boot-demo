package com.example.demo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用测试类
 */
public class MethodReferenceTest {

    public static void main(String[] args) {
        List<Student> studentList = Arrays.asList(
                new Student("tom", 15),
                new Student("jack", 17)
        );

        /**
         * 方法引用
         */
        // Class::staticMethod
        studentList.stream().sorted((s1, s2) -> Student.compareAge(s1, s2)).forEach(System.out::println);
        studentList.stream().sorted(Student::compareAge).forEach(System.out::println);
        System.out.println("------------");

        // Class::nonStaticMethod
        studentList.stream().filter(s -> s.checkName()).forEach(System.out::println);
        studentList.stream().filter(Student::checkName).forEach(System.out::println);
        System.out.println("------------");

        // Object::methodName
        Student student = new Student();
        studentList.stream().sorted((s1, s2) -> student.compareName(s1, s2)).forEach(System.out::println);
        studentList.stream().sorted(student::compareName).forEach(System.out::println);
        System.out.println("------------");

        // Class::new
        Student student1 = new Student("tony" ,16);
        System.out.println(student1);

        BiFunction<String, Integer, Student> biFunction1 = (name, age) ->new Student(name, age);
        Student student2 = biFunction1.apply("tony" ,16);
        System.out.println(student2);

        BiFunction<String, Integer, Student> biFunction2 = Student::new;
        Student student3 = biFunction2.apply("tony" ,16);
        System.out.println(student3);

    }

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class Student {

    private String name;
    private Integer age;

    public static int compareAge(Student student1, Student student2) {
        return student1.getAge() - student1.getAge();
    }

    public boolean checkName() {
        return name.startsWith("j");
    }

    public int compareName(Student student1, Student student2) {
        return student1.getName().length() - student2.getName().length();
    }

}