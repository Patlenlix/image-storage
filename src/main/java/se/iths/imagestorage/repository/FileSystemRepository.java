package se.iths.imagestorage.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imagestorage.entity.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileSystemRepository {
    private final Logger log = LoggerFactory.getLogger(FileSystemRepository.class);

    public void uploadImage(MultipartFile file, Image image){
        Path path = Paths.get(image.getPath());

        try {
            Files.createDirectories(path.getParent());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("Error while creating a directory. Make sure that the file name doesn't exist. Error message: "  + e);
        }
    }

    public FileSystemResource findInFileSystem(String path){
        try{
            return new FileSystemResource(Paths.get(path));
        }catch (InvalidPathException e){
            throw new RuntimeException();
        }
    }


}
