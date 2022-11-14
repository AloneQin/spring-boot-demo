package com.example.demo;

public class IntTest {

    public static void main(String[] args) {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        // false
        System.out.println(i1 == i2);

        int i3 = 1;
        Integer i4 = 1;
        Integer i5 = 1;
        // true
        System.out.println(i3 == i4);
        // true
        System.out.println(i4 == i5);
        // false
        System.out.println(i1 == i4);

        int i6 = 128;
        Integer i7 = 128;
        Integer i8 = 128;
        // true
        System.out.println(i6 == i7);
        // false
        System.out.println(i7 == i8);
    }



}
