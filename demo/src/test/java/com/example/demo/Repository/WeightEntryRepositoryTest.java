package com.example.demo.Repository;

import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class WeightEntryRepositoryTest {

    @Autowired
    private WeightEntryRepository weightEntryRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSaveWeightEntry() {

        User admin = new User("admin","admin",Set.of("USER_ADMIN"));
        userRepository.save(admin);

        Date date1 = new Date(10, Calendar.FEBRUARY,30);
        var weightEntry1 = new WeightEntry(50,date1, admin);
        weightEntryRepository.save(weightEntry1);

        var weightEntries = weightEntryRepository.findAll();

        assertEquals(1, weightEntries.size());
        assertEquals(date1, weightEntries.get(0).getDate());
        assertEquals(50, weightEntries.get(0).getWeight());
        assertEquals(admin, weightEntries.get(0).getUser());
    }

    @Test
    public void testDeleteWeightEntry() {
        User admin = new User("admin","admin",Set.of("USER_ADMIN"));
        userRepository.save(admin);

        Date date1 = new Date(10, Calendar.FEBRUARY,30);
        var weightEntry1 = new WeightEntry(50,date1, admin);
        weightEntryRepository.save(weightEntry1);

        this.weightEntryRepository.delete(weightEntry1);
        assertEquals(0, weightEntryRepository.findAll().size());
    }

}
