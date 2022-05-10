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

import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final ImageDbRepository imageDbRepository;
    private final FileSystemRepository fileSystemRepository;
    private final Logger log;

    public ImageService(ImageDbRepository imageDbRepository, FileSystemRepository fileSystemRepository, Logger log) {
        this.imageDbRepository = imageDbRepository;
        this.fileSystemRepository = fileSystemRepository;
        this.log = log;
    }

    public Long uploadImage(MultipartFile imageAsFile){
        log.info("File upload started at: {}", LocalDateTime.now());

        Image image = new Image();

        log.info("Setting Image name...");
        String imageName = imageAsFile.getOriginalFilename();
        image.setName(imageName);
        log.info("Image name set to: {}", imageName);

        log.info("Setting Image path...");
        String imagePath = setImagePath() + imageAsFile.getOriginalFilename();
        image.setPath(imagePath);
        log.info("Image path set to: {}", imagePath);

        fileSystemRepository.uploadImage(imageAsFile,image);
        return imageDbRepository.save(image).getId();
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
    private String setImagePath(){
        String folder = StringUtils.cleanPath(Paths.get(".").toAbsolutePath().toString());
        return folder + "/src/main/resources/static/images/";
    }

}
