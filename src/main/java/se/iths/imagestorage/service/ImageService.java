package se.iths.imagestorage.service;


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

@Service
public class ImageService {

    private final ImageDbRepository imageDbRepository;
    private final FileSystemRepository fileSystemRepository;
    private static final int TAGET_IMAGE_SIZE = 200;

    public ImageService(ImageDbRepository imageDbRepository, FileSystemRepository fileSystemRepository) {
        this.imageDbRepository = imageDbRepository;
        this.fileSystemRepository = fileSystemRepository;
    }

    public Long uploadImage(MultipartFile imageAsFile){
        Image image = new Image();
        image.setName(imageAsFile.getOriginalFilename());

        image.setPath(setImagePath() + imageAsFile.getOriginalFilename());
        fileSystemRepository.uploadImage(imageAsFile,image, TAGET_IMAGE_SIZE);
        return imageDbRepository.save(image).getId();
    }

    public FileSystemResource downloadImage(Long id){
        Image image = imageDbRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return fileSystemRepository.findInFileSystem(image.getPath());
    }
    private String setImagePath(){
        String folder = StringUtils.cleanPath(Paths.get(".").toAbsolutePath().toString());
        return folder + "/src/main/resources/static/images/";
    }

}
