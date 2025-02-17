package com.example.demo.dao.mysql.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.mysql.mapper.PhoneMapper;
import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import com.example.demo.model.dto.PhonePriceStatDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.SmartStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 手机表 服务实现类
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
@Service
@RequiredArgsConstructor
public class PhoneDAOImpl extends ServiceImpl<PhoneMapper, PhonePO> implements PhoneDAO {

    private final PhoneMapper phoneMapper;

    @Override
    public Page<PhonePO> pageByCondition(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .like(SmartStringUtils.nonEmpty(name), PhonePO::getName, name)
                .eq(SmartStringUtils.nonEmpty(brand), PhonePO::getBrand, brand)
                .like(SmartStringUtils.nonEmpty(remark), PhonePO::getRemark, remark)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<PhonePO> findByName(String name) {
        return phoneMapper.selectByName(name);
    }

    @Override
    public List<PhonePO> findByProdDate(LocalDate prodDate) {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .eq(Objects.nonNull(prodDate), PhonePO::getProdDate, prodDate)
                .list();
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
         * 1. 使用 service.getOne() 或 mapper.selectOne() 进行查询
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

    @Override
    public List<PhonePO> findByCountry(String country) {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                // 添加自定义操作
                .func(Objects.nonNull(country), wrapper -> {
                    if ("US".equals(country)) {
                        wrapper.eq(PhonePO::getBrand, "apple");
                    }
                })
                .list();
    }

    @Override
    public List<PhonePO> findByNested() {
        // SELECT * FROM phone WHERE ((price > ? AND brand = ?) OR (price < ? AND brand = ?)) ORDER BY price ASC
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .nested(wrapper -> wrapper.gt(PhonePO::getPrice, 5000).eq(PhonePO::getBrand, "apple"))
                .or()
                .nested(wrapper -> wrapper.lt(PhonePO::getPrice, 6000).eq(PhonePO::getBrand, "vivo"))
                .orderByAsc(PhonePO::getPrice)
                .list();
    }

    /**
     * 查询中国发布新品手机量的月份排行
     * @return 排布结果
     */
    @Override
    public List<Map<String, Object>> findChinaProductOfMonth() {
        QueryWrapper<PhonePO> wrapper = Wrappers.query();
        // select count(0) as num, date_format(prod_date, '%m') as month from phone where brand != 'apple' group by month order by month asc;
        wrapper.select("count(0) as num, date_format(prod_date, '%m') as month")
                .ne("brand", "apple")
                .groupBy("month")
                .orderByAsc("month");
        return phoneMapper.selectMaps(wrapper);
    }

    @Override
    public List<PhonePriceStatDTO> countPriceRangeNum() {
        QueryWrapper<PhonePO> wrapper = Wrappers.query();
        /*
         * SELECT
         *     CASE
         *         WHEN price BETWEEN 0 AND 4000 THEN '0-4k'
         *         WHEN price BETWEEN 4001 AND 5000 THEN '4-5k'
         *         WHEN price BETWEEN 5001 AND 6000 THEN '5-6k'
         *         ELSE '6k+'
         *     END AS price_range,
         *     COUNT(*) AS num
         * FROM
         *     phone
         * GROUP BY price_range
         * ORDER BY num DESC, price_range DESC;
         */
        wrapper.select("COUNT(*) AS num, CASE WHEN price BETWEEN 0 AND 4000 THEN '0-4k' WHEN price BETWEEN 4001 AND 5000 THEN '4-5k' WHEN price BETWEEN 5001 AND 6000 THEN '5-6k' ELSE '6k+' END AS price_range")
                .groupBy("price_range")
                .orderByDesc("num", "price_range");
        return phoneMapper.selectMaps(wrapper)
                .stream()
                .map(PhonePriceStatDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhonePriceStatDTO> countPriceRange() {
        return phoneMapper.countPriceRange();
    }

    @Override
    public int updatePriceById(Integer id, BigDecimal updatePrice) {
        return phoneMapper.updatePriceById(id, updatePrice);
    }

}
