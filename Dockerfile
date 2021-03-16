FROM openjdk:8-jdk-alpine

RUN apk add --no-cache tzdata
ENV TZ=Africa/Johannesburg

ADD ./src/main/resources/application-qa.properties application.properties
ADD target/BookStore-0.0.1-SNAPSHOT.war bookstore.war

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "bookstore.war"]