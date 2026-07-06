package com.demo.ecommerce.infrastructure.exception.token;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenNotValidExceptionTest {

    @Test
    void shouldExposeCurrentMessage() {
        TokenNotValidException exception = new TokenNotValidException();

        assertThat(exception).hasMessage("Token not valid");
    }
}
