FROM gradle:7.2.0-jdk11 AS build
WORKDIR /app
COPY employee-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
COPY application.properties .
CMD ["java", "-jar", "app.jar"]