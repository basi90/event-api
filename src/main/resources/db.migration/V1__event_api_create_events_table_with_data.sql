-- SEQUENCE
CREATE SEQUENCE event_seq START WITH 1 INCREMENT BY 1;

-- EVENTS
CREATE TABLE events (
    id BIGINT PRIMARY KEY DEFAULT nextval(event_seq),
    title VARCHAR(50) NOT NULL,
    description TEXT NOT NUll,
    startsAt TIMESTAMP NOT NULL
)