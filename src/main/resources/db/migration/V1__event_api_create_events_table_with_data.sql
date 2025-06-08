-- SEQUENCE
CREATE SEQUENCE event_seq START WITH 1 INCREMENT BY 1;

-- EVENTS
CREATE TABLE events (
    id BIGINT PRIMARY KEY DEFAULT nextval('event_seq'),
    title VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    starts_at TIMESTAMP NOT NULL
);

INSERT INTO events (title, description, starts_at) VALUES
('Spring Boot Workshop', 'Learn Spring Boot with hands-on examples.', '2025-07-01 10:00:00'),
('API Design Meetup', 'Discuss RESTful best practices.', '2025-07-02 14:30:00'),
('Java Conference', 'Explore new trends in Java development.', '2025-07-03 09:00:00'),
('Tech Talk', 'Latest in cloud and microservices.', '2025-07-04 11:00:00'),
('Hackathon', 'Collaborate and code for 24 hours.', '2025-07-05 08:00:00'),
('Startup Pitch', 'Pitching event for startups.', '2025-07-06 16:00:00'),
('DevOps Day', 'Focus on CI/CD pipelines.', '2025-07-07 13:00:00'),
('Clean Code Seminar', 'How to write clean and maintainable code.', '2025-07-08 15:00:00'),
('Webinar: Docker Basics', 'Get started with Docker containers.', '2025-07-09 10:30:00'),
('AI in Practice', 'Real-world applications of AI.', '2025-07-10 09:30:00'),
('Tech Career Fair', 'Meet companies hiring tech talent.', '2025-07-11 17:00:00'),
('Frontend Fest', 'React, Vue, and the modern web.', '2025-07-12 12:00:00'),
('Backend Battle', 'Node.js vs Java vs Go.', '2025-07-13 14:00:00'),
('Cybersecurity 101', 'Basics of securing applications.', '2025-07-14 13:30:00'),
('Mobile Dev Talk', 'Developing apps with Flutter and React Native.', '2025-07-15 16:30:00'),
('GraphQL Deep Dive', 'Advanced use of GraphQL APIs.', '2025-07-16 09:00:00'),
('Kubernetes Kickoff', 'Intro to Kubernetes orchestration.', '2025-07-17 11:15:00'),
('SaaS Business Models', 'Building and scaling SaaS.', '2025-07-18 15:45:00'),
('Testing Strategies', 'Unit, integration, and e2e tests.', '2025-07-19 10:00:00'),
('Design Systems Talk', 'Building consistent UI with design systems.', '2025-07-20 14:30:00'),
('Remote Dev Summit', 'Tools and workflows for remote teams.', '2025-07-21 13:00:00'),
('Data Engineering Intro', 'ETL pipelines and data lakes.', '2025-07-22 08:30:00'),
('Cloud Cost Optimization', 'Cut your AWS/GCP bills.', '2025-07-23 12:00:00'),
('Next.js in Production', 'Lessons from real Next.js deployments.', '2025-07-24 09:45:00'),
('Productivity Hacks for Devs', 'Stay focused and ship faster.', '2025-07-25 10:15:00');
