package com.ab.eventapi.repository;

import com.ab.eventapi.model.Event;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

// Temporary DB
@Repository
public class InMemoryEventRepository {
    private final HashMap<Long, Event> events;

    public InMemoryEventRepository() {
        this.events = new HashMap<>();
        populateEvents();
    }

    //  In memory db methods
    public Collection<Event> getAllEvents() {
        return this.events.values();
    }

    public void saveEvent(Event event) {
        this.events.put(event.getId(), event);
    }

    public boolean deleteEvent(Long eventId) {
        return this.events.remove(eventId) != null;
    }

    public Optional<Event> getEventById(Long eventId) {
        return Optional.ofNullable(this.events.get(eventId));
    }

    // Private method to populate in memory db
    private void populateEvents() {
        LocalDateTime first = LocalDateTime.of(2025, 6, 7, 10, 0, 0);
        LocalDateTime second = first.plusHours(1);
        LocalDateTime third = first.plusHours(2);
        LocalDateTime fourth = first.plusHours(3);

        Event event1 = new Event("Musical", "Short description", first);
        Event event2 = new Event("Concert", "Medium description", second);
        Event event3 = new Event("Wedding", "Long description", third);
        Event event4 = new Event("Carnival", "Brief description", fourth);

        this.events.put(event1.getId(), event1);
        this.events.put(event2.getId(), event2);
        this.events.put(event3.getId(), event3);
        this.events.put(event4.getId(), event4);
    }
}
