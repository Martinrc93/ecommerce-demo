package com.demo.ecommerce.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(NON_NULL)
public class ErrorResponse {

    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;

    private LocalDateTime timestamp;

    private Map<String, List<String>> errors;

    public ErrorResponse(HttpStatus status, String detail, String instance) {
        this(status, detail, instance, null);
    }

    public ErrorResponse(HttpStatus status, String detail, String instance,Map<String, List<String>> errors) {
        this.type = "https://localhost:8080/" + status.value();
        this.title = status.getReasonPhrase();
        this.status = status.value();
        this.detail = detail;
        this.instance = instance;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

}
