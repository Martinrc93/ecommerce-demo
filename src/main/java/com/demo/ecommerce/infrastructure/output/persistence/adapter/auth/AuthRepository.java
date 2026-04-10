package com.demo.ecommerce.infrastructure.output.persistence.adapter.auth;

import com.demo.ecommerce.infrastructure.output.persistence.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<AuthEntity, UUID> {
}
