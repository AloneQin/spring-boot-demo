package com.example.demo.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.demo.common.function.SFunction;
import com.example.demo.model.po.BasePO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * MyBatis-Plus 自动字段填充
 */
@Slf4j
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BasePO) {
            String value = "admin1";
            log.info("#insertFill, fill createdBy and updatedBy, value: {}, po: {}", value, metaObject.getOriginalObject().getClass().getName());
            this.strictInsertFill(metaObject, SFunction.getFieldName(BasePO::getCreatedBy), String.class, value);
            this.strictInsertFill(metaObject, SFunction.getFieldName(BasePO::getCreatedBy), String.class, value);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BasePO) {
            String value = "admin2";
            log.info("#updateFill, fill createdBy and updatedBy, value: {}, po: {}", value, metaObject.getOriginalObject().getClass().getName());
            this.strictInsertFill(metaObject, SFunction.getFieldName(BasePO::getCreatedBy), String.class, value);
        }
    }
}
