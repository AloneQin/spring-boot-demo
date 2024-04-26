package com.example.demo;


import org.springframework.util.Assert;

public class AssertTest {

    public static void main(String[] args) {
//        Assert.assertFalse("不对", 1 > 2);


//        Assert.notNull(null, () -> "空指针");

        Assert.notNull(null, "空指针");
    }

    public static void asserTest() {
        assert true;
        System.out.println("正常执行");

        assert false : "参数不合法";
        System.out.println("不会执行");
    }
}
