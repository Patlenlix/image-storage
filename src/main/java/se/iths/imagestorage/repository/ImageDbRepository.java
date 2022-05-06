package se.iths.imagestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.imagestorage.entity.Image;

@Repository
public interface ImageDbRepository extends JpaRepository<Image, Long> {}
