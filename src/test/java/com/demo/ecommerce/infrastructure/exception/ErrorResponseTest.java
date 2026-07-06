package com.demo.ecommerce.infrastructure.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void shouldCreateProblemLikeResponseWithoutErrors() {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request", "/api/v1/test");

        assertThat(response.getType()).isEqualTo("https://localhost:8080/400");
        assertThat(response.getTitle()).isEqualTo("Bad Request");
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getDetail()).isEqualTo("Invalid request");
        assertThat(response.getInstance()).isEqualTo("/api/v1/test");
        assertThat(response.getErrors()).isNull();
        assertThat(response.getTimestamp()).isNotNull();
    }

    @Test
    void shouldCreateProblemLikeResponseWithErrors() {
        Map<String, List<String>> errors = Map.of("email", List.of("Invalid email"));

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "The request contains invalid fields.", "/api/v1/test", errors);

        assertThat(response.getType()).isEqualTo("https://localhost:8080/400");
        assertThat(response.getErrors()).containsEntry("email", List.of("Invalid email"));
    }
}
