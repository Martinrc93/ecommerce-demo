package com.demo.ecommerce.infrastructure.output.persistence.adapter.auth;

import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthRepositoryAdapter implements AuthRepositoryPort {

    private final AuthRepository authRepository;

    @Override
    public void save(RefreshToken token) {

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void revokeByToken(String token) {

    }
}
