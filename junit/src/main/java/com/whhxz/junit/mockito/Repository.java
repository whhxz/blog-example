package com.whhxz.junit.mockito;

public class Repository {
    public void select(Integer id, User user) {
        System.out.println(user);
        System.out.println(id);
    }
}
