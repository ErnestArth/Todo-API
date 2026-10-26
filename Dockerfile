#FROM eclipse-temurin:21-jre-alpine
#WORKDIR /app
#COPY target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Build the application
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]