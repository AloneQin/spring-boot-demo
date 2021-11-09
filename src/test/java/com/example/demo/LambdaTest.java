package com.example.demo;

/**
 * lambda 测试类
 */
public class LambdaTest {

    public static void main(String[] args) {
        // 匿名内部类
        Fun fun1 = new Fun() {
            @Override
            public void doSomething(String s) {
                System.out.println(s);
            }
        };
        fun1.doSomething("Hello World!");

        // 实现类
        Fun fun2 = new FunImpl();
        fun2.doSomething("Hello World!");

        // lambda
        Fun fun3 = (s) -> System.out.println(s);
        fun3.doSomething("Hello World!");
    }

    public static void doSomething(String s) {
        System.out.println(s);
    }
}

@FunctionalInterface
interface Fun {
   void doSomething(String s);
}
class FunImpl implements Fun {
    @Override
    public void doSomething(String s) {
        System.out.println(s);
    }
}
