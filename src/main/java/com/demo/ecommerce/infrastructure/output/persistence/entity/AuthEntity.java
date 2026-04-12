package com.demo.ecommerce.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth")
@Getter
@Setter
public class AuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 512)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean revoked = false;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    public void revoke() {
        this.revoked = true;
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

}
