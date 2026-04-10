package com.demo.ecommerce.application.port.in.user.usecase;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;

public interface AuthUseCase {
    AuthResponse login(LoginCommand command);
    AuthResponse refresh(String token);
    void logout(String refreshToken);
}
