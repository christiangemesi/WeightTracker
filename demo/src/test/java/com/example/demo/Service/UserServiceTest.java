package com.example.demo.Service;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testSaveUserWithSameUsername() {
        var user1 = new User("username1", "password1", Set.of("ROLE_USER"));

        userService.addUser(user1.getUsername(), user1.getPassword(), Set.of("ROLE_USER"));

        assertTrue(userService.usernameAlreadyExists(user1.getUsername()));
    }

    @Test
    public void testLoadUserByUsername() {
        var user1 = new User("username1", "password1", Set.of("ROLE_USER"));

        userService.addUser(user1.getUsername(),user1.getPassword(),Set.of("ROLE_USER"));

        var userDetails = userService.loadUserByUsername(user1.getUsername());

        assertEquals("username1",userDetails.getUsername());
        assertNotEquals(user1.getPassword(),userDetails.getPassword());
    }
}
