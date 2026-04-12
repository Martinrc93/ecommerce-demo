package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.usecase.AuthUseCase;
import com.demo.ecommerce.infrastructure.input.web.dto.RefreshRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthUseCase authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginCommand request) { //TODO pasar de logincommand a loginrequest
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshRequest refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken.toString()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequest refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }

}
