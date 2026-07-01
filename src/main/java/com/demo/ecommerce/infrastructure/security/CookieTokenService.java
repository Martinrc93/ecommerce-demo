package com.demo.ecommerce.infrastructure.security;

import com.demo.ecommerce.infrastructure.config.ApiPaths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieTokenService {

    @Value("${app.security.cookie.domain:}")
    private String cookieDomain;

    @Value("${app.security.cookie.secure:true}")
    private boolean secureCookie;

    private static final int REFRESH_TOKEN_EXPIRY = 30 * 60;
    private static final int ACCESS_TOKEN_EXPIRY  = 15 * 60;

    public ResponseCookie buildAccessTokenCookie(String token) {
        return ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(secureCookie)
                .path("/")
                .maxAge(ACCESS_TOKEN_EXPIRY)
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

    public ResponseCookie buildRefreshTokenCookie(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(secureCookie)
                .path(ApiPaths.AUTH_REFRESH)
                .maxAge(REFRESH_TOKEN_EXPIRY)
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

    public ResponseCookie clearAccessTokenCookie() {
        return ResponseCookie.from("accessToken", "")
                .httpOnly(true).secure(secureCookie)
                .path("/").maxAge(0).build();
    }

    public ResponseCookie clearRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true).secure(secureCookie)
                .path(ApiPaths.AUTH_REFRESH).maxAge(0).build();
    }

}
