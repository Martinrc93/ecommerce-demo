package com.demo.ecommerce.infrastructure.input.web.controller.docs;

import com.demo.ecommerce.application.port.in.user.command.RegisterCommand;
import com.demo.ecommerce.application.port.in.user.command.RegisterResult;
import com.demo.ecommerce.infrastructure.input.web.dto.user.response.UserDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "Endpoints for user account and profile management")
public interface UserApiDocs {

    @Operation(summary = "Register a new user", description = "Creates a new account and returns the initial session tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResult.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration data", content = @Content)
    })
    ResponseEntity<RegisterResult> register(
            @Parameter(description = "User data required to create an account") @RequestBody RegisterCommand request);

    @Operation(summary = "Get user profile", description = "Returns the currently authenticated user information (requires a Bearer token).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated user returned successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDtoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<UserDtoResponse> userByToken(@Parameter(hidden = true) UserDetails userDetails);
}
