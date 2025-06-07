package com.ab.eventapi.service.mapper.dto;

import java.time.LocalDateTime;

public class EventListDto {
    private String title;
    private String startsAt;

    public EventListDto(String title, String startsAt) {
        this.title = title;
        this.startsAt = startsAt;
    }

    public String getTitle() {
        return title;
    }

    public String getStartsAt() {
        return startsAt;
    }
}
