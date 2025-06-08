package com.ab.eventapi.service;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.repository.EventRepository;
import com.ab.eventapi.service.mapper.EventMapper;
import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import com.ab.eventapi.service.mapper.dto.event.EventListDto;
import com.ab.eventapi.service.mapper.dto.event.EventOutputDto;
import com.ab.eventapi.utility.exception.EventNotFoundException;
import com.ab.eventapi.utility.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventOutputDto createEvent(EventInputDto eventInputDto) {
        logger.info("Creating event with title: {}", eventInputDto.getTitle());
        validateEventInput(eventInputDto);

        Event event = eventMapper.convertDtoToEvent(eventInputDto);
        eventRepository.save(event);

        logger.debug("Event saved: {}", event);
        return eventMapper.convertEventIntoDto(event);
    }

    public List<EventListDto> getFilteredEvents(String title, String order) {
        logger.info("Fetching events for title: {}", title);
        List<Event> events = fetchEvents(title);
        Comparator<Event> comparator = getDateComparator(order);

        List<Event> sortedEvents = events.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        return eventMapper.convertEventListIntoEventListDtoList(sortedEvents);
    }

    public EventOutputDto getEventById(Long id) {
        logger.info("Fetching event with id: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return eventMapper.convertEventIntoDto(event);
    }

    @Scheduled(fixedRate = 60000)
    public void notifyUpcomingEvents() {
        logger.info("Checking for upcoming events");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeThreshold = now.plusMinutes(4).plusSeconds(59);

        List<Event> upcomingEvents = getEventsStartingBefore(now, timeThreshold);
        logNotifications(upcomingEvents);
    }

    private void validateEventInput(EventInputDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().length() < 3) {
            throw new InvalidInputException("Title must be at least 3 characters long");
        }
        if (dto.getDescription() == null || dto.getDescription().trim().length() < 3) {
            throw new InvalidInputException("Description must be at least 3 characters long");
        }
    }

    private List<Event> fetchEvents(String title) {
        logger.info("Fetching all events with title: {}", title);
        if(title != null && !title.isBlank()) {
            return eventRepository.findByTitleContainingIgnoreCase(title);
        }
        return eventRepository.findAll();
    }

    private Comparator<Event> getDateComparator(String order) {
        Comparator<Event> comparator  = Comparator.comparing(Event::getStartsAt);
        if("DESC".equalsIgnoreCase(order)) {
            return comparator.reversed();
        }
        return comparator;
    }

    protected List<Event> getEventsStartingBefore(LocalDateTime now, LocalDateTime threshold) {
        return eventRepository.findAll().stream()
                .filter(event -> {
                    LocalDateTime start = event.getStartsAt();
                    return start.isAfter(now) && !start.isAfter(threshold);
                })
                .collect(Collectors.toList());
    }

    protected void logNotifications(List<Event> events) {
        events.forEach(event -> {
            logger.warn("Scheduled Notification: Event '{}' is starting at {}", event.getTitle(), event.getStartsAt());
        });
    }
}
