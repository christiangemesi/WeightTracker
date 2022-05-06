package com.example.demo.repositories;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageFileRepository {

    public static final String RESOURCES_DIR = "uploads/";

    public void save(byte[] content, Long id) {
        try {
            Path newFile = Paths.get(RESOURCES_DIR + id + ".jpeg");
            if (!Files.exists(newFile.getParent())) {
                Files.createDirectories(newFile.getParent());
            }
            Files.write(newFile, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileSystemResource findInFileSystem(Long id) {
        return new FileSystemResource(Paths.get(RESOURCES_DIR + id + ".jpeg"));
    }

    public boolean delete(Long id) {
        Path file = Paths.get(RESOURCES_DIR + id + ".jpeg");
        try {
            Files.delete(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
