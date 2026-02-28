package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class AudioTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3");
        log.info("list: {}", String.valueOf(list));
    }

}
