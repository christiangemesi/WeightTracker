package com.example.demo.Repository;

import com.example.demo.model.User;

import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSaveUser() {
        var user1 = new User("username1","password1", Set.of("ROLE_USER"));
        var user2 = new User("username2","password2", Set.of("ROLE_USER"));
        var user3 = new User("username3","password3", Set.of("ROLE_USER"));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        var users = this.userRepository.findAll();

        assertEquals(3, users.size());
        assertEquals("username1", users.get(0).getUsername());
        assertEquals("password1", users.get(0).getPassword());
    }

    @Test
    public void testSaveUserWithSameUsername() {
        var user1 = new User("username1","password1", Set.of("ROLE_USER"));
        var user2 = new User("username1","password1", Set.of("ROLE_USER"));

        userRepository.save(user1);

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(user2));
    }


    /*note this only works if tested alone */
    @Test
    public void testDeleteUser() {
        var user1 = new User("username1","password1", Set.of("ROLE_USER"));
        userRepository.save(user1);

        var users = this.userRepository.findById(1L).get();
        assertEquals(1, this.userRepository.findAll().size());

        this.userRepository.delete(users);
        assertEquals(0, this.userRepository.findAll().size());
    }


}
