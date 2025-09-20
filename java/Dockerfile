# Multi-stage build for Spring Boot application
FROM --platform=linux/amd64 openjdk:17-jdk-slim AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Copy TensorFlow model files
COPY modelo_ingredientes.h5 ./
COPY food101_model ./food101_model/
COPY *.jpeg ./

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM --platform=linux/amd64 eclipse-temurin:17-jre

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN groupadd -r spring && useradd -r -g spring spring

# Set working directory
WORKDIR /app

# Copy the jar file from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Copy model files from builder stage
COPY --from=builder /app/modelo_ingredientes.h5 ./
COPY --from=builder /app/food101_model ./food101_model/
COPY --from=builder /app/*.jpeg ./

# Change ownership to spring user
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/api/recognize/test || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=production"]