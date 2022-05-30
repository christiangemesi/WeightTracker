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

    /**
     *  Note this Test only works if executed alone
     *  */
    @Test
    public void testSaveUserWithSameUsername() {
        var user3 = new User("username1", "username1", Set.of("ROLE_USER"));

        userService.create(user3.getUsername(), user3.getPassword(), Set.of("ROLE_USER"));

        assertTrue(userService.findByUsername("username1").isPresent());
    }

    @Test
    public void testLoadUserByUsername() {
        var user2 = new User("username1", "password1", Set.of("ROLE_USER"));

        userService.create(user2.getUsername(),user2.getPassword(),Set.of("ROLE_USER"));

        var userDetails = userService.loadUserByUsername(user2.getUsername());

        assertEquals("username1",userDetails.getUsername());
        assertNotEquals(user2.getPassword(),userDetails.getPassword());
    }
}
