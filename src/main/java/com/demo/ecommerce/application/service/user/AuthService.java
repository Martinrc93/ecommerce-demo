package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.command.RefreshCommand;
import com.demo.ecommerce.application.port.in.user.usecase.AuthUseCase;
import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.exception.auth.InvalidCredentialException;
import com.demo.ecommerce.domain.exception.auth.InvalidTokenException;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class  AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final AuthRepositoryPort authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthResponse login(LoginCommand command) {
        User user = userRepository.getByEmail(command.email())
                .orElseThrow(InvalidCredentialException::new);

        if(!user.getPassword().matches(command.password())){
            throw new InvalidCredentialException();
        }
        return generateToken(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse refresh(String token) {
        RefreshToken refreshToken = authRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        if (refreshToken.isRevoked() || refreshToken.isExpired()) {
            throw new InvalidTokenException();
        }
        authRepository.revokeByToken(token);

        User user = userRepository.getById(refreshToken.getUserId())
                .orElseThrow(InvalidTokenException::new);

        return generateToken(user);
    }

    @Override
    @Transactional
    public void logout(RefreshCommand command) {
        authRepository.revokeByToken(command.refreshToken());
    }

    @Override
    @Transactional
    public AuthResponse generateToken(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user);

        RefreshToken refreshToken = RefreshToken.create(user.getId());
        authRepository.update(refreshToken);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

}