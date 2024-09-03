# Use a suitable Java base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file and other necessary files
COPY target/gcs-bq-sample-1.0.0-SNAPSHOT.jar /app
# Add any additional files if required

# Command to run your application
CMD ["java", "-jar", "gcs-bq-sample-1.0.0-SNAPSHOT.jar"]
