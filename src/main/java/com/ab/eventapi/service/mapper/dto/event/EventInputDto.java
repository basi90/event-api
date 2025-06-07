package com.ab.eventapi.service.mapper.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EventInputDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Start date/time must be provided")
    @Future(message = "Start date/time must be in the future")
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
