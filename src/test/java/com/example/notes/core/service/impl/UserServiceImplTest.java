package com.example.notes.core.service.impl;

import com.example.notes.core.contract.UserRepository;
import com.example.notes.core.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String NAME = "testUser";
    private static final String PICTURE = "testPicture";
    private static final String EMAIL = "test@email.com";
    private static final String ISSUER = "testIssuer";
    private static final Long ID = 1L;


    @Test
    void shouldCreateUser() {
        User expectedUser = new User(ID, NAME, PICTURE);
        when(userRepository.doesUserExists(EMAIL)).thenReturn(false);
        when(userRepository.createUser(EMAIL, NAME, ISSUER, PICTURE)).thenReturn(expectedUser);

        User user = userService.loginOrRegister(EMAIL, NAME, ISSUER, PICTURE);

        assertEquals(user, expectedUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .createUser(EMAIL, NAME, ISSUER, PICTURE);
        Mockito.verify(userRepository, Mockito.times(0))
                .updateLastLogin(EMAIL);
    }

    @Test
    void shouldUpdateLasLogin() {
        User expectedUser = new User(ID, NAME, PICTURE);
        when(userRepository.doesUserExists(EMAIL)).thenReturn(true);
        when(userRepository.updateLastLogin(EMAIL)).thenReturn(expectedUser);

        User user = userService.loginOrRegister(EMAIL, NAME, ISSUER, PICTURE );

        assertEquals(user, expectedUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .updateLastLogin(EMAIL);
        Mockito.verify(userRepository, Mockito.times(0))
                .createUser(EMAIL, NAME, ISSUER, PICTURE);

    }
}
