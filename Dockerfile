FROM openjdk:9.4
COPY target/helthCareEx.jar  /usr/app/
WORKDIR /usr/app/
ENTRYPOINT  ["java", "-jar", "healthCareEx.jar"]