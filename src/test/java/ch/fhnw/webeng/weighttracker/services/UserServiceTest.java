package ch.fhnw.webeng.weighttracker.services;

import ch.fhnw.webeng.weighttracker.models.User;
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
    public void testFindByUsername() {
        var username = "username0";
        var password = "password0";
        var user = userService.create(username, password, Set.of("ROLE_USER"));

        var userOption = userService.findByUsername(user.getUsername());

        assertTrue(userOption.isPresent());
        assertEquals(username, userOption.get().getUsername());
        assertNotEquals(password, userOption.get().getPassword());
    }

    @Test
    public void testLoadUserByUsername() {
        var username = "username1";
        var password = "password1";
        var user = userService.create(username, password, Set.of("ROLE_USER"));

        var userDetails = userService.loadUserByUsername(user.getUsername());

        assertEquals(username, userDetails.getUsername());
        assertNotEquals(password, userDetails.getPassword());
    }
}
