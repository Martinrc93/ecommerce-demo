package com.demo.ecommerce.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationResponse extends ErrorResponse{

    private final Map<String, List<String>> errors;

    public ValidationResponse(HttpStatus status, String message, LocalDateTime timestamp, Map<String, List<String>> errors) {
        super(status, message, timestamp);
        this.errors = errors;
    }

}
