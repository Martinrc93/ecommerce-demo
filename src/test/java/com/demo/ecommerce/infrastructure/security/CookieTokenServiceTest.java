package com.demo.ecommerce.infrastructure.security;

import com.demo.ecommerce.infrastructure.config.ApiPaths;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CookieTokenServiceTest {

    @Test
    void shouldBuildAccessTokenCookieWithExpectedAttributes() {
        CookieTokenService service = configuredService();

        ResponseCookie cookie = service.buildAccessTokenCookie("access-token");

        assertEquals("accessToken", cookie.getName());
        assertEquals("access-token", cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertEquals("example.com", cookie.getDomain());
        assertEquals("Strict", cookie.getSameSite());
        assertEquals(900, cookie.getMaxAge().toSeconds());
        assertEquals(true, cookie.isHttpOnly());
        assertEquals(false, cookie.isSecure());
    }

    @Test
    void shouldBuildRefreshTokenCookieWithExpectedAttributes() {
        CookieTokenService service = configuredService();

        ResponseCookie cookie = service.buildRefreshTokenCookie("refresh-token");

        assertEquals("refreshToken", cookie.getName());
        assertEquals("refresh-token", cookie.getValue());
        assertEquals(ApiPaths.AUTH_REFRESH, cookie.getPath());
        assertEquals("example.com", cookie.getDomain());
        assertEquals("Strict", cookie.getSameSite());
        assertEquals(1800, cookie.getMaxAge().toSeconds());
        assertEquals(true, cookie.isHttpOnly());
        assertEquals(false, cookie.isSecure());
    }

    @Test
    void shouldClearAccessTokenCookieUsingCurrentAttributes() {
        CookieTokenService service = configuredService();

        ResponseCookie cookie = service.clearAccessTokenCookie();

        assertEquals("accessToken", cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertEquals(0, cookie.getMaxAge().toSeconds());
        assertEquals(true, cookie.isHttpOnly());
        assertEquals(false, cookie.isSecure());
    }

    @Test
    void shouldClearRefreshTokenCookieUsingCurrentAttributes() {
        CookieTokenService service = configuredService();

        ResponseCookie cookie = service.clearRefreshTokenCookie();

        assertEquals("refreshToken", cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals(ApiPaths.AUTH_REFRESH, cookie.getPath());
        assertEquals(0, cookie.getMaxAge().toSeconds());
        assertEquals(true, cookie.isHttpOnly());
        assertEquals(false, cookie.isSecure());
    }

    private static CookieTokenService configuredService() {
        CookieTokenService service = new CookieTokenService();
        ReflectionTestUtils.setField(service, "cookieDomain", "example.com");
        ReflectionTestUtils.setField(service, "secureCookie", false);
        return service;
    }
}
