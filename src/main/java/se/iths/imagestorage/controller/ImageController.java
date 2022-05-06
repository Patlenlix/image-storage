package se.iths.imagestorage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imagestorage.service.ImageService;

@RestController
@RequestMapping("/img")
public class ImageController {
    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping
    public Long uploadImage(@RequestParam MultipartFile multipartFile){
        return service.uploadImage(multipartFile);
    }

}
