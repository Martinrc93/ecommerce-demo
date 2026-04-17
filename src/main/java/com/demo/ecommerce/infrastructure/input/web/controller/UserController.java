package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.command.RegisterResult;
import com.demo.ecommerce.application.service.user.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResult> register(@RequestBody RegisterCommand request) {
        RegisterResult registerResult = userRegisterService.register(request);

        return ResponseEntity.ok().body(registerResult);
    }

}
