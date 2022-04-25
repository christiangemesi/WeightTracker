package com.example.demo.repositories;

import com.example.demo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
