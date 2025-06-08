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
    void whenGetFilteredEventsWithAscOrder_thenReturnSortedList() {
        Event e1 = new Event("Event1", "desc1", fixedDateTime);
        Event e2 = new Event("Event2", "desc2", fixedDateTime.plusDays(1));
        List<Event> unsorted = List.of(e2, e1); // unsorted on purpose

        when(eventRepository.findAll()).thenReturn(unsorted);

        List<EventListDto> expected = List.of(
                new EventListDto("Event1", fixedDateTime),
                new EventListDto("Event2", fixedDateTime.plusDays(1))
        );

        when(eventMapper.convertEventListIntoEventListDtoList(List.of(e1, e2))).thenReturn(expected);

        List<EventListDto> result = eventService.getFilteredEvents(null, "ASC");

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    void whenGetFilteredEventsWithDescOrder_thenReturnSortedListReversed() {
        Event e1 = new Event("Event1", "desc1", fixedDateTime);
        Event e2 = new Event("Event2", "desc2", fixedDateTime.plusDays(1));
        List<Event> unsorted = List.of(e1, e2); // unsorted on purpose

        when(eventRepository.findAll()).thenReturn(unsorted);

        List<EventListDto> expected = List.of(
                new EventListDto("Event2", fixedDateTime.plusDays(1)),
                new EventListDto("Event1", fixedDateTime)
        );

        when(eventMapper.convertEventListIntoEventListDtoList(List.of(e2, e1))).thenReturn(expected);

        List<EventListDto> result = eventService.getFilteredEvents(null, "DESC");

        assertThat(result).containsExactlyElementsOf(expected);
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

    @Test
    void whenEventsStartInLessThanFiveMinutes_thenTheyAreIncludedInNotification() {
        LocalDateTime now = LocalDateTime.of(2025, 7, 7, 10, 0);
        LocalDateTime withinThreshold = now.plusMinutes(4).plusSeconds(58);
        LocalDateTime past = now.minusMinutes(1);
        LocalDateTime tooFar = now.plusMinutes(10);

        Event upcomingEvent = new Event("Soon", "desc", withinThreshold);
        Event pastEvent = new Event("Past", "desc", past);
        Event futureEvent = new Event("Far", "desc", tooFar);

        when(eventRepository.findAll()).thenReturn(List.of(upcomingEvent, pastEvent, futureEvent));

        EventService testService = new EventService(eventRepository, eventMapper) {
            @Override
            protected void logNotifications(List<Event> events) {
                assertThat(events)
                        .hasSize(1)
                        .extracting(Event::getTitle)
                        .containsExactly("Soon");
            }

            @Override
            protected List<Event> getEventsStartingBefore(LocalDateTime now, LocalDateTime threshold) {
                return super.getEventsStartingBefore(now, threshold);
            }

            @Override
            public void notifyUpcomingEvents() {
                LocalDateTime now = LocalDateTime.of(2025, 7, 7, 10, 0);
                LocalDateTime threshold = now.plusMinutes(4).plusSeconds(59);
                List<Event> upcomingEvents = getEventsStartingBefore(now, threshold);
                logNotifications(upcomingEvents);
            }
        };

        testService.notifyUpcomingEvents();
    }
}
