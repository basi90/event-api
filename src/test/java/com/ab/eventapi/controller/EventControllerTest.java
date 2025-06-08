package com.ab.eventapi.controller;

import com.ab.eventapi.model.Event;
import com.ab.eventapi.repository.EventRepository;
import com.ab.eventapi.service.mapper.dto.event.EventInputDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventControllerTest {

    private LocalDateTime fixedDateTime;

    @LocalServerPort
    private int port;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        fixedDateTime = LocalDateTime.of(2025, 7, 7, 10, 0);

        Event event = new Event("Title", "desc", fixedDateTime);
        eventRepository.save(event);
    }

    @Test
    void testCreateEvent_withValidInput_returns201() {
        EventInputDto input = new EventInputDto(
                "title",
                "description",
                fixedDateTime
        );

        given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/events")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("title", equalTo("title"))
                .body("description", equalTo("description"));
    }

    @Test
    void testCreateEvent_withTooShortTitle_throwsInvalidInputException() {
        EventInputDto dto = new EventInputDto(
                "te", // too short
                "description",
                fixedDateTime
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/events")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("Title must be at least 3 characters"));
    }

    @Test
    void testCreateEvent_withTooShortDescription_throwsInvalidInputException() {
        EventInputDto dto = new EventInputDto(
                "test", // too short
                "de",
                fixedDateTime
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/events")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("Description must be at least 3 characters"));
    }

    @Test
    void testCreateEvent_withInvalidInput_returns400() {
        EventInputDto dto = new EventInputDto(
                "",
                "",
                LocalDateTime.now().minusDays(1)
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/events")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("title"))
                .body("message", containsString("description"))
                .body("message", containsString("future"));
    }

    @Test
    void testGetAllEvents_returnsList() {
        given()
                .when()
                .get("/events")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void testGetAllEvents_withFilterAndOrder_returnsFilteredList() {
        given()
                .queryParam("title", "title") // use part of an existing title
                .queryParam("order", "DESC")
                .when()
                .get("/events")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].title", containsStringIgnoringCase("title"));
    }

    @Test
    void testGetEventById_whenNotFound_returns404() {
        given()
                .when()
                .get("/events/99")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", containsString("not found"));
    }
}
