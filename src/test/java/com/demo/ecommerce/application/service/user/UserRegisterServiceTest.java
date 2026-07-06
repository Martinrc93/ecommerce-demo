package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.command.RegisterResult;
import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRegisterServiceTest {

    @Test
    void shouldRegisterUserAndReturnIssuedTokens() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);

        User savedUser = User.reconstitute(
                UUID.randomUUID(),
                new UserName("John", "Doe"),
                Email.of("john@example.com"),
                HashedPassword.of("password123"),
                Rols.BUYER
        );

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtTokenProvider.generateAccessToken(savedUser)).thenReturn("access-token");

        UserRegisterService service = new UserRegisterService(userRepository, authRepository, jwtTokenProvider);

        RegisterResult result = service.register(new RegisterCommand(
                "John",
                "Doe",
                "john@example.com",
                "password123"
        ));

        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("access-token", result.accessToken());
        assertNotNull(result.refreshToken());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("John", userCaptor.getValue().getUserName().name());
        assertEquals("Doe", userCaptor.getValue().getUserName().lastName());
        assertEquals("john@example.com", userCaptor.getValue().getEmail().email());
        verify(jwtTokenProvider).generateAccessToken(same(savedUser));

        ArgumentCaptor<RefreshToken> refreshTokenCaptor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(authRepository).save(refreshTokenCaptor.capture());
        assertEquals(savedUser.getId(), refreshTokenCaptor.getValue().getUserId());
    }
}
