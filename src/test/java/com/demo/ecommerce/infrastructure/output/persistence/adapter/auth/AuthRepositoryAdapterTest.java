package com.demo.ecommerce.infrastructure.output.persistence.adapter.auth;

import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.infrastructure.output.persistence.entity.AuthEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.AuthMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthRepositoryAdapterTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private AuthMapper authMapper;

    @InjectMocks
    private AuthRepositoryAdapter adapter;

    @Test
    void shouldSaveToken() {
        RefreshToken token = RefreshToken.reconstistute(UUID.randomUUID(), "refresh-token", UUID.randomUUID(), Instant.now().plusSeconds(60));
        AuthEntity entity = new AuthEntity();

        when(authMapper.toEntity(token)).thenReturn(entity);

        adapter.save(token);

        verify(authRepository).save(entity);
    }

    @Test
    void shouldUpdateExistingTokenWithoutCallingSave() {
        UUID userId = UUID.randomUUID();
        RefreshToken token = RefreshToken.reconstistute(UUID.randomUUID(), "new-token", userId, Instant.now().plusSeconds(60));
        AuthEntity entity = new AuthEntity(UUID.randomUUID(), "old-token", Instant.now().plusSeconds(30), LocalDateTime.now(), false, new UserEntity(userId, "John", "Doe", "john@example.com", "hashed", "BUYER"));

        when(authRepository.findByUserId(userId)).thenReturn(Optional.of(entity));

        adapter.update(token);

        verify(authMapper).updateEntityFromDomain(token, entity);
        verify(authRepository, never()).save(entity);
    }

    @Test
    void shouldFindByToken() {
        RefreshToken token = RefreshToken.reconstistute(UUID.randomUUID(), "refresh-token", UUID.randomUUID(), Instant.now().plusSeconds(60));
        AuthEntity entity = new AuthEntity();

        when(authRepository.findByRefreshToken("refresh-token")).thenReturn(Optional.of(entity));
        when(authMapper.toDomain(entity)).thenReturn(token);

        Optional<RefreshToken> result = adapter.findByToken("refresh-token");

        assertThat(result).contains(token);
    }

    @Test
    void shouldRevokeByToken() {
        AuthEntity entity = new AuthEntity(UUID.randomUUID(), "refresh-token", Instant.now().plusSeconds(60), LocalDateTime.now(), false, new UserEntity());
        when(authRepository.findByRefreshToken("refresh-token")).thenReturn(Optional.of(entity));

        adapter.revokeByToken("refresh-token");

        assertThat(entity.isRevoked()).isTrue();
        verify(authRepository).save(entity);
    }
}
