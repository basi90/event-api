package com.ab.eventapi.service;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.repository.EventRepository;
import com.ab.eventapi.repository.InMemoryEventRepository;
import com.ab.eventapi.service.mapper.EventMapper;
import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import com.ab.eventapi.service.mapper.dto.event.EventListDto;
import com.ab.eventapi.service.mapper.dto.event.EventOutputDto;
import com.ab.eventapi.utility.exception.EventNotFoundException;
import com.ab.eventapi.utility.exception.InvalidInputException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final InMemoryEventRepository inMemoryRepo;
    private  EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(InMemoryEventRepository inMemoryRepo, EventMapper eventMapper) {
        this.inMemoryRepo = inMemoryRepo;
        this.eventMapper = eventMapper;
    }

    public EventOutputDto createEvent(EventInputDto eventInputDto) {
        validateEventInput(eventInputDto);

        Event event = eventMapper.convertDtoToEvent(eventInputDto);
        inMemoryRepo.saveEvent(event);
        return eventMapper.convertEventIntoDto(event);
    }

    public List<EventListDto> getAllEvents() {
        return eventMapper.convertEventListIntoEventListDtoList(inMemoryRepo.getAllEvents());
    }

    public EventOutputDto getEventById(Long id) {
        Event event = inMemoryRepo.getEventById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return eventMapper.convertEventIntoDto(event);
    }

    private void validateEventInput(EventInputDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().length() < 3) {
            throw new InvalidInputException("Title must be at least 3 characters long");
        }
        if (dto.getDescription() == null || dto.getDescription().trim().length() < 3) {
            throw new InvalidInputException("Description must be at least 3 characters long");
        }
    }
}
