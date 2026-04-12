package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.auth.RefreshToken;

import java.util.Optional;

public interface AuthRepositoryPort {

    void save (RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void revokeByToken(String token);
    void update(RefreshToken token);


}
