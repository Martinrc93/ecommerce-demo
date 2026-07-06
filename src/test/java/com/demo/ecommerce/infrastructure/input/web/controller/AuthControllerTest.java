package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.user.command.AuthResponse;
import com.demo.ecommerce.application.port.in.user.command.LoginCommand;
import com.demo.ecommerce.application.port.in.user.command.RefreshCommand;
import com.demo.ecommerce.application.port.in.user.usecase.AuthUseCase;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.LoginRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.auth.request.RefreshRequest;
import com.demo.ecommerce.infrastructure.input.web.mapper.AuthDtoMapper;
import com.demo.ecommerce.infrastructure.security.CookieTokenService;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUseCase authService;

    @MockBean
    private AuthDtoMapper authMapper;

    @MockBean
    private CookieTokenService cookieTokenService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldLoginAndSetBothCookies() throws Exception {
        LoginCommand command = new LoginCommand("john@example.com", "Password1!");
        when(authMapper.toCommand(any(LoginRequest.class))).thenReturn(command);
        when(authService.login(command)).thenReturn(new AuthResponse("access-token", "refresh-token"));
        when(cookieTokenService.buildAccessTokenCookie("access-token"))
                .thenReturn(ResponseCookie.from("accessToken", "access-token").path("/").build());
        when(cookieTokenService.buildRefreshTokenCookie("refresh-token"))
                .thenReturn(ResponseCookie.from("refreshToken", "refresh-token").path("/api/v1/auth/refresh").build());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\":\"john@example.com\",\"password\":\"Password1!\"}"))
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Set-Cookie", "accessToken=access-token; Path=/", "refreshToken=refresh-token; Path=/api/v1/auth/refresh"));
    }

    @Test
    void shouldRefreshAndReturnBodyWithCookies() throws Exception {
        when(authService.refresh("refresh-token")).thenReturn(new AuthResponse("new-access", "new-refresh"));
        when(cookieTokenService.buildAccessTokenCookie("new-access"))
                .thenReturn(ResponseCookie.from("accessToken", "new-access").path("/").build());
        when(cookieTokenService.buildRefreshTokenCookie("new-refresh"))
                .thenReturn(ResponseCookie.from("refreshToken", "new-refresh").path("/api/v1/auth/refresh").build());

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(APPLICATION_JSON)
                        .content("{\"refreshToken\":\"refresh-token\"}"))
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Set-Cookie", "accessToken=new-access; Path=/", "refreshToken=new-refresh; Path=/api/v1/auth/refresh"))
                .andExpect(jsonPath("$.accessToken").value("new-access"))
                .andExpect(jsonPath("$.refreshToken").value("new-refresh"));
    }

    @Test
    void shouldLogoutAndReturnNoContent() throws Exception {
        RefreshCommand command = new RefreshCommand("refresh-token");
        when(authMapper.toCommand(any(RefreshRequest.class))).thenReturn(command);

        mockMvc.perform(post("/api/v1/auth/logout")
                        .contentType(APPLICATION_JSON)
                        .content("{\"refreshToken\":\"refresh-token\"}"))
                .andExpect(status().isNoContent());

        verify(authService).logout(command);
    }

    @Test
    void shouldRejectBlankRefreshTokenOnRefresh() throws Exception {
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(APPLICATION_JSON)
                        .content("{\"refreshToken\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(authService, never()).refresh(any());
    }

    @Test
    void shouldAcceptInvalidLoginPayloadBecauseValidationIsMissing() throws Exception {
        LoginCommand command = new LoginCommand("bad-email", "short");
        when(authMapper.toCommand(any(LoginRequest.class))).thenReturn(command);
        when(authService.login(command)).thenReturn(new AuthResponse("access-token", "refresh-token"));
        when(cookieTokenService.buildAccessTokenCookie("access-token"))
                .thenReturn(ResponseCookie.from("accessToken", "access-token").path("/").build());
        when(cookieTokenService.buildRefreshTokenCookie("refresh-token"))
                .thenReturn(ResponseCookie.from("refreshToken", "refresh-token").path("/api/v1/auth/refresh").build());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\":\"bad-email\",\"password\":\"short\"}"))
                .andExpect(status().isOk());
    }
}
