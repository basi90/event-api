package com.ab.eventapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "event_seq_gen")
    @SequenceGenerator(name = "event_seq_gen", sequenceName = "event_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    public Event() {
    }

    public Event(String title, String description, LocalDateTime startsAt) {
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
    }

    public Event(Long id, String title, String description, LocalDateTime startsAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        // add dateTime to String
        return "Event: " + title;
    }

    // private method to convert the dateTime to String
}
