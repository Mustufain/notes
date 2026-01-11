package com.example.notes.web.controller;

import com.example.notes.core.contract.UserService;
import com.example.notes.web.exception.GlobalExceptionHandler;
import com.example.notes.web.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({UserController.class, GlobalExceptionHandler.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        UserMapper userMapper() {
            return mock(UserMapper.class);
        }
    }

    @BeforeEach
    void BeforeEach() {
        Mockito.reset(service);
    }

    @Test
    void shouldReturnSuccessfulResponse() throws Exception {
        mockMvc.perform(post("/v1/users")
                        .header("X-User-Email", "test@example.com")
                        .header("X-User-Name", "Test")
                        .header("X-User-Issuer", "Issuer")
                        .header("X-User-Picture", "Pic"))
                .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1))
                .loginOrRegister("test@example.com", "Test", "Issuer", "Pic");
    }

    @Test
    void shouldReturnBadRequestResponse() throws Exception {
        mockMvc.perform(post("/v1/users")
                        .header("X-User-Email", "")
                        .header("X-User-Name", "Test")
                        .header("X-User-Issuer", "Issuer")
                        .header("X-User-Picture", "Pic"))
                .andExpect(status().isBadRequest());
        Mockito.verify(service, Mockito.times(0))
                .loginOrRegister("test@example.com", "Test", "Issuer", "Pic");
    }
}
