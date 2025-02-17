package com.example.demo.dao.mysql.wrapper.impl;

import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import com.example.demo.model.dto.PhonePriceStatDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.FastjsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
//        System.out.println(count);phoneDAO.
    }



    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("b", "b");
        map.put("a", "a");
        map.put("x-Access-XXX", "XXX");
        System.out.println(FastjsonUtils.toStringFormat(map));

        // ASCII
        System.out.println((int)'x');
        System.out.println((int)'X');
        System.out.println((int)'a');
    }

    @Test
    void findByNested() {
        List<PhonePO> list = phoneDAO.findByNested();
        System.out.println(FastjsonUtils.toStringFormat(list));
    }

    @Test
    void findChinaProductOfMonth() {
        List<Map<String, Object>> list = phoneDAO.findChinaProductOfMonth();
        System.out.println(FastjsonUtils.toStringFormat(list));
    }

    @Test
    void countPriceRangeNum() {
        List<PhonePriceStatDTO> list = phoneDAO.countPriceRangeNum();
        System.out.println(FastjsonUtils.toStringFormat(list));
    }

    @Test
    void countPriceRange() {
        List<PhonePriceStatDTO> list = phoneDAO.countPriceRange();
        System.out.println(FastjsonUtils.toStringFormat(list));
    }

    @Test
    void updatePriceById() {
        int i = phoneDAO.updatePriceById(6, new BigDecimal(-1));
        System.out.println(i);
    }
}