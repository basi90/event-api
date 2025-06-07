package com.ab.eventapi.service.mapper.dto;

import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import com.ab.eventapi.service.mapper.dto.event.EventListDto;
import com.ab.eventapi.service.mapper.dto.event.EventOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@JsonTest
public class EventDtosTest {

    @Autowired
    private JacksonTester<EventInputDto> inputJson;

    @Autowired
    private JacksonTester<EventOutputDto> outputJson;

    @Autowired
    private JacksonTester<EventListDto> toListJson;

    private LocalDateTime fixedDateTime;

    @BeforeEach
    void setUp() {
        fixedDateTime = LocalDateTime.of(2025, 7, 7, 10, 0);
    }

    @Test
    void testSerializeInput() throws Exception {

        EventInputDto input = new EventInputDto("title", "description", fixedDateTime);

        String eventJson = """
                {
                "title": "title",
                "description": "description",
                "startsAt": "2025-07-07T10:00:00"
                }
                """;

        assertThat(inputJson.write(input)).isEqualToJson(eventJson);
    }

    @Test
    void testSerializeOutput() throws Exception {
        EventOutputDto output = new EventOutputDto(1L, "title", "description", fixedDateTime);

        String eventJson = """
                {
                "id": 1,
                "title": "title",
                "description": "description",
                "startsAt": "2025-07-07T10:00:00"
                }
                """;

        assertThat(outputJson.write(output)).isEqualToJson(eventJson);
    }

    @Test
    void testSerializeListElement() throws Exception {
        EventListDto listElement = new EventListDto("title", fixedDateTime);

        String eventJson = """
                {
                "title": "title",
                "startsAt": "2025-07-07T10:00:00"
                }
                """;

        assertThat(toListJson.write(listElement)).isEqualToJson(eventJson);
    }
}
