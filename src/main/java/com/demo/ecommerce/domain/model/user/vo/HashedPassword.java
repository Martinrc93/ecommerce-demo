package com.demo.ecommerce.domain.model.user.vo;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import org.springframework.security.crypto.bcrypt.BCrypt;

public record HashedPassword (String password) {

    public static HashedPassword of (String pass){
        if (pass == null || pass.length() <8 ) throw new InvalidValueObjectException("Password must contain a minimum of 8 characters");

        return new HashedPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));
    }

    public  static HashedPassword fromHashed (String hashed){
        return new HashedPassword(hashed);
    }

    public boolean matches (String pass){
        return BCrypt.checkpw(pass, password);
    }

}
