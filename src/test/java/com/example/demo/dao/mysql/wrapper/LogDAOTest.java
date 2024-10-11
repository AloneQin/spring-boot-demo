package com.example.demo.dao.mysql.wrapper;

import com.example.demo.model.po.LogPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogDAOTest {

    @Resource
    private LogDAO logDAO;

    @Test
    public void insert() {
        LogPO logPO = new LogPO().setContent("test log");
        logDAO.save(logPO);
    }

    @Test
    public void update() {
        LogPO logPO = new LogPO().setId(9).setContent("test log 2");
        logDAO.updateById(logPO);
    }
}