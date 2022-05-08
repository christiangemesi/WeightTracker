package com.example.demo.Model;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageTest {

    @Test
    public void testCreateImage() {

        var image = new Image();
        byte[] byteArray = new byte[]{(byte) 0xe0};

        image.setId(1L);
        image.setName("test");
        image.setMimeType("image/jpeg");
        image.setFile(byteArray);

        assertEquals(1L,image.getId());
        assertEquals("test",image.getName());
        assertEquals("image/jpeg", image.getMimeType());
        assertEquals(byteArray,image.getFile());
    }

}
