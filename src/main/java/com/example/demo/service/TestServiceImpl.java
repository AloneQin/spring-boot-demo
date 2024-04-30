package com.example.demo.service;

import com.example.demo.service.facade.PhoneServiceFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    public final PhoneServiceFacade phoneServiceFacade;

    @Override
    public void testPhoneOpenServiceRef(String name) {
        phoneServiceFacade.checkPhoneExists(null, name);
    }
}
