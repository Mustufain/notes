package com.example.notes.infrastructure.db.repository.impl;

import com.example.notes.core.contract.UserRepository;
import com.example.notes.core.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser() {
        User user = userRepository.createUser(
                "testemail@example.com",
                "testName",
                "testIssuer",
                "testPictureUrl"
        );
        assertNotNull(user);
        assertEquals("testName", user.getName());
        assertEquals("testPictureUrl", user.getPicture());
    }

    @Test
    public void doesUserExists() {
        userRepository.createUser(
                "testemail_exists@example.com",
                "testName",
                "testIssuer",
                "testPictureUrl"
        );

        Boolean userExists = userRepository.doesUserExists("testemail_exists@example.com");
        assertTrue(userExists);

        Boolean userDoesNotExists = userRepository.doesUserExists("nonexistent@example.com");
        assertEquals(false, userDoesNotExists);
    }

    @Test
    public void shouldUpdateLastLogin() {
        userRepository.createUser(
                "testemail_1@example.com",
                "testName1",
                "testIssuer1",
                "testPictureUrl1"
        );
        userRepository.createUser(
                "testemail_2@example.com",
                "testName2",
                "testIssuer2",
                "testPictureUrl2"
        );

        User user = userRepository.updateLastLogin("testemail_1@example.com");
        assertNotNull(user);
        assertEquals("testName1", user.getName());
        assertEquals("testPictureUrl1", user.getPicture());
    }
}
