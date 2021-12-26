# Pull the Java Runtime Environment
FROM openjdk:11-jre

# Set the working directory in the container
WORKDIR /app

# Move compiled jar file to the app folder
COPY target/mancala-backend-0.0.1-SNAPSHOT.jar ./

# Expose container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "mancala-backend-0.0.1-SNAPSHOT.jar"]