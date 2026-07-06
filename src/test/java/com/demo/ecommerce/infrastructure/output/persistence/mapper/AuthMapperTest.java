package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.infrastructure.output.persistence.entity.AuthEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthMapperTest {

    private final AuthMapper mapper = Mappers.getMapper(AuthMapper.class);

    @Test
    void shouldMapDomainToEntity() {
        UUID userId = UUID.randomUUID();
        RefreshToken token = RefreshToken.reconstistute(UUID.randomUUID(), "refresh-token", userId, Instant.now().plusSeconds(60));

        AuthEntity entity = mapper.toEntity(token);

        assertThat(entity.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(entity.getUser()).isNotNull();
        assertThat(entity.getUser().getId()).isEqualTo(userId);
        assertThat(entity.isRevoked()).isFalse();
    }

    @Test
    void shouldMapEntityToDomainIgnoringPersistedRevokedFlag() {
        UUID userId = UUID.randomUUID();
        AuthEntity entity = new AuthEntity(
                UUID.randomUUID(),
                "refresh-token",
                Instant.now().plusSeconds(60),
                LocalDateTime.now(),
                true,
                new UserEntity(userId, "John", "Doe", "john@example.com", "hashed", Rols.BUYER.name())
        );

        RefreshToken token = mapper.toDomain(entity);

        assertThat(token.getToken()).isEqualTo("refresh-token");
        assertThat(token.getUserId()).isEqualTo(userId);
        assertThat(token.isRevoked()).isFalse();
    }
}
