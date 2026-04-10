package com.demo.ecommerce.domain.model.user;

import com.demo.ecommerce.domain.model.user.vo.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {

    private UUID id;
    private UserName userName;
    private Email email;

    @Getter(value = AccessLevel.NONE)
    private HashedPassword password;

    private Rols rol;

    public static User create (String name,String lastName,String email,String password){
        User user = new User();
        user.userName = new UserName(name,lastName);
        user.email = Email.of(email);
        user.password = HashedPassword.of(password);
        return user;
    }

    public static User reconstitute (UUID id,UserName userName,Email email,HashedPassword password){
        User user = new User();
        user.id = id;
        user.userName = userName;
        user.email = email;
        user.password = password;
        return user;
    }

}
