FROM adoptopenjdk:17-jdk-hotspot
WORKDIR /app
COPY employee-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
COPY application.properties .
CMD ["java", "-jar", "app.jar"]