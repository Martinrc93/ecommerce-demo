package com.demo.ecommerce.infrastructure.output.persistence.adapter.auth;

import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.infrastructure.output.persistence.entity.AuthEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.AuthMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthRepositoryAdapter implements AuthRepositoryPort {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;


    @Override
    public void save(RefreshToken token) {
        AuthEntity authEntity = authMapper.toEntity(token);
        authRepository.save(authEntity);
    }

    @Override
    public void update(RefreshToken token) {
        Optional<AuthEntity> authEntity = authRepository.findByUserId(token.getUserId());
        if (authEntity.isPresent()){
            authMapper.updateEntityFromDomain(token, authEntity.get());
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        Optional<AuthEntity> authEntity = authRepository.findByRefreshToken(token);
        return authEntity.map(authMapper::toDomain);
    }

    @Override
    public void revokeByToken(String token) {
        Optional<AuthEntity> authEntity = authRepository.findByRefreshToken(token);
        if (authEntity.isPresent()) {
            authEntity.get().revoke();
            authRepository.save(authEntity.get());
        }
    }

}
