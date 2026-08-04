# Why Modular Monolith?

## Status
Accepted

## Context
This project is intended as a learning project, but it is also designed to resemble a production backend.
The application contains several business domains such as authentication, restaurants, orders, payments, drivers, and notifications.
Placing all of these features into a single package structure would make the codebase harder to navigate and maintain as the project grows.

A microservices architecture would introduce additional complexity
such as service communication, distributed transactions, deployment, and operational overhead.
Those concerns are unnecessary for the current size and scope of the project.

## Decision
This project will build the application as a modular monolith for now.
Each business domain will be implemented as an independent module with clear package boundaries and minimal coupling between modules.
Modules will communicate through well-defined interfaces instead of directly accessing each other's implementation details.
If the application grows to the point where a module needs to be deployed or scaled independently,
the modular structure will make extracting it into a microservice significantly easier.

## Consequences
Some patterns that support modularity may feel unnecessary during the early stages of development.