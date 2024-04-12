package com.example.demo.service.open;

import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.AssertUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 手机开放服务，可供其他服务调用
 *
 * 避免 Service 之间相互调用<br/>
 *
 * 调用规则：<br/>
 * 1.规定服务调用只能由上至下纵向调用，调用链路：AController -> AService -> ADAO<br/>
 * 2.禁止横向调用，如 BService -> AService，如有类似场景，将公共方法抽取到 AOpenService 开放层<br/>
 *
 * 总调用链路：
 * AController -> AService -> ADAO
 *                  ↑
 *              AOpenService
 *                  ↓
 * BController -> BService -> BDAO
 *
 */
@Slf4j
@Service
@AllArgsConstructor
public class PhoneOpenService {

    private final PhoneDAO phoneDAO;

    public void checkPhoneExists(Integer id, String name) {
        List<PhonePO> phonePOList = phoneDAO.findByIdAndName(id, name);
        AssertUtils.isTrue(CollectionUtils.isEmpty(phonePOList), ReturnCodeEnum.PHONE_ALREADY_EXISTS,
                () -> log.warn("the phone already exists, id: {}, name: {}", id, name)
        );
    }
}
