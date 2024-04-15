FROM openjdk:17-alpine
LABEL authors="yiyaaa"

COPY ./web/build/libs/web-0.0.1-SNAPSHOT.jar selected-seat.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/selected-seat.jar"]
