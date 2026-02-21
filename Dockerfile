
# docker build -t blk-hacking-ind-prachi-rajput .
# OS: Using Alpine Linux for small footprint and fast updates (Linux-based as required).
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY target/blackrock-challenge-1.0.0.jar app.jar

EXPOSE 5477
ENTRYPOINT ["java","-jar","/app/app.jar"]
