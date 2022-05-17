FROM maven:3.8.5-openjdk-17-slim as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package

FROM eclipse-temurin:17-jre
COPY --from=build /src/target/image-storage.jar /usr/src/imagestorage/
WORKDIR usr/src/imagestorage
ENTRYPOINT ["java","-jar","image-storage.jar"]



