package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapDomainToEntity() {
        User user = User.reconstitute(UUID.randomUUID(), new UserName("John", "Doe"), Email.of("john@example.com"), HashedPassword.of("hashed-password"), Rols.ADMIN);

        UserEntity entity = mapper.toEntity(user);

        assertThat(entity.getName()).isEqualTo("John");
        assertThat(entity.getLastName()).isEqualTo("Doe");
        assertThat(entity.getEmail()).isEqualTo("john@example.com");
        assertThat(entity.getPassword()).isEqualTo(user.getPassword().password());
        assertThat(entity.getRol()).isEqualTo("ADMIN");
    }

    @Test
    void shouldMapEntityToDomain() {
        UserEntity entity = new UserEntity(UUID.randomUUID(), "John", "Doe", "john@example.com", "hashed-password", "ADMIN");

        User user = mapper.toDomain(entity);

        assertThat(user.getUserName().name()).isEqualTo("John");
        assertThat(user.getUserName().lastName()).isEqualTo("Doe");
        assertThat(user.getEmail().email()).isEqualTo("john@example.com");
        assertThat(user.getPassword().password()).isEqualTo("hashed-password");
        assertThat(user.getRole()).isEqualTo(Rols.ADMIN);
    }
}

