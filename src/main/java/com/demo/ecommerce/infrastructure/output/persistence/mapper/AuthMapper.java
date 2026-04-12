package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.auth.RefreshToken;
import com.demo.ecommerce.infrastructure.output.persistence.entity.AuthEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "revoked", ignore = true)
    @Mapping(target = "refreshToken", source = "token")
    @Mapping(target = "user.id", source = "userId")
    AuthEntity toEntity(RefreshToken token);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "refreshToken", source = "token")
    @Mapping(target = "user.id", source = "userId")
    void updateEntityFromDomain(RefreshToken token, @MappingTarget AuthEntity entity);

    default RefreshToken toDomain(AuthEntity entity){
        if (entity != null){
            return RefreshToken.reconstistute(entity.getId(),entity.getRefreshToken(), entity.getUser().getId(), entity.getExpiresAt());
        }
        return null;
    }
}
