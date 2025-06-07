package com.ab.eventapi.service.mapper.dto;

import java.time.LocalDateTime;

public class EventOutputDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startsAt;

    public EventOutputDto(Long id, String title, String description, LocalDateTime startsAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }
}
