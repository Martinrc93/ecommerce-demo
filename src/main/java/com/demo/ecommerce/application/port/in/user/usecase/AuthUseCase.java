package com.demo.ecommerce.application.port.in.user.usecase;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.input.web.dto.RefreshRequest;

public interface AuthUseCase {
    AuthResponse login(LoginCommand command);
    AuthResponse refresh(String token);
    void logout(RefreshRequest command);
    AuthResponse generateToken(User user);
}
