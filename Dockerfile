FROM eclipse-temurin:11-jre-alpine

ARG profile=prod

WORKDIR /app
COPY app/build/libs/*-SNAPSHOT.jar /app/app.jar
COPY config /app/config

CMD ["java","-jar","-Dspring.profiles.active=prod","/app/app.jar"]
EXPOSE 8000
