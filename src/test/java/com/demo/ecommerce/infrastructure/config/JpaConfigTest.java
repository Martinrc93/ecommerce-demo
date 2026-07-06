package com.demo.ecommerce.infrastructure.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JpaConfigTest {

    private final JpaConfig config = new JpaConfig();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnSystemWhenAuthenticationIsMissing() {
        AuditorAware<String> auditorAware = config.auditorProvider();

        assertThat(auditorAware.getCurrentAuditor()).contains("system");
    }

    @Test
    void shouldReturnSystemWhenAuthenticationIsNotAuthenticated() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("john@example.com", null, List.of())
        );
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

        AuditorAware<String> auditorAware = config.auditorProvider();

        assertThat(auditorAware.getCurrentAuditor()).contains("system");
    }

    @Test
    void shouldReturnAuthenticatedUserName() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("john@example.com", null, List.of())
        );

        AuditorAware<String> auditorAware = config.auditorProvider();

        assertThat(auditorAware.getCurrentAuditor()).contains("john@example.com");
    }
}
