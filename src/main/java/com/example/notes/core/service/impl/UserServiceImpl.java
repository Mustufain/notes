package com.example.notes.core.service.impl;

import com.example.notes.core.contract.UserRepository;
import com.example.notes.core.contract.UserService;
import com.example.notes.core.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User loginOrRegister(@NonNull String email, @NonNull String name, @NonNull String issuer, @NonNull String picture) {
       Boolean doesUserExists = userRepository.doesUserExists(email);
       if (doesUserExists) {
           return userRepository.updateLastLogin(email);
       }
       return userRepository.createUser(email, name, issuer, picture);
    }
}
