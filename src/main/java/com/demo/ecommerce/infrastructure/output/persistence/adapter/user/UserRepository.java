package com.demo.ecommerce.infrastructure.output.persistence.adapter.user;

import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

}
