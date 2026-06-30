package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {

    @Test
    void shouldTrimAndBuildFullNameForValidValues() {
        UserName userName = new UserName("John", "Doe");

        assertEquals("John", userName.name());
        assertEquals("Doe", userName.lastName());
        assertEquals("John Doe", userName.fullName());
    }

    @Test
    void shouldRejectBlankName() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> new UserName(" ", "Doe")
        );

        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectBlankLastName() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> new UserName("John", " ")
        );

        assertEquals("lastname cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectNamesWithNumbers() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> new UserName("John1", "Doe")
        );

        assertEquals("Name cannot contain numbers or special characters", exception.getMessage());
    }

    @Test
    void shouldRejectLastNamesWithSpecialCharacters() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> new UserName("John", "Doe-Smith")
        );

        assertEquals("Lastname cannot contain numbers or special characters", exception.getMessage());
    }

    @Test
    void shouldRejectValuesWithSurroundingSpacesBecauseValidationRunsBeforeTrim() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> new UserName(" John", "Doe")
        );

        assertEquals("Name cannot contain numbers or special characters", exception.getMessage());
    }
}
