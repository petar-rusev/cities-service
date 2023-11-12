# Use OpenJDK as base image
FROM openjdk:17-jdk

# Add Maintainer Info
LABEL maintainer="petar.rusev.87@gmail.com"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/cities-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} my-spring-boot-app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/my-spring-boot-app.jar"]
