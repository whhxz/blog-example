package com.whhxz.junit.mockito;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Service {
    private final Repository repository;

    public int save(String name){
        User user = new User(name);
        repository.select(1, user);
        return 1;
    }
}
