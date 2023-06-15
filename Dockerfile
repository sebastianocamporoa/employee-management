#
# Build stage
#
FROM gradle:7.2.0-jdk11 AS build
COPY src /home/app/src
COPY build.gradle /home/app
COPY settings.gradle /home/app
RUN gradle -p /home/app clean build

#
# Package stage
#
FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /home/app/target/employee-management-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]