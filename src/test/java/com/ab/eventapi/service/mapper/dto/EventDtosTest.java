package com.ab.eventapi.service.mapper.dto;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import com.ab.eventapi.service.mapper.dto.event.EventListDto;
import com.ab.eventapi.service.mapper.dto.event.EventOutputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;

@JsonTest
public class EventDtosTest {

    @Autowired
    private JacksonTester<EventInputDto> inputJson;

    @Autowired
    private JacksonTester<EventOutputDto> outputJson;

    @Autowired
    private JacksonTester<EventListDto> toListJson;

    @Test
    void testSerializeInput() throws Exception {
        EventInputDto input = new EventInputDto("title", "description", LocalDateTime.now());
        

    }

    @Test
    void testSerializeToOutput() throws Exception {}

    @Test
    void testSerializeListElement() throws Exception {}
}
