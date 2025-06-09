# Event API

A modular Spring Boot application to manage and schedule events. It supports creating events, retrieving them by ID, filtering by title and start time, and includes a scheduled task to notify (via logging) when events are about to begin.

---

## Features

- Create and store events with title, description, and start time  
- Filter events by title and order by start time (ASC/DESC)  
- Scheduled background check for events starting within minutes
- Database migration handled via Flyway  
- Docker + Docker Compose setup  
- Swagger UI available for testing endpoints  

---

## Getting Started

### Clone the repository and enter its directory
```bash
git clone https://github.com/basi90/event-api.git
cd event-api
```

### Launch Docker and run
```bash
docker compose up --build
```
This command will build the Spring Boot application, start the PostgreSQL database container, apply the Flyway migrations, and run the app at:

http://localhost:8080

To explore the API, open your browser at:

http://localhost:8080/swagger-ui/index.html

## Future Improvements

- **Pagination**: Support for paginated responses on event listing  
- **Advanced Filtering**: Add filters for description, date range, and combined fields  
- **Persistent Notification Tracking**: Store notified event IDs in a database or distributed cache (e.g. Redis)  
- **Authentication and Authorization**: Add user login and role-based access control  
- **Webhook/Event Integration**: Send real-time notifications via email, Slack, or external webhooks  
- **Admin UI Dashboard**: A web interface for managing and monitoring events  
