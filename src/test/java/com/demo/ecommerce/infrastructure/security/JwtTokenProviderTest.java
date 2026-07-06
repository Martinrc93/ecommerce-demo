package com.demo.ecommerce.infrastructure.security;

import com.demo.ecommerce.domain.exception.auth.InvalidTokenException;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import com.demo.ecommerce.infrastructure.exception.token.TokenExpirationException;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenProviderTest {

    private static final String SECRET = "dGhpcy1pcy1hLXN1ZmZpY2llbnRseS1sb25nLXNlY3JldC1rZXktZm9yLWp3dA==";

    @Test
    void shouldGenerateAndReadValidAccessToken() {
        JwtTokenProvider provider = providerWith(60_000L, 1_800_000L);
        User user = sampleUser();

        String token = provider.generateAccessToken(user);

        assertTrue(provider.validateToken(token));
        assertEquals(user.getId().toString(), provider.extractUserId(token));
        assertEquals(List.of("ROLE_ADMIN"), provider.extractRoles(token));
    }

    @Test
    void shouldExposeRefreshTokenDurationFromConfiguration() {
        JwtTokenProvider provider = providerWith(60_000L, 1_800_000L);

        assertEquals(Duration.ofMinutes(30), provider.getRefreshTokenDuration());
    }

    @Test
    void shouldThrowInvalidTokenForMalformedToken() {
        JwtTokenProvider provider = providerWith(60_000L, 1_800_000L);

        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> provider.validateToken("not-a-jwt"));

        assertEquals("invalid token", exception.getMessage());
    }

    @Test
    void shouldThrowTokenExpirationForExpiredToken() {
        JwtTokenProvider provider = providerWith(-1_000L, 1_800_000L);
        User user = sampleUser();
        String expiredToken = provider.generateAccessToken(user);

        TokenExpirationException exception = assertThrows(TokenExpirationException.class,
                () -> provider.validateToken(expiredToken));

        assertEquals("Toke expiated", exception.getMessage());
    }

    private static JwtTokenProvider providerWith(long accessTokenExpiration, long refreshTokenExpiration) {
        JwtTokenProvider provider = new JwtTokenProvider();
        ReflectionTestUtils.setField(provider, "secret", SECRET);
        ReflectionTestUtils.setField(provider, "accessTokenExpiration", accessTokenExpiration);
        ReflectionTestUtils.setField(provider, "refreshTokenExpiration", refreshTokenExpiration);
        return provider;
    }

    private static User sampleUser() {
        return User.reconstitute(
                UUID.randomUUID(),
                new UserName("John", "Doe"),
                Email.of("john@example.com"),
                HashedPassword.of("password123"),
                Rols.ADMIN
        );
    }
}
