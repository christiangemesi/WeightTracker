package ch.fhnw.webeng.weighttracker.repositories;

import ch.fhnw.webeng.weighttracker.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
