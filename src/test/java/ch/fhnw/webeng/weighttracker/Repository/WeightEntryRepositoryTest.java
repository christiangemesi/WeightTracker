package ch.fhnw.webeng.weighttracker.Repository;

import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.repositories.UserRepository;
import ch.fhnw.webeng.weighttracker.repositories.WeightEntryRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
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

        LocalDate date1 = LocalDate.of(10, Calendar.FEBRUARY,30);
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

        LocalDate date1 = LocalDate.of(2020, 1, 8);
        var weightEntry1 = new WeightEntry(50,date1, admin);
        weightEntryRepository.save(weightEntry1);

        this.weightEntryRepository.delete(weightEntry1);
        assertEquals(0, weightEntryRepository.findAll().size());
    }

}
