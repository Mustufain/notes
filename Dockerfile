# Stage 1: Build stage
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copy gradle files for caching layers
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Download dependencies (this layer is cached unless build files change)
RUN ./gradlew build -x test --no-daemon > /dev/null 2>&1 || true

# Copy source code, jooq files and build the application
COPY build/generated-sources build/generated-sources
COPY src src
RUN ./gradlew bootJar -x test --no-daemon

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root user for security (Best Practice)
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy only the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Configure JVM for container environments
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]