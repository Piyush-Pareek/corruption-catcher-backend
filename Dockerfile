# STAGE 1: Build the Java App using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# STAGE 2: Run the App using a lightweight Java environment
# FIXED: Replaced the deleted openjdk image with eclipse-temurin
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the compiled .jar file from Stage 1
COPY --from=build /app/target/*.jar app.jar
# Open the port Spring Boot runs on
EXPOSE 8080
# Turn on the server!
ENTRYPOINT ["java", "-jar", "app.jar"]