package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.usecase.AuthUseCase;
import com.demo.ecommerce.infrastructure.config.ApiPaths;
import com.demo.ecommerce.infrastructure.input.web.controller.docs.AuthApiDocs;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.RefreshRequest;
import com.demo.ecommerce.infrastructure.input.web.mapper.AuthDtoMapper;
import com.demo.ecommerce.infrastructure.security.CookieTokenService;
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

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(ApiPaths.AUTH)
@AllArgsConstructor
public class AuthController implements AuthApiDocs {

    private final AuthUseCase authService;
    private final AuthDtoMapper authMapper;
    private final CookieTokenService cookieTokenService;

    @Override
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

    @Override
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

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequest refreshToken) {
        authService.logout(authMapper.toCommand(refreshToken));
        return ResponseEntity.noContent().build();
    }

}
