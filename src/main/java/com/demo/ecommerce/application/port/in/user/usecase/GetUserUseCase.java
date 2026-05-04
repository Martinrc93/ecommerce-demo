package com.demo.ecommerce.application.port.in.user.usecase;

import com.demo.ecommerce.domain.model.user.User;

import java.util.UUID;

public interface GetUserUseCase {

    User getById (UUID userID);
}
