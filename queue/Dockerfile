FROM openjdk:17-alpine
LABEL authors="yiyaaa"

COPY ./build/libs/queue-0.0.1-SNAPSHOT.jar webflux.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","/webflux.jar"]
