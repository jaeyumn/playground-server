FROM openjdk:17-alpine
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} playground.jar
ENTRYPOINT ["java", "-jar", "playground.jar"]