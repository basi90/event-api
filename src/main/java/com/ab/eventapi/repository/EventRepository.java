package com.ab.eventapi.repository;

import com.ab.eventapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
