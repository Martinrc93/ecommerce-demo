package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

    User save (User user);
    Optional<User> getByEmail (String email);
    Optional<User> getById (UUID id);

}
