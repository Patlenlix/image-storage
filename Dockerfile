FROM maven:3.8.5-openjdk-17-slim as build
COPY ./ /src
RUN mvn -f /src/pom.xml clean package

FROM eclipse-temurin:17-jre
COPY --from=build /src/target/image-service.jar /usr/src/imageservice/
WORKDIR usr/src/imageservice
ENTRYPOINT ["java","-jar","image-service.jar"]
