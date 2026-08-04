# Food Delivery

0% AI production-inspired food delivery platform built for learning backend engineering.

## Goals

- Learn Spring Boot
- Learn software architecture
- Start as a modular monolith
- Evolve into microservices

## Tech Stack

- Java 21
- Spring Boot
- PostgreSQL
- Docker & Docker Compose
- Liquibase
- Spring Security

## Getting Started

### Clone the repository

```bash
   git clone https://github.com/OmarAlMayouf/food-delivery.git
```
```bash
   cd food-delivery
```

### Create the environment file

```bash
   cp .env.example .env
```

### Configure the environment variables

```properties
DATABASE_NAME=food-delivery-db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres123
```

### Start the application

```bash
   docker compose up --build
```

The application will be available at:

```
http://localhost:8080
```

PostgreSQL will be available at:

```
localhost:5433
```

## Documentation

Project documentation is available under the `docs/` directory.

- Project Scope
- Restaurant Module
- Architecture Decisions