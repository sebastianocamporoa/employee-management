FROM gradle:7.2.0-jdk11 AS build
WORKDIR /app
COPY /build/libs/employee-management-0.0.1-SNAPSHOT.WAR app.WAR
EXPOSE 8080
CMD ["java", "-WAR", "app.WAR"]