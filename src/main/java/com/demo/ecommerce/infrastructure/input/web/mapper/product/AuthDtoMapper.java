package com.demo.ecommerce.infrastructure.input.web.mapper.product;

import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthDtoMapper {

    LoginCommand toCommand(LoginRequest request);
}
