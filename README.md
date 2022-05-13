         
# Image Storage Microservice

![GitHub issues](https://img.shields.io/github/issues-raw/patlenlix/image-storage)
![GitHub pull requests](https://img.shields.io/github/issues-pr/patlenlix/image-storage)

## Description

Image upload, storage and download microservice using Spring boot, MySQL and local storage. <br/>
This service will be used for the final project of our Webservices course at IT-Högskolan, Gothenburg.

## Deployment

1. Create network:
   `docker network create net`
2. Create volumes:
   `docker volume create dataVol`
   `docker volume create fileVol`
3. Start consul:
   `docker run -d -p 8500:8500 -p 8600:8600/udp --name=consul --network=net consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client='0.0.0.0'
   `
4. Start database:
   `docker run -d --name database -e MYSQL_ROOT_PASSWORD=root -e 'MYSQL_ROOT_HOST=%' -e MYSQL_DATABASE=images -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3306:3306 --network=net -v=dataVol mysql:latest
   `
5. Start application
   1. build docker image via the dockerfil, name it "app"
   run: `docker run -d --name myapp -p 8080:8080 --network=net -v=fileVol -e MYSQL_HOST=database -e CONSUL_HOST=consul app:latest`

## Endpoints

| HTTP-verb | URL               | BODY                                                                      | PRODUCES     | HEADER                             | Info                                   |
|-----------|-------------------|---------------------------------------------------------------------------|--------------|------------------------------------|----------------------------------------|
| POST      | /images           | A MultipartFile with the name "multipartFile" and the mediatype set to any |              | Content-Type: multipart/form-data</br> optional: (URI: uri used to reach endpoint)| Uploads an image                       |
| GET       | /images/{imageId} |                                                                           | An image file |                                    | Downloads an image with id = {imageId} |
