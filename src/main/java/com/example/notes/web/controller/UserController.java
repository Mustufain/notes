package com.example.notes.web.controller;

import com.example.notes.core.contract.UserService;
import com.example.notes.core.model.User;
import com.example.notes.web.dto.UserResponseDto;
import com.example.notes.web.mapper.UserMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
@Validated
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto createUser(@RequestHeader("X-User-Email") @NotBlank String email,
                                      @RequestHeader("X-User-Name") String name,
                                      @RequestHeader("X-User-Issuer") String issuer,
                                      @RequestHeader("X-User-Picture") String picture
                                      ) {
        User user = service.loginOrRegister(email, name, issuer, picture);
        return mapper.toResponse(user);
    }
}
