package se.iths.imagestorage.service;


import org.slf4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import se.iths.imagestorage.entity.Image;
import se.iths.imagestorage.repository.FileSystemRepository;
import se.iths.imagestorage.repository.ImageDbRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final ImageDbRepository imageDbRepository;
    private final FileSystemRepository fileSystemRepository;
    private static final int TAGET_IMAGE_SIZE = 200;
    private final Logger log;

    public ImageService(ImageDbRepository imageDbRepository, FileSystemRepository fileSystemRepository, Logger log) {
        this.imageDbRepository = imageDbRepository;
        this.fileSystemRepository = fileSystemRepository;
        this.log = log;
    }

    public Long uploadImage(MultipartFile imageAsFile) throws IOException {
        log.info("File upload started at: {}", LocalDateTime.now());
        String imagePath = setImagePath() + imageAsFile.getOriginalFilename();
        Path path = Paths.get(imagePath);

        log.info("Try to upload file... ");
        try {
            fileSystemRepository.uploadImage(imageAsFile, path, TAGET_IMAGE_SIZE);
            log.info("Saving information about image...");
            Long imageId = saveImageInformation(imageAsFile, imagePath);
            log.info("Image saved with id: {}", imageId);
            return imageId;
        } catch (IOException e) {
            log.info("Upload was NOT successfully completed");
            throw new IOException();
        }
    }

    public FileSystemResource downloadImage(Long id){
        log.info("File download started at: {}", LocalDateTime.now());

        log.info("Attempting to find image with ID: {}", id);
        Image image = imageDbRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No file with ID {} found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        log.info("Image with ID {} found", id);

        return fileSystemRepository.findInFileSystem(image.getPath());
    }

    private String setImagePath() {
        String folder = StringUtils.cleanPath(Paths.get(".").toAbsolutePath().toString());
        return folder + "/src/main/resources/static/images/";
    }

    private Long saveImageInformation(MultipartFile imageAsFile, String imagePath) {
        Image image = new Image();
        String imageName = imageAsFile.getOriginalFilename();
        image.setName(imageName);
        image.setPath(imagePath);
        return imageDbRepository.save(image).getId();
    }
}
