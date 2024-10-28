package com.whhxz.junit.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @InjectMocks
    private Service service;
    @Mock
    private Repository repository;

    @Test
    void save() {

        ArgumentCaptor<User> args = ArgumentCaptor.forClass(User.class);
        service.save("whhxz");

        verify(repository, times(1)).select(any(), args.capture());

        User value = args.getValue();
        Assertions.assertEquals(value.name(), "whhxz");
    }
}