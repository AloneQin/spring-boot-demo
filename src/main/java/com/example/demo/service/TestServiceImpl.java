package com.example.demo.service;

import com.example.demo.dao.wrapper.impl.PhoneDAOImpl;
import com.example.demo.model.po.PhonePO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final PhoneDAOImpl phoneDAO;

    @Override
    @Transactional
    public void add1() {
        PhonePO phonePO = new PhonePO();
        phonePO.setPhoneCode("aaaa");
        phonePO.setName("aaaa");
        phonePO.setBrand("aaaa");
        phonePO.setProdDate(LocalDate.now());
        phonePO.setPrice(new BigDecimal("100.00"));
        phonePO.setRemark("test");
        phoneDAO.save(phonePO);

        this.add2();
    }

    @Override
    @Transactional
    public void add2() {
        PhonePO phonePO = new PhonePO();
        phonePO.setPhoneCode("bbbb");
        phonePO.setName("bbbb");
        phonePO.setBrand("bbbb");
        phonePO.setProdDate(LocalDate.now());
        phonePO.setPrice(new BigDecimal("100.00"));
        phonePO.setRemark("test");
        phoneDAO.save(phonePO);

//        throw new RuntimeException("test exception");
    }
}
