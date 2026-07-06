package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.command.RefreshCommand;
import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.exception.auth.InvalidCredentialException;
import com.demo.ecommerce.domain.exception.auth.InvalidTokenException;
import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Test
    void shouldLoginAndGenerateTokensWhenCredentialsAreValid() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        User user = sampleUser();
        when(userRepository.getByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateAccessToken(user)).thenReturn("access-token");
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        AuthResponse response = authService.login(new LoginCommand("john@example.com", "password123"));

        assertEquals("access-token", response.accessToken());
        assertNotNull(response.refreshToken());
        verify(jwtTokenProvider).generateAccessToken(user);
        ArgumentCaptor<RefreshToken> refreshTokenCaptor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(authRepository).update(refreshTokenCaptor.capture());
        assertEquals(user.getId(), refreshTokenCaptor.getValue().getUserId());
    }

    @Test
    void shouldThrowInvalidCredentialWhenUserDoesNotExist() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        when(userRepository.getByEmail("missing@example.com")).thenReturn(Optional.empty());
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        assertThrows(InvalidCredentialException.class,
                () -> authService.login(new LoginCommand("missing@example.com", "password123")));

        verify(jwtTokenProvider, never()).generateAccessToken(any());
        verify(authRepository, never()).update(any());
    }

    @Test
    void shouldThrowInvalidCredentialWhenPasswordDoesNotMatch() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        when(userRepository.getByEmail("john@example.com")).thenReturn(Optional.of(sampleUser()));
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        assertThrows(InvalidCredentialException.class,
                () -> authService.login(new LoginCommand("john@example.com", "wrong-password")));

        verify(jwtTokenProvider, never()).generateAccessToken(any());
        verify(authRepository, never()).update(any());
    }

    @Test
    void shouldRefreshAndRevokePreviousTokenWhenTokenIsValid() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        User user = sampleUser();
        RefreshToken refreshToken = RefreshToken.reconstistute(
                UUID.randomUUID(),
                "refresh-token",
                user.getId(),
                Instant.now().plusSeconds(300)
        );
        when(authRepository.findByToken("refresh-token")).thenReturn(Optional.of(refreshToken));
        when(userRepository.getById(user.getId())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateAccessToken(user)).thenReturn("new-access-token");
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        AuthResponse response = authService.refresh("refresh-token");

        assertEquals("new-access-token", response.accessToken());
        assertNotNull(response.refreshToken());
        verify(authRepository).revokeByToken("refresh-token");
        verify(jwtTokenProvider).generateAccessToken(user);
    }

    @Test
    void shouldThrowInvalidTokenWhenRefreshTokenDoesNotExist() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        when(authRepository.findByToken("missing-token")).thenReturn(Optional.empty());
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        assertThrows(InvalidTokenException.class, () -> authService.refresh("missing-token"));

        verify(authRepository, never()).revokeByToken(anyString());
    }

    @Test
    void shouldThrowInvalidTokenWhenRefreshTokenIsExpired() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        RefreshToken refreshToken = RefreshToken.reconstistute(
                UUID.randomUUID(),
                "expired-token",
                UUID.randomUUID(),
                Instant.now().minusSeconds(60)
        );
        when(authRepository.findByToken("expired-token")).thenReturn(Optional.of(refreshToken));
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        assertThrows(InvalidTokenException.class, () -> authService.refresh("expired-token"));

        verify(authRepository, never()).revokeByToken(anyString());
    }

    @Test
    void shouldThrowInvalidTokenWhenRefreshTokenUserDoesNotExist() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        User user = sampleUser();
        RefreshToken refreshToken = RefreshToken.reconstistute(
                UUID.randomUUID(),
                "refresh-token",
                user.getId(),
                Instant.now().plusSeconds(300)
        );
        when(authRepository.findByToken("refresh-token")).thenReturn(Optional.of(refreshToken));
        when(userRepository.getById(user.getId())).thenReturn(Optional.empty());
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        assertThrows(InvalidTokenException.class, () -> authService.refresh("refresh-token"));

        verify(authRepository).revokeByToken("refresh-token");
        verify(jwtTokenProvider, never()).generateAccessToken(any());
    }

    @Test
    void shouldDelegateLogoutToRepository() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        authService.logout(new RefreshCommand("refresh-token"));

        verify(authRepository).revokeByToken("refresh-token");
    }

    @Test
    void shouldGenerateTokensForGivenUser() {
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        AuthRepositoryPort authRepository = mock(AuthRepositoryPort.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        User user = sampleUser();
        when(jwtTokenProvider.generateAccessToken(user)).thenReturn("access-token");
        AuthService authService = new AuthService(userRepository, authRepository, jwtTokenProvider);

        AuthResponse response = authService.generateToken(user);

        assertEquals("access-token", response.accessToken());
        assertNotNull(response.refreshToken());
        verify(jwtTokenProvider).generateAccessToken(same(user));
        verify(authRepository).update(any(RefreshToken.class));
    }

    private static User sampleUser() {
        return User.reconstitute(
                UUID.randomUUID(),
                new UserName("John", "Doe"),
                Email.of("john@example.com"),
                HashedPassword.of("password123"),
                Rols.ADMIN
        );
    }
}
