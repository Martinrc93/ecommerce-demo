package com.demo.ecommerce.infrastructure.exception.token;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenExpirationExceptionTest {

    @Test
    void shouldExposeCurrentMessage() {
        TokenExpirationException exception = new TokenExpirationException();

        assertThat(exception).hasMessage("Toke expiated");
    }
}
