         
# Image Storage Microservice

![GitHub issues](https://img.shields.io/github/issues-raw/patlenlix/image-storage)
![GitHub pull requests](https://img.shields.io/github/issues-pr/patlenlix/image-storage)

## Description

Image upload, storage and download microservice using Spring boot, MySQL and local storage. <br/>
This service will be used for the final project of our Webservices course at IT-Högskolan, Gothenburg.

## Deployment

1. Create network:
   `docker network create image-net`
2. Create volumes:
   `docker volume create db-storage`
   `docker volume create file-storage`
3. Start consul:
   `docker run -d -p 8500:8500 -p 8600:8600/udp --name=consul --network=image-net consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client='0.0.0.0'
   `
4. Start database:
   `docker run -d --name image-database -e MYSQL_ROOT_PASSWORD=root -e 'MYSQL_ROOT_HOST=%' -e MYSQL_DATABASE=images -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3306:3306 --network=image-net -v=db-storage mysql:latest
   `
5. Start application
   `docker run -d --name image-service -p 8080:8080 --network=image-net -v=file-storage -e MYSQL_HOST=database -e CONSUL_HOST=consul ghcr.io/patlenlix/image-storage:latest`

## Endpoints

| HTTP-verb | URL               | BODY                                                                      | PRODUCES     | HEADER                             | Info                                   |
|-----------|-------------------|---------------------------------------------------------------------------|--------------|------------------------------------|----------------------------------------|
| POST      | /images           | A MultipartFile with the name "multipartFile" and the mediatype set to any |              | Content-Type: multipart/form-data</br> optional: (URI: uri used to reach endpoint)| Uploads an image                       |
| GET       | /images/{imageId} |                                                                           | An image file |                                    | Downloads an image with id = {imageId} |
