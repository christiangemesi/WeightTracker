package ch.fhnw.webeng.weighttracker.services;

import ch.fhnw.webeng.weighttracker.models.Image;
import ch.fhnw.webeng.weighttracker.repositories.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void delete(Image image) {
        image.getWeightEntry().getImages().remove(image);
        imageRepository.delete(image);
    }
}
