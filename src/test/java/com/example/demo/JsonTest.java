package com.example.demo;

import com.example.demo.model.po.PhonePO;
import com.example.demo.model.vo.DemoVO;
import com.example.demo.utils.FastjsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonTest {

    public static void main(String[] args) {
        String s = "{\"id\":1,\"age\":10,\"name\":\"tom\"}";
        A a = FastjsonUtils.toObject(s, A.class);
        System.out.println(a);
        B b = FastjsonUtils.toObject(s, B.class);
        System.out.println(b);
        C c = FastjsonUtils.toObject(s, C.class);
        System.out.println(c);
        D d = FastjsonUtils.toObject(s, D.class);
        System.out.println(d);

        String  k = "123456";
        k = k.substring(0, k.length() - 1);
        System.out.println(k);

        B b1 = new B();
        b1.setId(2);
        b1.setName("jack");
        System.out.println(FastjsonUtils.toString(b1));

        List<A> list = new ArrayList<>();
        int sum = list.stream().mapToInt(A::getId).sum();
        System.out.println(sum);

        String str = "aaaa[1]bbbb";
        System.out.println(str.substring(str.indexOf("[") + 1, str.indexOf("]")));

        List<DemoVO> list1 = Arrays.asList(new DemoVO().setF1("f1"), new DemoVO().setF1("f1"));
        list1.stream().map(e -> e.setF1("fffff"));
        list1.forEach(System.out::println);


        list1 = list1.stream().peek(e -> e.setF2("f2")).collect(Collectors.toList());
        list1.forEach(System.out::println);

        String sss = "a";
        System.out.println(Arrays.toString(sss.split("\\|\\|")));

        Date date = new Date();

        System.out.println(date.after(date));

        list1.forEach(JsonTest::test);

        list1.forEach(System.out::println);

        List<DemoVO> list2 = Arrays.asList(
                new DemoVO().setF1("1").setF2("0").setF3(1),
                new DemoVO().setF1("2").setF2("0").setF3(1),
                new DemoVO().setF1("3").setF2("1").setF3(2),
                new DemoVO().setF1("4").setF2("1").setF3(2),
                new DemoVO().setF1("5").setF2("3").setF3(3),
                new DemoVO().setF1("6").setF2("5").setF3(4)
        );

        List<DemoVO> list3 = new ArrayList<>();
        recursion(new DemoVO().setF1("1").setF2("0").setF3(1), list2, list3);
        System.out.println();
    }

    private static void recursion(DemoVO demoVO, List<DemoVO> list1,  List<DemoVO> list2) {
        for (int i = demoVO.getF3() + 1; i < 4; i++) {
            List<DemoVO> children = findChildren(demoVO.getF2(), list1);
            list2.addAll(children);
            children.forEach(e -> recursion(e, list1, list2));
        }
    }

     private static List<DemoVO> findChildren(String f2, List<DemoVO> list) {
        return list.stream().filter(e -> f2.equals(e.getF2())).collect(Collectors.toList());
    }

    private static void test(DemoVO demoVO) {
        demoVO.setF1("xxxxxxx");
    }

}

@Data
class A {
    protected int id;
}

@Data
@EqualsAndHashCode(callSuper = true)
class B extends A {
    private String name;
}

@Data
@EqualsAndHashCode(callSuper = true)
class C extends A {
    private int age;
}

@Data
@EqualsAndHashCode(callSuper = true)
class D extends A {
    private String name;
    private int age;
}