package com.example.demo.Service;

import com.example.demo.model.Image;
import com.example.demo.model.WeightEntry;
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


        // imageRepository.save(bytes, image.getId());
        return image;
    }

//    public Optional<FileSystemResource> find(Long imageId) {
//        return imageRepository.findById(imageId).map((image) -> (
//                fileRepository.findInFileSystem(image.getId())
//        ));
//    }

}
