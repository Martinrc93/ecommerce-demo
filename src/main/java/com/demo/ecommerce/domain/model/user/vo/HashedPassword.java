package com.demo.ecommerce.domain.model.user.vo;

import org.springframework.security.crypto.bcrypt.BCrypt;

public record HashedPassword (String password) {

    public static HashedPassword of (String pass){
        if (pass == null || pass.length() <8 ) throw new RuntimeException("error in validation pass"); //TODO crear bien la exepcion

        return new HashedPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));
    }

    public  static HashedPassword fromHashed (String hashed){
        return new HashedPassword(hashed);
    }

    public boolean matches (String pass){
        return BCrypt.checkpw(pass, password);
    }

}
