package com.demo.ecommerce.infrastructure.output.persistence.adapter.user;

import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        return userMapper.toDomain(userRepository.save(userEntity));
    }

    @Override
    public Optional<User> getByEmail(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);
        User user = userMapper.toDomain(userEntity);

        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> getById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        User user = userMapper.toDomain(userEntity);
        return user != null ? Optional.of(user) : Optional.empty();
    }
}
