package com.ab.eventapi.service;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventNotificationService {

    private final EventRepository eventRepository;
    private final Notifier notifier;

    private final Set<Long> notifiedEventIds = ConcurrentHashMap.newKeySet();

    public EventNotificationService(EventRepository eventRepository, Notifier notifier) {
        this.eventRepository = eventRepository;
        this.notifier = notifier;
    }

    public void checkAndNotifyUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.plusMinutes(4).plusSeconds(59);

        List<Event> upcomingEvents = eventRepository.findAll().stream()
                .filter(event -> {
                    LocalDateTime start = event.getStartsAt();
                    return start.isAfter(now) && !start.isAfter(threshold);
                })
                .filter(event -> !notifiedEventIds.contains(event.getId()))
                .toList();

        upcomingEvents.forEach(this::notifyEvent);
    }

    private void notifyEvent(Event event) {
        notifier.notify(event);
        notifiedEventIds.add(event.getId());
    }

    public void clearNotifiedEvents() {
        notifiedEventIds.clear();
    }

    public Set<Long> getNotifiedEventIds() {
        return Collections.unmodifiableSet(notifiedEventIds);
    }
}
