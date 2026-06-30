package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateBuyerUserFromRawValues() {
        User user = User.create("John", "Doe", "John.Doe@example.com", "password123");

        assertNull(user.getId());
        assertEquals("John", user.getUserName().name());
        assertEquals("Doe", user.getUserName().lastName());
        assertEquals("john.doe@example.com", user.getEmail().email());
        assertTrue(user.getPassword().matches("password123"));
        assertEquals(Rols.BUYER, user.getRole());
    }

    @Test
    void shouldReconstituteUserWithExplicitRole() {
        UUID id = UUID.randomUUID();
        UserName userName = new UserName("Jane", "Doe");
        Email email = Email.of("jane@example.com");
        HashedPassword password = HashedPassword.of("password123");

        User user = User.reconstitute(id, userName, email, password, Rols.ADMIN);

        assertEquals(id, user.getId());
        assertSame(userName, user.getUserName());
        assertSame(email, user.getEmail());
        assertSame(password, user.getPassword());
        assertEquals(Rols.ADMIN, user.getRole());
    }

    @Test
    void shouldDefaultToBuyerWhenReconstitutedRoleIsNull() {
        User user = User.reconstitute(
                UUID.randomUUID(),
                new UserName("Jane", "Doe"),
                Email.of("jane@example.com"),
                HashedPassword.of("password123"),
                null
        );

        assertEquals(Rols.BUYER, user.getRole());
    }
}
