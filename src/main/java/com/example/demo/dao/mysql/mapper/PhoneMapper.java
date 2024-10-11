package com.example.demo.dao.mysql.mapper;

import com.example.demo.model.dto.PhonePriceStatDTO;
import com.example.demo.model.po.PhonePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 手机表 Mapper 接口
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
@Repository
public interface PhoneMapper extends BaseMapper<PhonePO> {

    List<PhonePO> selectByName(String name);

    List<PhonePriceStatDTO> countPriceRange();

}
