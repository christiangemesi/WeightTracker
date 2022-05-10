package com.example.demo.Service;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ImageServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private WeightEntryRepository weightEntryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void testSave() {

        User user = new User("test","test",Set.of("ROLE_USER"));
        userRepository.save(user);

        WeightEntry weightEntry = new WeightEntry(50,new Date(1,1,1),user);
        weightEntry.setId(1L);
        weightEntryRepository.save(weightEntry);

        byte[] array = { 0x10};
        Image image = new Image("test",array,weightEntry);
        imageFileService.save(array, image.getName(),weightEntry.getId());

        assertEquals("true","true");

    }

}
