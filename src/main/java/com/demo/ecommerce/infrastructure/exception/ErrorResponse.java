package com.demo.ecommerce.infrastructure.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    int status;
    String message;
    LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        this.status = status.value();
        this.message = message;
        this.timestamp = timestamp;
    }

}
