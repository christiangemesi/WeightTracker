package ch.fhnw.webeng.weighttracker.Repository;

import ch.fhnw.webeng.weighttracker.models.Image;
import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.repositories.ImageRepository;
import ch.fhnw.webeng.weighttracker.repositories.UserRepository;
import ch.fhnw.webeng.weighttracker.repositories.WeightEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
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
        var user = userRepository.save(new User("user","user",Set.of("ROLE_USER")));

        WeightEntry weightEntry = new WeightEntry(84, LocalDate.now(), user);
        weightEntryRepository.save(weightEntry);

        Image image = new Image("Test", new byte[] {}, weightEntry, "image/png");
        imageRepository.save(image);

        var images = imageRepository.findAll();

        assertEquals(1, images.size());
    }

    @Test
    public void testDeleteImage() {
        var user = userRepository.save(new User("user","user",Set.of("ROLE_USER")));

        WeightEntry weightEntry = new WeightEntry(84, LocalDate.now(), user);
        weightEntryRepository.save(weightEntry);

        Image image = new Image("Test", new byte[] {}, weightEntry, "image/png");
        imageRepository.save(image);

        imageRepository.delete(image);
        assertEquals(0, imageRepository.findAll().size());
    }
}
