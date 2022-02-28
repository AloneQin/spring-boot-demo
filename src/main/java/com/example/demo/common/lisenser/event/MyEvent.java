package com.example.demo.common.lisenser.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MyEvent extends ApplicationEvent {

    private String str;

    public MyEvent(Object source, String str) {
        super(source);
        this.str = str;
    }
}
