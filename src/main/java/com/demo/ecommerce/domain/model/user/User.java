package com.demo.ecommerce.domain.model.user;

import com.demo.ecommerce.domain.model.user.vo.Email;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import com.demo.ecommerce.domain.model.user.vo.Rol;
import com.demo.ecommerce.domain.model.user.vo.UserName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
public class User {

    private UUID id;
    private UserName userName;
    private Email email;
    private HashedPassword password;

    private Rol rol;

    public User create (String name,String lastName,String email,String password){
        User user = new User();
        this.userName = new UserName(name,lastName);
        this.email = Email.of(email);
        this.password = HashedPassword.of(password);
        return user;
    }




}
