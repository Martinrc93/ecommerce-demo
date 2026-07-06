package com.demo.ecommerce.application.service.user;

import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetUserServiceTest {

    @Test
    void shouldReturnUserByIdWhenItExists() {
        InMemoryUserRepository repository = new InMemoryUserRepository();
        UUID userId = UUID.randomUUID();
        User stored = User.create("John", "Doe", "john@example.com", "password123");
        repository.byId = Optional.of(stored);
        GetUserService getUserService = new GetUserService(repository);

        User user = getUserService.getById(userId);

        assertSame(stored, user);
        assertEquals(userId, repository.lastRequestedId);
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        InMemoryUserRepository repository = new InMemoryUserRepository();
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        GetUserService getUserService = new GetUserService(repository);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> getUserService.getById(userId));

        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    private static class InMemoryUserRepository implements UserRepositoryPort {
        private Optional<User> byId = Optional.empty();
        private UUID lastRequestedId;

        @Override
        public User save(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<User> getByEmail(String email) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<User> getById(UUID id) {
            this.lastRequestedId = id;
            return byId;
        }
    }
}
