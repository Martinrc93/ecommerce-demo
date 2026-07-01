package com.demo.ecommerce.infrastructure.input.web.controller.docs;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.RefreshRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "Endpoints for login, logout, and token refresh operations")
public interface AuthApiDocs {

    @Operation(summary = "Log in", description = "Validates user credentials and returns session tokens in HttpOnly cookies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    ResponseEntity<Void> login(
            @Parameter(description = "Email and password used for authentication") @RequestBody LoginRequest request);

    @Operation(summary = "Refresh access token", description = "Generates new tokens from a valid refresh token and updates the cookies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens refreshed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid or expired", content = @Content)
    })
    ResponseEntity<AuthResponse> refresh(
            @Parameter(description = "Refresh token used to renew the session") @RequestBody RefreshRequest refreshToken);

    @Operation(summary = "Log out", description = "Invalidates the current refresh token to safely end the user session.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Logout completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid or expired", content = @Content)
    })
    ResponseEntity<Void> logout(
            @Parameter(description = "Refresh token used to close the session") @RequestBody RefreshRequest refreshToken);
}
