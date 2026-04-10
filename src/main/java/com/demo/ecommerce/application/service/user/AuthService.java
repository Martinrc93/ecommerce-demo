package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.usecase.AuthUseCase;
import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final AuthRepositoryPort authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse login(LoginCommand command) {
        User user = userRepository.getByEmail(command.email())
                .orElseThrow(()-> new RuntimeException("invalid credentials")); //TODO personaliza exception.

        if(!user.getPassword().matches(command.password())){
            throw new RuntimeException("invalid credentials"); //TODO personaliza exception.
        }

        return generateToken(user);
    }

    @Override
    public AuthResponse refresh(String token) {
        RefreshToken refreshToken = authRepository.findByToken(token)
                .orElseThrow(()-> new RuntimeException("invalid token")); //TODO exeception

        if(refreshToken.isRevoked() || refreshToken.isExpired()){
            throw new RuntimeException("invalid token"); //TODO exeception
        }

        authRepository.revokeByToken(token);

        User user = userRepository.getById(refreshToken.getId())
                .orElseThrow(()-> new RuntimeException("invalid token")); //TODO exception

        return generateToken(user);
    }

    @Override
    public void logout(String refreshToken) {
        authRepository.revokeByToken(refreshToken);
    }

    private AuthResponse generateToken(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user);

        RefreshToken refreshToken = authRepository.save(RefreshToken.create(user.getId())); //TODO a crete le falta el refresh toke

        return new AuthResponse(accessToken, refreshToken.getToken());
    }
}