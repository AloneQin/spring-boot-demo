package com.example.demo.dao;

import com.example.demo.domain.entity.Phone;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PhoneMapper extends Mapper<Phone> {

    List<Phone> findByCondition(@Param("name") String name,
                                @Param("brand") String brand,
                                @Param("remark") String remark);

}