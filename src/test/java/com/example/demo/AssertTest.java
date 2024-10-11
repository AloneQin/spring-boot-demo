package com.example.demo;


import com.example.demo.utils.RandomUtils;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;

import java.util.Map;

public class AssertTest {

    public static void main(String[] args) {
//        Assert.assertFalse("不对", 1 > 2);


//        Assert.notNull(null, () -> "空指针");

//        Assert.notNull(null, "空指针");


        Obj obj = new Obj(100);
        Map<String, Obj> map = Map.of("1", obj);

        System.out.println(RandomUtils.getUUID(true));
    }

    public static void asserTest() {
        assert true;
        System.out.println("正常执行");

        assert false : "参数不合法";
        System.out.println("不会执行");
    }


    @AllArgsConstructor
    static class Obj {
        int id;
    }
}
