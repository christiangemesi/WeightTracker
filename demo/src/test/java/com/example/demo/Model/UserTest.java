package com.example.demo.Model;

import com.example.demo.models.User;
import com.example.demo.models.WeightEntry;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        user.setWeightEntryList(List.of(weightEntry));

        assertEquals("test",user.getUsername());
        assertEquals("test",user.getPassword());
        assertEquals(10L,user.getId());
        assertEquals(List.of(weightEntry),user.getWeightEntryList());
    }

    @Test
    public void testAddMultipleWeightEntries() {
        var user = new User();

        Date date = new Date(10, Calendar.NOVEMBER,10);
        Date date2 = new Date(11, Calendar.NOVEMBER,10);

        var weightEntry1 = new WeightEntry(20,date,user);
        var weightEntry2 = new WeightEntry(200,date2,user);

        List<WeightEntry> list = new ArrayList<>();
        list.add(weightEntry1);
        list.add(weightEntry2);

        user.setWeightEntryList(list);

        assertEquals(2,user.getWeightEntryList().size());
    }

}