package com.whhxz.blogexample.genericsget;

import com.whhxz.blogexample.genericsget.event.BaseEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @EventListener
    public void listener(BaseEvent<String> event) {
        event.handle();
    }

    @EventListener
    public void listener2(BaseEvent<Integer> event) {
        event.handle();
    }
}
