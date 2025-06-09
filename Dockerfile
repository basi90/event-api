# Step 1: Build the app with Maven
FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run the app with a JDK base image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/eventApi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]