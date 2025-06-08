package com.ab.eventapi.service;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.repository.EventRepository;
import com.ab.eventapi.service.mapper.EventMapper;
import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import com.ab.eventapi.service.mapper.dto.event.EventListDto;
import com.ab.eventapi.service.mapper.dto.event.EventOutputDto;
import com.ab.eventapi.utility.exception.EventNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    private LocalDateTime fixedDateTime;

    @BeforeEach
    void setUp() {
        fixedDateTime = LocalDateTime.of(2025, 7, 7, 10, 0);
    }

    @Test
    void whenCreateEvent_thenSaveAndReturnDto() {
        EventInputDto inputDto = new EventInputDto(
                "title",
                "description",
                fixedDateTime
        );

        Event event = new Event(
                "title",
                "description",
                fixedDateTime
        );

        EventOutputDto outputDto = new EventOutputDto(
                1L,
                "title",
                "description",
                fixedDateTime
        );

        when(eventMapper.convertDtoToEvent(inputDto)).thenReturn(event);
        when(eventMapper.convertEventIntoDto(event)).thenReturn(outputDto);

        EventOutputDto result = eventService.createEvent(inputDto);

        assertThat(result).isEqualTo(outputDto);
        verify(eventRepository).save(event);
    }

    @Test
    void whenGetAllEvents_thenReturnListOfEventListDto() {
        List<Event> events = List.of(new Event(
                "title",
                "description",
                fixedDateTime
            ),
            new Event(
                    "title2",
                    "description2",
                    fixedDateTime
            )
        );
        List<EventListDto> expectedDtos = List.of(
            new EventListDto(
                    "title",
                    fixedDateTime
            ),
            new EventListDto(
                    "title2",
                    fixedDateTime
            )
        );

        when(eventRepository.findAll()).thenReturn(events);
        when(eventMapper.convertEventListIntoEventListDtoList(events)).thenReturn(expectedDtos);

        List<EventListDto> result = eventService.getAllEvents();

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedDtos);
    }

    @Test
    void givenValidId_whenGetEventById_thenReturnDto() {
        Long id = 1L;
        Event event = new Event(
                "title",
                "description",
                fixedDateTime
        );
        EventOutputDto dto = new EventOutputDto(
                1L,
                "title",
                "description",
                fixedDateTime
        );

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(eventMapper.convertEventIntoDto(event)).thenReturn(dto);

        EventOutputDto result = eventService.getEventById(id);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void givenInvalidId_whenGetEventById_thenThrowException() {
        Long id = 99L;

        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            eventService.getEventById(id);
        });
    }
}
