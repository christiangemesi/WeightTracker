package ch.fhnw.webeng.weighttracker.bootstrap;

import ch.fhnw.webeng.weighttracker.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class BootstrapData implements CommandLineRunner {
    private final UserService userService;

    public BootstrapData(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (userService.findByUsername("admin").isEmpty()) {
            this.userService.create("admin", "admin", Set.of("ROLE_ADMIN"));
        }
        if (userService.findByUsername("user").isEmpty()) {
            this.userService.create("user", "user", Set.of("ROLE_USER"));
        }
    }
}
