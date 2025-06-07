package com.ab.eventapi.service.mapper;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.service.mapper.dto.EventInputDto;
import com.ab.eventapi.service.mapper.dto.EventListDto;
import com.ab.eventapi.service.mapper.dto.EventOutputDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public EventOutputDto convertEventIntoDto(Event event) {
        return new EventOutputDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartsAt()
        );
    }

    public Event convertDtoToEvent(EventInputDto dto) {
        return new Event(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStartsAt()
        );
    }

    public List<EventListDto> convertEventListIntoEventListDtoList(List<Event> eventList) {
        return eventList.stream()
                .map(this::convertEventIntoListDto)
                .collect(Collectors.toList());
    }

    private EventListDto convertEventIntoListDto(Event event) {
        return new EventListDto(event.getTitle(), event.getStartsAt());
    }
}
