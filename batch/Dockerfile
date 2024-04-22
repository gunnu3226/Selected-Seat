FROM openjdk:17-alpine
LABEL authors="yiyaaa"
COPY ./build/libs/batch-0.0.1-SNAPSHOT.jar batch.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/batch.jar"]
