package com.demo.ecommerce.application.port.in.user.usecase;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.usecase.dto.RegisterResult;

public interface UserRegisterUseCase {

   RegisterResult register (RegisterCommand command);

}