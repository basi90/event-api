package com.ab.eventapi.service;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.repository.EventRepository;
import com.ab.eventapi.service.notifier.Notifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventNotificationServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private Notifier notifier;

    @InjectMocks
    private EventNotificationService notificationService;

    @Test
    void whenUpcomingEventsExist_thenTheyAreNotifiedOnlyOnce() {
        LocalDateTime now = LocalDateTime.now();

        Event soon = new Event(1L, "Soon", "desc", now.plusMinutes(4).plusSeconds(30));
        Event past = new Event(2L, "Past", "desc", now.minusMinutes(5));
        Event later = new Event(3L, "Later", "desc", now.plusMinutes(10));

        when(eventRepository.findAll()).thenReturn(List.of(soon, past, later));

        notificationService.checkAndNotifyUpcomingEvents();

        verify(notifier, times(1)).notify(soon);
        assertThat(notificationService.getNotifiedEventIds()).containsExactly(1L);

        notificationService.checkAndNotifyUpcomingEvents();
        verifyNoMoreInteractions(notifier);

        notificationService.clearNotifiedEvents();
        notificationService.checkAndNotifyUpcomingEvents();

        verify(notifier, times(2)).notify(soon);
    }

    @Test
    void whenNoUpcomingEvents_thenNotifierIsNotCalled() {
        LocalDateTime now = LocalDateTime.now();

        Event far = new Event(4L,"Far", "desc", now.plusMinutes(15));
        Event past = new Event(5L,"Past", "desc", now.minusMinutes(10));

        when(eventRepository.findAll()).thenReturn(List.of(far, past));

        notificationService.checkAndNotifyUpcomingEvents();

        verifyNoInteractions(notifier);
        assertThat(notificationService.getNotifiedEventIds()).isEmpty();
    }

}
