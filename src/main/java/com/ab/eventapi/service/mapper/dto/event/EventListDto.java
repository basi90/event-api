package com.ab.eventapi.service.mapper.dto.event;

import java.time.LocalDateTime;

public class EventListDto {
    private String title;
    private LocalDateTime startsAt;

    public EventListDto(String title, LocalDateTime startsAt) {
        this.title = title;
        this.startsAt = startsAt;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }
}
