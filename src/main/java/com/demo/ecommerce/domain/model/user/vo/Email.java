package com.demo.ecommerce.domain.model.user.vo;

import java.util.regex.Pattern;

public record Email(String email) {

    private static final Pattern PATTERN = Pattern.compile("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");

    public static Email of(String email) {
        if (email == null || !PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Email inválido: " + email);
        return new Email(email.toLowerCase());
    }

}
