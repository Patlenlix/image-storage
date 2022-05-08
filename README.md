         
# Image Storage Microservice
  
![GitHub issues](https://img.shields.io/github/issues-raw/patlenlix/image-storage)
![GitHub pull requests](https://img.shields.io/github/issues-pr/patlenlix/image-storage)

## Description

Image upload, storage and download microservice using Spring boot, MySQL and local storage. <br/> 
This service will be used for the final project of our Webservices course at IT-Högskolan, Gothenburg. 
  

## Endpoints

| HTTP-verb | URL               | BODY                                                                      | PRODUCES     | HEADER                             | Info                                   |
|-----------|-------------------|---------------------------------------------------------------------------|--------------|------------------------------------|----------------------------------------|
| POST      | /images           | A MultipartFile with the name "multipartFile" and the mediatype set to any |              | Content-Type: multipart/form-data  | Uploads an image                       |
| GET       | /images/{imageId} |                                                                           | A .jpeg file |                                    | Downloads an image with id = {imageId} |
