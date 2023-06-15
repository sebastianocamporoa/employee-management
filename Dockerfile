#
# Build stage
#
FROM gradle:7.2.0-jdk11 AS build
WORKDIR /home/app
COPY build.gradle .
COPY settings.gradle .
COPY employee-management/src ./employee-management/src
RUN gradle clean build

#
# Package stage
#
FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /home/app/build/libs/employee-management-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/demo.jar"]