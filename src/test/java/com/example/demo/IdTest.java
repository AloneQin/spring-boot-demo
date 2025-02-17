package com.example.demo;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;

public class IdTest {

    public static void main(String[] args) {
        DefaultIdentifierGenerator generator = new DefaultIdentifierGenerator();
        // 19位数值
        Long id1 = generator.nextId(null);
        Long id2 = generator.nextId(null);
        Long id3 = generator.nextId(null);

        System.out.println(id1);
        System.out.println(id2);
        System.out.println(id3);

        System.out.println(id1.compareTo(id2));
        System.out.println(id2.compareTo(id3));
        System.out.println(id1.compareTo(id3));

        // 纳秒：16位长度
        long l = System.nanoTime();
        System.out.println(l);
        System.out.println(String.valueOf(l).length());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());

        try {

        } finally {

        }
    }
}
