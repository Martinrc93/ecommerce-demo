package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.usecase.AuthUseCase;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.RefreshRequest;
import com.demo.ecommerce.infrastructure.input.web.mapper.AuthDtoMapper;
import com.demo.ecommerce.infrastructure.security.CookieTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "Endpoints for login, logout, and token refresh operations")
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthUseCase authService;
    private final AuthDtoMapper authMapper;
    private final CookieTokenService cookieTokenService;

    @Operation(summary = "Log in", description = "Validates user credentials and returns session tokens in HttpOnly cookies.")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {

        LoginCommand command = authMapper.toCommand(request);

        AuthResponse tokens = authService.login(command);

        ResponseCookie accessToken = cookieTokenService.buildAccessTokenCookie(tokens.accessToken());
        ResponseCookie refreshToken = cookieTokenService.buildRefreshTokenCookie(tokens.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,accessToken.toString())
                .header(HttpHeaders.SET_COOKIE,refreshToken.toString())
                .build();
    }

    @Operation(summary = "Refresh access token", description = "Generates new tokens from a valid refresh token and updates the cookies.")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshRequest refreshToken)
    {
        AuthResponse tokens = authService.refresh(refreshToken.refreshToken());

        ResponseCookie accessToken = cookieTokenService.buildAccessTokenCookie(tokens.accessToken());
        ResponseCookie refreshTokenCookie = cookieTokenService.buildRefreshTokenCookie(tokens.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,accessToken.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(tokens);
    }

    @Operation(summary = "Log out", description = "Invalidates the current refresh token to safely end the user session.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequest refreshToken) {
        authService.logout(authMapper.toCommand(refreshToken));
        return ResponseEntity.noContent().build();
    }

}