#
# Build stage
#
FROM gradle:7.2.0-jdk11 AS build
WORKDIR /home/app
COPY employee-management/build.gradle ./employee-management
COPY employee-management/settings.gradle ./employee-management
COPY employee-management/src ./employee-management/src
RUN gradle clean build -p employee-management

#
# Package stage
#
FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /home/app/employee-management/build/libs/employee-management-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/demo.jar"]