# --- Stage 1: Build React Frontend ---
FROM node:18-alpine AS frontend-build
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# --- Stage 2: Build Spring Boot Backend ---
FROM maven:3.9.4-eclipse-temurin-17 AS backend-build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
# Copy the built React app into Spring Boot's static resources folder
COPY --from=frontend-build /frontend/dist ./src/main/resources/static

RUN mvn package -DskipTests

# --- Stage 3: Runtime ---
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=backend-build /app/target/report-management-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
