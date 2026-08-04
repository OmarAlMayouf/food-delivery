# Why Docker?

## Status 
Accepted

## Context

Running the application locally requires several setup steps.
A new developer would need to install PostgreSQL, create a local database, configure an `application-local.yaml` file,
and then start the application. These manual steps increase the time required to get the project running and can lead to configuration differences
between development environments.

## Decision

Use Docker Compose to run both the application and PostgreSQL.

## Rationale

Docker reduces the amount of manual setup required for local development. Instead of installing and configuring PostgreSQL manually,
a developer can start the entire development environment with a single command:

```bash
   docker compose up --build
```

This provides a more consistent setup experience for anyone cloning the repository and reduces the likelihood of environment-specific configuration issues.

An additional benefit is that using Docker gave me practical experience with containerizing applications and managing development environments.

## Consequences

This added an extra layer of complexity to the project, but it provides a more consistent development experience.
This also increased the likelihood of spending more time on debugging issues related to environment setup.
