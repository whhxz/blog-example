package com.whhxz.blogexample.genericsget.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class BaseEvent<T>extends ApplicationEvent implements ResolvableTypeProvider {
    private T data;
    private int times;

    public BaseEvent(T source) {
        super(source);
    }
    public void handle(){
        times += 1;
    }

    public int getTimes() {
        return times;
    }

    public T getData() {
        return data;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
