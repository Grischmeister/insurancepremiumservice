# application-service/Dockerfile
FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/application-service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]