# OMC Tower Monitor

A Java Spring Boot application for monitoring temperature sensors on a commercial building ("A-Tower").

## 📋 Features

- Accepts real-time temperature readings from sensors via HTTP
- Aggregates hourly temperature averages per building face (north, east, south, west)
- Identifies malfunctioning sensors that deviate >20% from others on the same face
- Provides two API reports:
    - Hourly average report for the past 7 days
    - List of currently malfunctioning sensors
- Logs malfunctioning sensors automatically every hour

---

## 🚀 Run Locally

```
./gradlew build
java -jar build/libs/omc-tower-monitor-1.0.0.jar
```

Make sure Java 21+ and SQLite JDBC driver are supported.

---

## 🐳 Run with Docker

```
docker build -t omc-tower-monitor .
docker run -p 8080:8080 omc-tower-monitor
```

---

## 🔌 REST API

### POST `/sensor-data`

Receives sensor temperature data.

```json
{
  "timestamp": 1716603600,
  "sensorId": 123,
  "face": "north",
  "temperature": 24.3
}
```

Example:

```
curl -X POST http://localhost:8080/sensor-data \
  -H "Content-Type: application/json" \
  -d '{"timestamp":1716603600,"sensorId":123,"face":"north","temperature":24.3}'
```

---

### GET `/report/hourly`

Returns hourly average temperatures per building face for the past 7 days.

```
curl http://localhost:8080/report/hourly
```

---

### GET `/report/malfunctioning`

Returns a list of sensors with more than 20% deviation from others on the same face (based on last hour).

```
curl http://localhost:8080/report/malfunctioning
```

---

## 📝 Notes

- All timestamps are expected to be Unix time (in seconds).
- Data is persisted in SQLite (`sensor-monitoring.db`).
- The app uses plain JDBC via Spring's JdbcTemplate, and schema is initialized from schema.sql at startup.
