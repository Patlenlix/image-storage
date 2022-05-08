         
# Image storage
  
![GitHub issues](https://img.shields.io/github/issues-raw/patlenlix/image-storage)
![GitHub pull requests](https://img.shields.io/github/issues-pr/patlenlix/image-storage)

  
  **Image storage** is a Spring Boot application. 
  

  ## Endpoints

| HTTP-verb | URL               | BODY                                                                      | PRODUCES     | HEADER                             | Info                                   |
|-----------|-------------------|---------------------------------------------------------------------------|--------------|------------------------------------|----------------------------------------|
| POST      | /images           | A MultipartFile with the name "multipartFile" <br/>and the mediatype set to any |              | Content-Type: multipart/form-data  | Uploads an image                       |
| GET       | /images/{imageId} |                                                                           | A .jpeg file |                                    | Downloads an image with id = {imageId} |
