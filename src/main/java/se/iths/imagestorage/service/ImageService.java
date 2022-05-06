package se.iths.imagestorage.service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imagestorage.entity.Image;
import se.iths.imagestorage.repository.FileSystemRepository;
import se.iths.imagestorage.repository.ImageDbRepository;

import java.nio.file.Paths;

@Service
public class ImageService {

    private final ImageDbRepository imageDbRepository;
    private final FileSystemRepository fileSystemRepository;

    public ImageService(ImageDbRepository imageDbRepository, FileSystemRepository fileSystemRepository) {
        this.imageDbRepository = imageDbRepository;
        this.fileSystemRepository = fileSystemRepository;
    }

    public Long uploadImage(MultipartFile imageAsFile){
        Image image = new Image();
        image.setName(imageAsFile.getOriginalFilename());

        image.setPath(setImagePath() + imageAsFile.getOriginalFilename());
        fileSystemRepository.uploadImage(imageAsFile,image);
        return imageDbRepository.save(image).getId();
    }

    private String setImagePath(){
        String folder = StringUtils.cleanPath(Paths.get(".").toAbsolutePath().toString());
        return folder + "/src/main/resource/static/images/";
    }

}
