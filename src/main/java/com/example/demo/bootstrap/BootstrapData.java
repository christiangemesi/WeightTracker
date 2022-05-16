package com.example.demo.bootstrap;

import com.example.demo.services.UserService;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class BootstrapData implements CommandLineRunner {
    private final WeightEntryRepository weightEntryRepository;
    private final UserService userService;

    public BootstrapData(WeightEntryRepository weightEntryRepository, UserService userService) {
        this.weightEntryRepository = weightEntryRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.loadUserByUsername("admin");
        } catch (UsernameNotFoundException e) {
            this.userService.addUser("admin", "admin", Set.of("ROLE_ADMIN"));
        }
        try {
            userService.loadUserByUsername("user");
        } catch (UsernameNotFoundException e) {
            this.userService.addUser("user", "user", Set.of("ROLE_USER"));
        }
    }
}
