# ------------ Build Stage ------------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy source code and Maven wrapper
COPY . .

# Build the app (skip tests to speed it up)
RUN ./mvnw clean package -DskipTests

# ------------ Runtime Stage ------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/computer-point-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional but good practice)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
