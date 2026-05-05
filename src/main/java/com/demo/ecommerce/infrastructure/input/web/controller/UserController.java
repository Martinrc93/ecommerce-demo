package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.command.RegisterResult;
import com.demo.ecommerce.application.port.in.user.usecase.GetUserUseCase;
import com.demo.ecommerce.application.port.in.user.usecase.UserRegisterUseCase;
import com.demo.ecommerce.domain.model.user.User;
import com.demo.ecommerce.infrastructure.input.web.dto.user.response.UserDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "Usuarios (Users)", description = "Endpoints para la gestión de cuentas de usuario y perfiles")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRegisterUseCase userRegisterService;
    private final GetUserUseCase getUserService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una nueva cuenta en el sistema y devuelve los tokens de sesión iniciales.")
    @PostMapping("/register")
    public ResponseEntity<RegisterResult> register(@RequestBody RegisterCommand request) {
        RegisterResult registerResult = userRegisterService.register(request);

        return ResponseEntity.ok().body(registerResult);
    }

    @Operation(summary = "Obtener perfil del usuario", description = "Devuelve la información del usuario actualmente autenticado (requiere token Bearer).")
    @GetMapping("/me")
    public ResponseEntity<UserDtoResponse> userByToken(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails){
        UUID userId = UUID.fromString(userDetails.getUsername());
        User user = getUserService.getById(userId);
        UserDtoResponse userResponse = new UserDtoResponse(user.getUserName().name(), user.getUserName().lastName(), user.getEmail().email());
        return ResponseEntity.ok(userResponse);
    }

}