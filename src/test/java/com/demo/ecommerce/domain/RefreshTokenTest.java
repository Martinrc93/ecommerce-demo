package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.auth.RefreshToken;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    @Test
    void shouldCreateActiveRefreshTokenWithThirtyMinuteLifetime() {
        UUID userId = UUID.randomUUID();
        Instant beforeCreation = Instant.now();

        RefreshToken refreshToken = RefreshToken.create(userId);

        Instant afterCreation = Instant.now();
        Instant minimumExpectedExpiry = beforeCreation.plus(Duration.ofMinutes(30)).minusSeconds(2);
        Instant maximumExpectedExpiry = afterCreation.plus(Duration.ofMinutes(30)).plusSeconds(2);

        assertNull(refreshToken.getId());
        assertEquals(userId, refreshToken.getUserId());
        assertNotNull(refreshToken.getToken());
        assertDoesNotThrow(() -> UUID.fromString(refreshToken.getToken()));
        assertFalse(refreshToken.isRevoked());
        assertFalse(refreshToken.isExpired());
        assertFalse(refreshToken.getExpiresAt().isBefore(minimumExpectedExpiry));
        assertFalse(refreshToken.getExpiresAt().isAfter(maximumExpectedExpiry));
    }

    @Test
    void shouldReconstituteNonExpiredTokenAsActive() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(5));

        RefreshToken refreshToken = RefreshToken.reconstistute(id, "persisted-token", userId, expiresAt);

        assertEquals(id, refreshToken.getId());
        assertEquals("persisted-token", refreshToken.getToken());
        assertEquals(userId, refreshToken.getUserId());
        assertEquals(expiresAt, refreshToken.getExpiresAt());
        assertFalse(refreshToken.isRevoked());
        assertFalse(refreshToken.isExpired());
    }

    @Test
    void shouldReconstituteExpiredTokenAsRevoked() {
        Instant expiresAt = Instant.now().minus(Duration.ofMinutes(5));

        RefreshToken refreshToken = RefreshToken.reconstistute(UUID.randomUUID(), "expired-token", UUID.randomUUID(), expiresAt);

        assertTrue(refreshToken.isRevoked());
        assertTrue(refreshToken.isExpired());
    }
}
