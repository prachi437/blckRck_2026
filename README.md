
# Blackrock Auto-Saving APIs (Java 17 + Spring Boot)

Implements endpoints per the challenge:
- `/blackrock/challenge/v1/transactions:parse`
- `/blackrock/challenge/v1/transactions:validator`
- `/blackrock/challenge/v1/transactions:filter`
- `/blackrock/challenge/v1/returns:nps`
- `/blackrock/challenge/v1/returns:index`
- `/blackrock/challenge/v1/performance`

Processing order, q/p/k rules, inclusive timestamps, NPS & Index returns, and Docker requirements follow the spec. See *Challenge.pdf* for details.

## Build & Run

```bash
mvn -DskipTests package
java -jar target/blackrock-challenge-1.0.0.jar
# server on :5477
```

## Docker

```bash
docker build -t blk-hacking-ind-prachi-rajput .
docker run -d -p 5477:5477 blk-hacking-ind-prachi-rajput
```

## Sample cURL

```bash
curl -s -X POST http://localhost:5477/blackrock/challenge/v1/transactions:parse   -H "Content-Type: application/json"   -d '{"expenses":[{"timestamp":"2023-10-12 20:15:00","amount":250}]}'
```
