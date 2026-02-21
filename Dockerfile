
# docker build -t blk-hacking-ind-prachi-rajput .
# Base image supports linux/amd64 and linux/arm64 (eclipse-temurin:17-jre-alpine has no arm64 manifest).
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY target/blackrock-challenge-1.0.0.jar app.jar

EXPOSE 5477
ENTRYPOINT ["java","-jar","/app/app.jar"]
