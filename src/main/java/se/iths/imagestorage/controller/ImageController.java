package se.iths.imagestorage.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.iths.imagestorage.service.ImageService;

import java.net.URI;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile multipartFile) {
        Long createdImageId = service.uploadImage(multipartFile);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdImageId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    public FileSystemResource downloadImage(@PathVariable Long id) {
        return service.downloadImage(id);
    }
}
