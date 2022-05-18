         
# Image Storage Microservice

![GitHub issues](https://img.shields.io/github/issues-raw/patlenlix/image-storage)
![GitHub pull requests](https://img.shields.io/github/issues-pr/patlenlix/image-storage)

## Description

Image upload, storage and download microservice using Spring boot, MySQL and local storage. <br/>
This service will be used for the final project of our Webservices course at IT-Högskolan, Gothenburg.

## Deployment

1. Create network:</br>
   `docker network create net`
2. Create volumes:</br>
   `docker volume create db-storage`</br>
   `docker volume create file-storage`
3. Start consul:</br>
   `docker run -d -p 8500:8500 -p 8600:8600/udp --name=consul --network=net consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client='0.0.0.0'`
4. Add config file to consul
   1. Open Consul UI: [http://localhost:8500](http://localhost:8500)
   2. Go to Key/Value
   3. Create the following folder structure: `config/image-service/`
   4. Create a file named: `data (.yml)`
   5. Add the following YML data:

```yaml
spring:
   servlet:
      multipart:
         max-file-size: 10MB
         max-request-size: 10MB
   cloud:
      consul:
         discovery:
            register: true
            prefer-ip-address: true
            instance-id: ${spring.application.name}:${spring.cloud.client.hostname}:${random.int[1,999999]}
         host: consul
   jpa:
      hibernate:
         ddl-auto: update
   datasource:
      url: jdbc:mysql://image-database:3306/images
      username: user
      password: password
server:
   port: 8080
```

5. Start database:</br>
   `docker run -d --name image-database -e MYSQL_ROOT_PASSWORD=root -e 'MYSQL_ROOT_HOST=%' -e MYSQL_DATABASE=images -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3306:3306 --network=net -v=db-storage mysql:latest`
6. Start application:</br>
   `docker run -d --name image-service -p 8080:8080 --network=net -v=file-storage ghcr.io/patlenlix/image-service:latest`

## Endpoints

| HTTP-verb | URL               | BODY                                                                      | PRODUCES     | HEADER                             | Info                                   |
|-----------|-------------------|---------------------------------------------------------------------------|--------------|------------------------------------|----------------------------------------|
| POST      | /images           | A MultipartFile with the name "multipartFile" and the mediatype set to any |              | Content-Type: multipart/form-data</br> optional: (URI: uri used to reach endpoint)| Uploads an image                       |
| GET       | /images/{imageId} |                                                                           | An image file |                                    | Downloads an image with id = {imageId} |
