package ch.fhnw.webeng.weighttracker.Model;

import ch.fhnw.webeng.weighttracker.models.Image;
import org.junit.jupiter.api.Test;

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
