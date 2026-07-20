# Event Ticket Reservation System

A backend REST API for managing events, seat reservations, payments and user authentication using Spring Boot.

The project simulates a real-world ticket reservation workflow with authentication, seat availability control, payment simulation, notifications and statistics.

---

## Features

* JWT Authentication & Authorization
* User registration and login
* Event management
* Seat management
* Ticket reservation
* Payment simulation
* Reservation notifications
* Statistics dashboard
* Global exception handling
* Database migration with Flyway
* Unit testing
* Docker support

---

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* Flyway
* MapStruct
* Lombok
* Docker
* JUnit 5
* Mockito

---

## Architecture

The project follows a layered architecture.

```text
src
├── auth
├── common
├── event
├── notification
├── payment
├── reservation
├── seat
├── statistics
└── user
```

Each module is organized into:

* Controller
* Service
* Repository
* DTO
* Mapper
* Entity

---

## Reservation Flow

```text
User
  │
  ▼
Select Event
  │
  ▼
Select Seat
  │
  ▼
Check Availability
  │
  ▼
Create Reservation (PENDING)
  │
  ▼
Payment Simulation
  │
  ├──────────────┐
SUCCESS         FAILED
  │              │
  ▼              ▼
CONFIRMED     CANCELLED
  │              │
Seat Reserved  Seat Released
  │              │
Notification   Payment Failed Notification
```

---

## Running the Application

### Using Docker

Create a `.env` file from `.env.example`.

```bash
docker compose up --build
```

The application will be available at:

```text
http://localhost:8080
```

### Running Locally

Requirements:

* Java 21
* PostgreSQL

Configure the required environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
SECRET_KEY
EXPIRATION_TIME
```

Run the application normally from your IDE or with Maven.

---

## Testing

The project contains unit tests for the service layer.

Covered services:

* ReservationService
* EventService
* StatisticsService

Current test result:

```text
Tests run: 19
Failures: 0
Errors: 0
Skipped: 0
```

---

## Future Improvements

* Real payment integration
* Email notifications
* Redis caching
* Integration tests
* GitHub Actions CI/CD
* Reservation expiration mechanism

---

## Author

Emre Özalp
