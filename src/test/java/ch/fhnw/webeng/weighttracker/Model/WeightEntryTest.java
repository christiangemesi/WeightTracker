package ch.fhnw.webeng.weighttracker.Model;

import ch.fhnw.webeng.weighttracker.models.Image;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightEntryTest {

    @Test
    public void testCreateWeightEntry() {

        var weightEntry = new WeightEntry();
        LocalDate date = LocalDate.of(2022, Calendar.MAY,10);
        Image image = new Image();

        weightEntry.setId(1L);
        weightEntry.setDate(date);
        weightEntry.setWeight(60);
        weightEntry.setImageList(List.of(image));

        assertEquals(1L,weightEntry.getId());
        assertEquals(date,weightEntry.getDate());
        assertEquals(60,weightEntry.getWeight());
        assertEquals(List.of(image),weightEntry.getImageList());
    }

    @Test
    public void testAddMultipleImages() {
        var weightEntry = new WeightEntry();

        var image1 = new Image();
        var image2 = new Image();

        List<Image> list = new ArrayList<>();
        list.add(image1);
        list.add(image2);

        weightEntry.setImageList(list);

        assertEquals(2,weightEntry.getImageList().size());
    }

}
