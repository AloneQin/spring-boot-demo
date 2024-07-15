package com.example.demo.dao.mysql.wrapper.impl;

import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneDAOImplTest {

    @Resource
    private PhoneDAO phoneDAO;

    @Test
    void findPriceMax() {
        BigDecimal priceMax = phoneDAO.findPriceMax();
        System.out.println(priceMax);
    }

    @Test
    void findCount() {
        Integer count = phoneDAO.findCount();
        System.out.println(count);
    }
}