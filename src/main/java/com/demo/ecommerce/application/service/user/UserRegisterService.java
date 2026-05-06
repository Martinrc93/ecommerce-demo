package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.usecase.UserRegisterUseCase;
import com.demo.ecommerce.application.port.in.user.command.RegisterResult;
import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {

    private final UserRepositoryPort userRepository;
    private final AuthRepositoryPort authRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @Transactional()
    public RegisterResult register(RegisterCommand command) {

        User user = User.create(command.firstName(), command.lastName(), command.email(), command.password());
        User savedUser = userRepository.save(user);
        String accessToken = jwtTokenProvider.generateAccessToken(savedUser);
        RefreshToken refreshToken = RefreshToken.create(savedUser.getId());
        authRepository.save(refreshToken);

        return new RegisterResult(savedUser.getUserName().name(), savedUser.getUserName().lastName(), accessToken,refreshToken.getToken());
    }
}