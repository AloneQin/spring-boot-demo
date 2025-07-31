package com.example.demo.dao.mysql.wrapper;

import com.example.demo.dao.mysql.mapper.CommonMapper;
import com.example.demo.model.po.LogPO;
import com.example.demo.utils.FastjsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonDAOTest {

    @Resource
    private CommonDAO commonDAO;

    @Resource
    private CommonMapper commonMapper;

    @Test
    public void testSelectById() {
        // 每次查询传递表名，万金油方式，但是略显麻烦
        LinkedHashMap<String, Object> linkedHashMap = commonMapper.findByTableNameAndId("log", 1);
        System.out.println(linkedHashMap);

        // 同上，只不过用 DAO 封装了一下
        linkedHashMap = commonDAO.findByTableNameAndId("sys_user", 1);
        System.out.println(linkedHashMap);

        // 单独对目标表封装了一个方法，只适用表很少的时候
        LogPO logPO = commonDAO.findLogById(1);
        System.out.println(FastjsonUtils.toString(logPO));

        // 每次根据对应表的 PO 进行调用，支持泛型，操作简便
        logPO = commonDAO.findById(LogPO.class, 1);
        System.out.println(FastjsonUtils.toString(logPO));

        // 与上类似，使用 Lambda 表达式，但每次需要创建一个空对象，性能不如直接传递 Class 好
        logPO = commonDAO.findById(LogPO::new, 1);
        System.out.println(FastjsonUtils.toString(logPO));
    }


}