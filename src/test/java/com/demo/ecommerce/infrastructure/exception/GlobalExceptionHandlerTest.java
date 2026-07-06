package com.demo.ecommerce.infrastructure.exception;

import com.demo.ecommerce.domain.exception.auth.InvalidCredentialException;
import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.infrastructure.exception.token.TokenExpirationException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleValidationExceptions() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        when(request.getRequestURI()).thenReturn("/api/v1/products");
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("request", "name", "Name is required"),
                new FieldError("request", "name", "Name must be between 3 and 50 characters long")
        ));

        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("The request contains invalid fields.");
        assertThat(response.getBody().getErrors()).containsKey("name");
        assertThat(response.getBody().getErrors().get("name")).containsExactly(
                "Name is required",
                "Name must be between 3 and 50 characters long"
        );
    }

    @Test
    void shouldHandleInvalidCredentialException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/auth/login");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidCredentialException(new InvalidCredentialException(), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("invalid credentials");
    }

    @Test
    void shouldHandleTokenExpirationException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/auth/refresh");

        ResponseEntity<ErrorResponse> response = handler.handleTokenExpirationException(new TokenExpirationException(), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("Toke expiated");
    }

    @Test
    void shouldHandleInvalidSort() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/products/all");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidSort(new InvalidDataAccessApiUsageException("bad sort"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("Invalid sort parameter");
    }

    @Test
    void shouldHandleNotFoundException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/brands/99");

        ResponseEntity<ErrorResponse> response = handler.handleNotFound(new NotFoundException("Brand not found"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("Brand not found");
    }

    @Test
    void shouldHandleInvalidValueObjectException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/brands");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidValueObject(new InvalidValueObjectException("brand name cannot be null or empty"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("brand name cannot be null or empty");
    }

    @Test
    void shouldHandleUnexpectedException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/test");

        ResponseEntity<ErrorResponse> response = handler.handleException(new RuntimeException("boom"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDetail()).isEqualTo("An unexpected error occurred");
    }
}
