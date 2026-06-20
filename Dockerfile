# STAGE 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build 
WORKDIR /app
# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy the actual code and compile it into a .jar file
COPY src ./src
RUN mvn clean package -DskipTests

# STAGE 2: Run the application using the upgraded Temurin environment
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Safely copy the .jar file from STAGE 1 (the 'build' stage)
COPY --from=build /app/target/*.jar app.jar
# Open the port Render needs
EXPOSE 8080
# Boot up the server
ENTRYPOINT ["java", "-jar", "app.jar"]