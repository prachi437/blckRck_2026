# Blackrock Auto-Saving APIs

Java 17 + Spring Boot 3.3 application implementing the Blackrock challenge endpoints:

- `POST /blackrock/challenge/v1/transactions:parse`
- `POST /blackrock/challenge/v1/transactions:validator`
- `POST /blackrock/challenge/v1/transactions:filter`
- `POST /blackrock/challenge/v1/returns:nps`
- `POST /blackrock/challenge/v1/returns:index`
- `GET /blackrock/challenge/v1/performance`

Processing order, q/p/k rules, inclusive timestamps, NPS & Index returns, and Docker requirements follow the spec. See *Challenge.pdf* for details.

---

## Prerequisites

- **Java 17** (e.g. Eclipse Temurin, OpenJDK)
- **Maven 3.6+** (to build from source)
- **Docker** (optional, for containerized run)

Check versions:

```bash
java -version   # should show 17.x
mvn -v          # Maven 3.6+
```

---

## Build

From the project root:

```bash
# Build and run tests
mvn clean package

# Build without running tests
mvn clean package -DskipTests
```

The runnable JAR is produced at:

```
target/blackrock-challenge-1.0.0.jar
```

---

## Run

The server listens on **port 5477** by default.

### Option 1: Run the JAR

```bash
java -jar target/blackrock-challenge-1.0.0.jar
```

### Option 2: Run with Maven

```bash
mvn spring-boot:run
```

Base URL when running locally: **http://localhost:5477**

---

## Docker

Build the image **after** creating the JAR (Dockerfile copies it from `target/`):

```bash
# 1. Build the JAR
mvn clean package -DskipTests

# 2. Build the Docker image
docker build -t blackrock-challenge .

# 3. Run the container (port 5477)
docker run -d -p 5477:5477 --name blackrock-api blackrock-challenge
```

Stop and remove the container:

```bash
docker stop blackrock-api
docker rm blackrock-api
```

---

## Quick test

```bash
curl -s -X POST http://localhost:5477/blackrock/challenge/v1/transactions:parse \
  -H "Content-Type: application/json" \
  -d '{"expenses":[{"timestamp":"2023-10-12 20:15:00","amount":250}]}'
```

---

## Project structure

| Path | Description |
|------|-------------|
| `src/main/java/com/example/blackrock/api/` | REST controllers |
| `src/main/java/com/example/blackrock/domain/` | Domain models and DTOs |
| `src/main/java/com/example/blackrock/service/` | Business logic |
| `pom.xml` | Maven build and dependencies |
