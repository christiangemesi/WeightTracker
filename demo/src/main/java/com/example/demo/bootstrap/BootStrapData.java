package com.example.demo.bootstrap;

import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WeightEntryRepository weightEntryRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;

    public BootStrapData(UserRepository userRepository, WeightEntryRepository weightEntryRepository, ImageRepository imageRepository, UserService userService) {
        this.userRepository = userRepository;
        this.weightEntryRepository = weightEntryRepository;
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            userService.loadUserByUsername("admin");
        } catch (UsernameNotFoundException e) {
            this.userService.addUser("admin", "admin",Set.of("ROLE_ADMIN"));
        }
        System.out.println(userRepository.findByUsername("admin").get().getId());

        try {
            userService.loadUserByUsername("admin2");
        } catch (UsernameNotFoundException e) {
            this.userService.addUser("user", "user",Set.of("ROLE_USER"));
        }

    }
}
