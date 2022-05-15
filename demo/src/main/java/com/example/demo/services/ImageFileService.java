package com.example.demo.services;

import com.example.demo.models.Image;
import com.example.demo.models.WeightEntry;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.WeightEntryRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
public class ImageFileService {

    private final ImageRepository imageRepository;
    private final WeightEntryRepository weightEntryRepository;

    public ImageFileService(
            ImageRepository imageRepository,
            WeightEntryRepository weightEntryRepository
    ) {
        this.imageRepository = imageRepository;
        this.weightEntryRepository = weightEntryRepository;
    }

    public Image save(byte[] bytes, String imageName, long weightEntryId) {
        Tika tika = new Tika();
        String mimeType = tika.detect(bytes);

        WeightEntry weightEntry = weightEntryRepository.findById(weightEntryId).orElseThrow();

        Image image = new Image(imageName, bytes, weightEntry);
        image.setMimeType(mimeType);
        image = imageRepository.save(image);

        return image;
    }
}
