package com.demo.ecommerce.infrastructure.output.persistence.adapter.user;

import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryAdapter adapter;

    @Test
    void shouldSaveUser() {
        User user = User.reconstitute(UUID.randomUUID(), new UserName("John", "Doe"), Email.of("john@example.com"), HashedPassword.of("hashed-password"), Rols.BUYER);
        UserEntity entity = new UserEntity();

        when(userMapper.toEntity(user)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toDomain(entity)).thenReturn(user);

        User result = adapter.save(user);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldGetByEmail() {
        User user = User.reconstitute(UUID.randomUUID(), new UserName("John", "Doe"), Email.of("john@example.com"), HashedPassword.of("hashed-password"), Rols.BUYER);
        UserEntity entity = new UserEntity();

        when(userRepository.findByEmail("john@example.com")).thenReturn(entity);
        when(userMapper.toDomain(entity)).thenReturn(user);

        Optional<User> result = adapter.getByEmail("john@example.com");

        assertThat(result).contains(user);
    }

    @Test
    void shouldGetById() {
        UUID userId = UUID.randomUUID();
        User user = User.reconstitute(userId, new UserName("John", "Doe"), Email.of("john@example.com"), HashedPassword.of("hashed-password"), Rols.BUYER);
        UserEntity entity = new UserEntity();

        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        when(userMapper.toDomain(entity)).thenReturn(user);

        Optional<User> result = adapter.getById(userId);

        assertThat(result).contains(user);
    }
}
