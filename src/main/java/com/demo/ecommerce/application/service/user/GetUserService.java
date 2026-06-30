package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.in.user.usecase.GetUserUseCase;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final UserRepositoryPort repository;

    @Override
    public User getById(UUID userID) {
        return repository.getById(userID).orElseThrow(() -> new NotFoundException("User not found with id: " + userID));
    }
}
