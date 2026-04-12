package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userName.name", target = "name")
    @Mapping(source = "userName.lastName", target = "lastName")
    @Mapping(source = "password.password" , target ="password")
    @Mapping(source = "email.email" , target ="email")
    UserEntity toEntity (User user);

    @Mapping(source = "name", target = "userName.name")
    @Mapping(source = "lastName", target = "userName.lastName")
    @Mapping(source = "password", target = "password.password")
    @Mapping(source = "email", target = "email.email")
    //@Mapping(target = "rol",ignore = true)
    User  toDomain (UserEntity userEntity);


}
