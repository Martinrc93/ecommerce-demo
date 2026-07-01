package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.command.RegisterResult;
import com.demo.ecommerce.application.port.in.user.usecase.GetUserUseCase;
import com.demo.ecommerce.application.port.in.user.usecase.UserRegisterUseCase;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.config.ApiPaths;
import com.demo.ecommerce.infrastructure.input.web.controller.docs.UserApiDocs;
import com.demo.ecommerce.infrastructure.input.web.dto.user.response.UserDtoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(ApiPaths.USERS)
@AllArgsConstructor
public class UserController implements UserApiDocs {

    private final UserRegisterUseCase userRegisterService;
    private final GetUserUseCase getUserService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<RegisterResult> register(@RequestBody RegisterCommand request) {
        RegisterResult registerResult = userRegisterService.register(request);

        return ResponseEntity.ok().body(registerResult);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserDtoResponse> userByToken(@AuthenticationPrincipal UserDetails userDetails){
        UUID userId = UUID.fromString(userDetails.getUsername());
        User user = getUserService.getById(userId);
        UserDtoResponse userResponse = new UserDtoResponse(user.getUserName().name(), user.getUserName().lastName(), user.getEmail().email());
        return ResponseEntity.ok(userResponse);
    }

}
