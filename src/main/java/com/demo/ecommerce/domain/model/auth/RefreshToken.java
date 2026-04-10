package com.demo.ecommerce.domain.model.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Getter
    private String token;

    @Getter
    private UUID id;

    @Getter
    private Instant expiresAt;

    private boolean revoked;

    public static RefreshToken create (UUID id) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.token = UUID.randomUUID().toString();
        refreshToken.id = id;
        refreshToken.expiresAt = Instant.now().plusMillis(1800000);
        refreshToken.revoked = false;

        return refreshToken;
    }

    public boolean isRevoked() {
        return this.revoked;
    }

    public boolean isExpired(){
        return this.expiresAt.isBefore(Instant.now());
    }

}
