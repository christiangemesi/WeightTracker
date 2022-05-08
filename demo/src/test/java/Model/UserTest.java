package Model;

import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void testCreateUser() {

        var weightEntry = new WeightEntry();

        var user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setId(10L);
        user.setWeightEntrySet(Set.of(weightEntry));

        assertEquals("test",user.getUsername());
        assertEquals("test",user.getPassword());
        assertEquals(10L,user.getId());
        assertEquals(Set.of(weightEntry),user.getWeightEntrySet());
    }

    @Test
    public void testAddMultipleWeightEntries() {
        var user = new User();

        var weightEntry1 = new WeightEntry();
        var weightEntry2 = new WeightEntry();

        user.setWeightEntrySet(Set.of(weightEntry1,weightEntry2));



    }

}
