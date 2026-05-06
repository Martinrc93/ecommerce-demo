package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.command.RefreshCommand;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.RefreshRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthDtoMapper {

    RefreshCommand toCommand(RefreshRequest token);

    LoginCommand toCommand(LoginRequest request);
}
