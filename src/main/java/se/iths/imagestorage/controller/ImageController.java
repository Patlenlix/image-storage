package se.iths.imagestorage.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import se.iths.imagestorage.service.ImageService;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile multipartFile, @RequestHeader(value = "URI", required = false) String uriAsString) {
        try {
            Long createdImageId = service.uploadImage(multipartFile);
            URI uri = getLocationOfCreatedImage(uriAsString, createdImageId);
            return ResponseEntity.created(uri).build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    public FileSystemResource downloadImage(@PathVariable Long id) {
        return service.downloadImage(id);
    }

    private URI getLocationOfCreatedImage(String uriAsString, Long createdImageId) {
        UriComponentsBuilder uriComponentsBuilder;
        if (uriAsString == null)
            uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        else
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(uriAsString);
        return uriComponentsBuilder.path("/{id}").buildAndExpand(createdImageId).toUri();
    }
}
