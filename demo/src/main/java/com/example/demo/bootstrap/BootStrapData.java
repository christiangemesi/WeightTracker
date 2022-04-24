package com.example.demo.bootstrap;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WeightEntryRepository weightEntryRepository;
    private final ImageRepository imageRepository;

    public BootStrapData(UserRepository userRepository, WeightEntryRepository weightEntryRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.weightEntryRepository = weightEntryRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User christian = new User("christian.gemesi@students.fhnw.ch");
        Date dateChristian = new Date(2022, Calendar.APRIL,23);
        WeightEntry christianWeight = new WeightEntry(70.0,dateChristian,christian);
        userRepository.save(christian);
        weightEntryRepository.save(christianWeight);
        christian.getWeightEntriesSet().add(christianWeight);

        Image image1 = new Image(christianWeight);
        imageRepository.save(image1);


        User daniel = new User("daniel.vonatzigen@students.fhnw.ch");
        Date dateDaniel = new Date(2022, Calendar.APRIL,24);
        WeightEntry danielWeight = new WeightEntry(71.0,dateDaniel,christian);
        daniel.getWeightEntriesSet().add(danielWeight);
        userRepository.save(daniel);
        weightEntryRepository.save(danielWeight);

        Image image2 = new Image(danielWeight);
        imageRepository.save(image2);


        System.out.println("Number of Users " + userRepository.count());
        System.out.println("Number of WeightEntries " + weightEntryRepository.count());
        System.out.println("Number of Images " + imageRepository.count());

    }
}
