package com.example.demo.service;

import com.example.demo.service.open.PhoneOpenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    public final PhoneOpenService phoneOpenService;

    @Override
    public void testPhoneOpenServiceRef(String name) {
        phoneOpenService.checkPhoneExists(null, name);
    }
}
