package com.example.demo.bootstrap;

import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WeightEntryRepository weightEntryRepository;

    public BootStrapData(UserRepository userRepository, WeightEntryRepository weightEntryRepository) {
        this.userRepository = userRepository;
        this.weightEntryRepository = weightEntryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User christian = new User("christian.gemesi@students.fhnw.ch");
        Date date = new Date();

        WeightEntry christianWeight = new WeightEntry(70.0,date,christian);
        christian.getWeightEntriesSet().add(christianWeight);


        userRepository.save(christian);
        weightEntryRepository.save(christianWeight);

        User daniel = new User("daniel.vonatzigen@students.fhnw.ch");
        WeightEntry danielWeight = new WeightEntry(65.0,date,daniel);

        daniel.getWeightEntriesSet().add(danielWeight);

        userRepository.save(daniel);
        weightEntryRepository.save(danielWeight);


        System.out.println("Number of Users " + userRepository.count());
        System.out.println("Number of WeightEntries " + weightEntryRepository.count());

    }
}
