FROM openjdk:17-jdk-slim
ENV TZ "Asiz/Seoul"

WORKDIR /usr/bin/app
EXPOSE 8080
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

