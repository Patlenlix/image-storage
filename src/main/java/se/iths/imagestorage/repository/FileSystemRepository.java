package se.iths.imagestorage.repository;

import org.slf4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imagestorage.ImageManipulation;
import se.iths.imagestorage.entity.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileSystemRepository {
    private final Logger log;

    public FileSystemRepository(Logger log) {
        this.log = log;
    }

    public void uploadImage(MultipartFile file, Image image, int targetSize) throws IOException {
        Path path = Paths.get(image.getPath());

        log.info("Checking and creating directory...");
        Files.createDirectories(path.getParent());
        byte[] bytes = file.getBytes();
        log.info("Attempting to upload file...");
        Files.write(path, ImageManipulation.resize(bytes, targetSize, file.getContentType()));
        log.info("Image successfully uploaded to: {}", path);
    }

    public FileSystemResource findInFileSystem(String path) {
        try {
            log.info("Attempting to download file at: {}", path);
            return new FileSystemResource(Paths.get(path));
        } catch (InvalidPathException e) {
            log.error("No file found at: {}", path);
            throw new RuntimeException(e);
        }
    }

}
