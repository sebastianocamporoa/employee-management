FROM gradle:7.2.0-jdk11 AS build
WORKDIR /app
COPY /employee-management/build/libs/employee-management-0.0.1-SNAPSHOT.war employee-management-0.0.1-SNAPSHOT.war
EXPOSE 8080
CMD ["java", "-jar", "employee-management-0.0.1-SNAPSHOT.war"]