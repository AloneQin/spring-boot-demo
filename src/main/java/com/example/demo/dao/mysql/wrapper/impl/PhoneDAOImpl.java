package com.example.demo.dao.mysql.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.mysql.mapper.PhoneMapper;
import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.SmartStringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 手机表 服务实现类
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
@Service
@AllArgsConstructor
public class PhoneDAOImpl extends ServiceImpl<PhoneMapper, PhonePO> implements PhoneDAO {

    private final PhoneMapper phoneMapper;

    @Override
    public Page<PhonePO> pageByCondition(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .like(SmartStringUtils.nonBlank(name), PhonePO::getName, name)
                .eq(SmartStringUtils.nonBlank(brand), PhonePO::getBrand, brand)
                .like(SmartStringUtils.nonBlank(remark), PhonePO::getRemark, remark)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<PhonePO> findByName(String name) {
        return phoneMapper.selectByName(name);
    }

    @Override
    public List<PhonePO> findByIdAndName(Integer id, String name) {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .eq(Objects.nonNull(id), PhonePO::getId, id)
                .eq(Objects.nonNull(name), PhonePO::getName, name)
                .list();
    }

    @Override
    public PhonePO findOne() {
        /*
         * one() 和 oneOpt() 不表示查一条数据，而是表示将结果集转换为单个对象
         * 假如结果集有多条，将会抛出 TooManyResultsException 异常
         * 解决方式：
         * 1. 使用 last("limit 1") 方法确保结果集只有一条（推荐）
         * 2. 使用 list() 方法获取多条结果，然后在结果集中手动选取一条
         */
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .last("limit 1")
                .one();
    }

    @Override
    public BigDecimal findPriceMax() {
        QueryWrapper<PhonePO> queryWrapper = new QueryWrapper<>();
        // select max(price) as price from phone
        queryWrapper.select("max(price) as price");
        PhonePO phonePO = phoneMapper.selectOne(queryWrapper);
        if (Objects.nonNull(phonePO)) {
            return phonePO.getPrice();
        }
        return new BigDecimal(0);
    }

    @Override
    public Integer findCount() {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .count();
    }
}
