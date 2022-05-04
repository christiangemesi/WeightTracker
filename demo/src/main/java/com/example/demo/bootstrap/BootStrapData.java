package com.example.demo.bootstrap;

import com.example.demo.Service.UserService;
import com.example.demo.Service.WeightEntityService;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;


@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WeightEntryRepository weightEntryRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final WeightEntityService weightEntityService;

    public BootStrapData(UserRepository userRepository, WeightEntryRepository weightEntryRepository, ImageRepository imageRepository, UserService userService, WeightEntityService weightEntityService) {
        this.userRepository = userRepository;
        this.weightEntryRepository = weightEntryRepository;
        this.imageRepository = imageRepository;
        this.userService = userService;
        this.weightEntityService = weightEntityService;
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

        User user = (User) userService.loadUserByUsername("user");
        User admin = (User) userService.loadUserByUsername("admin");

        Date date = new Date(2022, Calendar.JUNE, 4);
        WeightEntry weightEntry1 = new WeightEntry(68, date, admin);
        weightEntryRepository.save(weightEntry1);

        Date date2 = new Date(2022, Calendar.JUNE, 5);
        WeightEntry weightEntry2 = new WeightEntry(69, date2, admin);
        weightEntryRepository.save(weightEntry2);


        Date date3 = new Date(2022, Calendar.JUNE, 6);
        WeightEntry weightEntry3 = new WeightEntry(70, date2, user);
        weightEntryRepository.save(weightEntry3);

        //var x = weightEntryRepository.findById(admin.getId());
    }
}
