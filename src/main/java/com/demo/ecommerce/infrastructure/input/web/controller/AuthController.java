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

@Tag(name = "Autenticación (Auth)", description = "Endpoints para el inicio y cierre de sesión, y renovación de tokens")
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthUseCase authService;
    private final AuthDtoMapper authMapper;
    private final CookieTokenService cookieTokenService;

    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales del usuario y devuelve tokens de sesión en cookies HttpOnly.")
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

    @Operation(summary = "Renovar token de acceso", description = "Genera nuevos tokens a partir de un refreshToken válido y los actualiza en las cookies.")
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

    @Operation(summary = "Cerrar sesión", description = "Invalida el refreshToken actual para finalizar la sesión del usuario de forma segura.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequest refreshToken) {
        authService.logout(authMapper.toCommand(refreshToken));
        return ResponseEntity.noContent().build();
    }

}