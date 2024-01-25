package com.whhxz.blogexample.genericsget.event;

import com.whhxz.blogexample.genericsget.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class EventTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void event() {
        BaseEvent<String> event1 = new BaseEvent<>("123");
        context.publishEvent(event1);
        assertEquals(event1.getTimes(), 1);
        BaseEvent<Integer> event2 = new BaseEvent<>(123);
        context.publishEvent(event2);
        assertEquals(event1.getTimes(), 1);
    }
}
