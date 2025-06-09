# Event API

A simple Spring Boot application for managing events. It supports creating, retrieving, and filtering events by title and start time, and includes a notification scheduler to log upcoming events starting within the next 5 minutes.

## Features

- Create new events
- Retrieve a single event by ID
- Filter events by title and sort by start time
- Scheduled logging for upcoming events
- Flyway-based database migrations
- Dockerized for easy deployment

## Tech Stack

- Java 21
- Spring Boot 3.5
- PostgreSQL 16
- Flyway for database migrations
- Docker & Docker Compose

## Running the App

Make sure Docker is installed and run:

```bash
docker compose up --build
