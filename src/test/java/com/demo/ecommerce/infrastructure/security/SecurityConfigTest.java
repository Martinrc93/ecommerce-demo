package com.demo.ecommerce.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SecurityTestController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityConfig securityConfig;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldAllowAnonymousAccessToPublicEndpoints() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/users/register"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRequireAuthenticationForProtectedEndpoints() throws Exception {
        mockMvc.perform(get("/api/v1/secure/ping"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/v1/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowBuyerToPostSalesButNotProducts() throws Exception {
        mockAuthenticatedToken("buyer-token", "buyer-id", List.of("ROLE_BUYER"));

        mockMvc.perform(post("/api/v1/sales").header("Authorization", "Bearer buyer-token"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/products").header("Authorization", "Bearer buyer-token"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowAdminToManageProducts() throws Exception {
        mockAuthenticatedToken("admin-token", "admin-id", List.of("ROLE_ADMIN"));

        mockMvc.perform(post("/api/v1/products").header("Authorization", "Bearer admin-token"))
                .andExpect(status().isOk());
    }

    @Test
    void userDetailsServiceShouldAlwaysThrowUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> securityConfig.userDetailsService().loadUserByUsername("missing-user"));
    }

    private void mockAuthenticatedToken(String token, String userId, List<String> roles) {
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.extractUserId(token)).thenReturn(userId);
        when(jwtTokenProvider.extractRoles(token)).thenReturn(roles);
    }
}
