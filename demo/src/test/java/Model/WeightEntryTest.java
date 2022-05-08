package Model;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightEntryTest {

    @Test
    public void testCreateWeightEntry() {

        var weightEntry = new WeightEntry();
        var user = new User();
        Date date = new Date(2022, Calendar.MAY,10);
        Image image = new Image();

        weightEntry.setId(1L);
        weightEntry.setDate(date);
        weightEntry.setWeight(60);
        weightEntry.setImageSet(Set.of(image));

        assertEquals(1L,weightEntry.getId());
        assertEquals(date,weightEntry.getDate());
        assertEquals(60,weightEntry.getWeight());
        assertEquals(Set.of(image),weightEntry.getImageSet());
    }

}
