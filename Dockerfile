FROM eclipse-temurin:21-jre

LABEL maintainer = "maddie@gmail.com"

WORKDIR /opt

COPY target/*.jar /opt/spring-countryInfo

ENTRYPOINT exec java -jar spring-countryInfo

EXPOSE 8081