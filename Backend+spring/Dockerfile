# Use an official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/ecomguard-pro-0.0.1-SNAPSHOT.jar"]
