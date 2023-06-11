FROM openjdk:8-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} TimeServer.jar
ENTRYPOINT ["java","-jar","TimeServer.jar", "--spring.profiles.active=common, prod"]