package com.demo.ecommerce.domain.model.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Getter
    private UUID id;

    @Getter
    private String token;

    @Getter
    private UUID userId;

    @Getter
    private Instant expiresAt;

    private boolean revoked;

    public static RefreshToken create (UUID userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.token = UUID.randomUUID().toString();
        refreshToken.userId = userId;
        refreshToken.expiresAt = Instant.now().plusMillis(1800000);
        refreshToken.revoked = false;

        return refreshToken;
    }

    public static RefreshToken reconstistute (UUID id,String token, UUID userId, Instant expiresAt){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.id = id;
        refreshToken.token = token;
        refreshToken.userId = userId;
        refreshToken.expiresAt = expiresAt;
        refreshToken.revoked = refreshToken.isExpired(expiresAt);

        return refreshToken;
    }

    public boolean isRevoked() {
        return this.revoked;
    }

    public boolean isExpired(){
        return this.expiresAt.isBefore(Instant.now());
    }

    private boolean isExpired(Instant date){return date.isBefore(Instant.now());}

}
