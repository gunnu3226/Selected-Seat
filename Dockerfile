FROM openjdk:17-alpine
LABEL authors="yiyaaa"

COPY ./web/build/libs/web-0.0.1-SNAPSHOT.jar /web/build/libs/web-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/web/build/libs/web-0.0.1-SNAPSHOT.jar"]
