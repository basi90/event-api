# Event API

A modular Spring Boot application for managing and scheduling events. Users can create and retrieve events, filter them by title or start time, and rely on a background task to log upcoming events automatically.

---

## Features

- Add, list, and fetch events by ID  
- Filter events by title and sort by start time (ASC/DESC)  
- Scheduled background task to log events starting within 5 minutes  
- Database migrations handled via Flyway  
- Docker and Docker Compose setup for local development  
- OpenAPI docs available via Swagger UI

---

## Getting Started

### 1. Clone the repository and enter its directory
```bash
git clone https://github.com/basi90/event-api.git
cd event-api
```

### 2. Install Docker and run the application with Docker Compose
```bash
docker compose up --build
```
This will:
- Build the Spring Boot application
- Start a PostgreSQL container
- Apply Flyway database migrations
- Launch the app at: http://localhost:8080

### 3. Explore the API (Swagger UI)
- Visit: http://localhost:8080/swagger-ui/index.html
---

## Future Improvements

- **Pagination**: Support for paginated responses on event listing  
- **Advanced Filtering**: Add filters for description, date range, and combined fields  
- **Persistent Notification Tracking**: Store notified event IDs in a database or distributed cache (e.g. Redis)  
- **Authentication and Authorization**: Add user login and role-based access control  
- **Webhook/Event Integration**: Send real-time notifications via email, Slack, or external webhooks  
- **Admin UI Dashboard**: A web interface for managing and monitoring events  

---

## If I Had More Time, I Would...

- Design a basic user system with registration, login, and subscriptions  
- Replace log-based notifications with real-time email or webhook integrations  
- Build a front-end UI for managing and viewing events  
- Improve filtering with date ranges and multiple field criteria  
- Migrate notified event tracking to Redis or Postgres  
- Deploy the app publicly using platforms 
