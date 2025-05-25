FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/omc-tower-monitor-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]