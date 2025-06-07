package com.ab.eventapi.model;

import java.time.LocalDateTime;
import java.util.Objects;

// add annotations for JPA mirroring
public class Event {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startsAt;

    private static long nextId = 1;

    // JPA constructor
    public Event() {
    }

    // In memory db constructor
    public Event(String title, String description, LocalDateTime startsAt) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
    }

    // Full constructor
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
