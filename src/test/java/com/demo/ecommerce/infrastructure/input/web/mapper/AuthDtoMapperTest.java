package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.command.RefreshCommand;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.RefreshRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AuthDtoMapperTest {

    private final AuthDtoMapper mapper = Mappers.getMapper(AuthDtoMapper.class);

    @Test
    void shouldMapLoginRequestToLoginCommand() {
        LoginRequest request = new LoginRequest("john@example.com", "Password1!");

        LoginCommand command = mapper.toCommand(request);

        assertThat(command.email()).isEqualTo("john@example.com");
        assertThat(command.password()).isEqualTo("Password1!");
    }

    @Test
    void shouldMapRefreshRequestToRefreshCommand() {
        RefreshRequest request = new RefreshRequest("refresh-token");

        RefreshCommand command = mapper.toCommand(request);

        assertThat(command.refreshToken()).isEqualTo("refresh-token");
    }
}
