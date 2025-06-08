package com.ab.eventapi.repository;

import com.ab.eventapi.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private LocalDateTime fixedDateTime;

    @BeforeEach
    void setUp() {
        fixedDateTime = LocalDateTime.of(2025, 7, 7, 10, 0);

        eventRepository.save(new Event("Spring Boot Meetup", "desc", fixedDateTime));
        eventRepository.save(new Event("Kotlin Workshop", "desc", fixedDateTime.plusDays(1)));
        eventRepository.save(new Event("Bootcamp", "desc", fixedDateTime.plusDays(2)));
    }

    @Test
    void whenFindByTitleContainingIgnoreCase_thenReturnMatchingEvents() {
        List<Event> result = eventRepository.findByTitleContainingIgnoreCase("boot");

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Event::getTitle)
                .anyMatch(title -> title.toLowerCase().contains("boot"));
    }

    @Test
    void whenFindByTitleContainingIgnoreCaseWithNoMatch_thenReturnEmptyList() {
        List<Event> result = eventRepository.findByTitleContainingIgnoreCase("nonexistent");

        assertThat(result).isEmpty();
    }
}
