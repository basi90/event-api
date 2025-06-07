package com.ab.eventapi.service.mapper.dto.event;

import java.time.LocalDateTime;

public class EventInputDto {
    private String title;
    private String description;
    private LocalDateTime startsAt;

    public EventInputDto(String title, String description, LocalDateTime startsAt) {
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
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
