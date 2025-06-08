package com.ab.eventapi.controller;

import com.ab.eventapi.service.EventService;
import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import com.ab.eventapi.service.mapper.dto.event.EventListDto;
import com.ab.eventapi.service.mapper.dto.event.EventOutputDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EventOutputDto createEvent(@Valid @RequestBody EventInputDto eventInputDto) {
        logger.info("Received POST /events with payload: {}", eventInputDto);
        return eventService.createEvent(eventInputDto);
    }

    @GetMapping(produces = "application/json")
    public List<EventListDto> getAllEvents() {
        logger.info("Received GET /events");
        return eventService.getAllEvents();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public EventOutputDto getEventById(@PathVariable("id") Long id) {
        logger.info("Received GET /events/{}", id);
        return eventService.getEventById(id);
    }
}
