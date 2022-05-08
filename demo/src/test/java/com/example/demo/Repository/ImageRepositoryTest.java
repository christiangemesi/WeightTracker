package com.example.demo.Repository;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    WeightEntryRepository weightEntryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSaveImage() {

        User user = new User("user","user",Set.of("ROLE_USER"));
        userRepository.save(user);

        WeightEntry weightEntry = new WeightEntry();
        weightEntryRepository.save(weightEntry);

        Image image = new Image();
        imageRepository.save(image);

        var images = imageRepository.findAll();

        assertEquals(1, images.size());
    }

    @Test
    public void testDeleteImage() {
        User user = new User("user","user",Set.of("ROLE_USER"));
        userRepository.save(user);

        WeightEntry weightEntry = new WeightEntry();
        weightEntryRepository.save(weightEntry);

        Image image = new Image();
        imageRepository.save(image);

        imageRepository.delete(image);
        assertEquals(0, imageRepository.findAll().size());
    }


}
