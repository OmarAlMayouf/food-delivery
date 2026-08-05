# Why Liquibase?

## Status

Accepted

## Context

Writing SQL commands to create the database schema and tables is tedious and error prone
not to mention that it is not easy to track changes or actually impossible to understand what happened before.
So instead of manually typing SQL commands that are easy to forget,
we use Liquibase to manage the database schema and track it through the changelogs.

## Decision

We use Liquibase to manage the database schema and track it through the changelogs instead of manually typing SQL
commands. This is because it is easier to maintain and easier to understand and track changes.
It also auto created the database schema and tables for the users who run the application.
Everything is automated. Even easier for new developers to understand what is going on.

why not use ddl-auto ?
well because we want to be able to change the database schema and track it through the changelogs.
then we need to use ddl-validate only when we want to validate the database schema
to ensure that it is correct.

## Consequences

It introduces more dependencies to the project, making the project more complex and larger.
Changelogs are immutable meaning that they cannot be changed once they are created.