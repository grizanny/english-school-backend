FROM openjdk:8-jdk-alpine
LABEL maintainer="Aliaksandr Rahavoi <rogovoi200023507@gmail.com>"

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} backend.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/backend.jar"]
