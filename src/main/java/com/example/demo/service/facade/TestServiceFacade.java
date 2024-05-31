package com.example.demo.service.facade;

import com.example.demo.service.PhoneService;
import com.example.demo.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 门面服务，与服务层同级，将多个服务编排组合到一起
 *
 * 避免 Service 之间相互调用<br/>
 *
 * 调用规则：<br/>
 * 1.规定服务调用只能由上至下纵向调用，调用链路：AController -> AService -> ADAO<br/>
 * 2.禁止横向调用，如 BService -> AService，如有类似场景，在门面层 AServiceFacade 进行多服务的编排和聚合<br/>
 *
 * 总调用链路：
 * AController -> AService -> ADAO
 *                  ↑
 *        AServiceFacade
 *          ↑       ↑
 * BController -> BService -> BDAO
 *
 */
@Slf4j
@Service
@AllArgsConstructor
public class TestServiceFacade {

    private final PhoneService phoneService;

    private final TestService testService;

    public void testFacadeServiceRef(String name) {
        testService.checkName(name);
        phoneService.checkPhoneExists(null, name);
    }
}
