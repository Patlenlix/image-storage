package se.iths.imagestorage.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    public FileSystemResource downloadImage(@PathVariable Long id){
        return service.downloadImage(id);
    }
}
