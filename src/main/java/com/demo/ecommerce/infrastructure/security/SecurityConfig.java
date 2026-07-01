package com.demo.ecommerce.infrastructure.security;

import com.demo.ecommerce.infrastructure.config.ApiPaths;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException(username);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                ApiPaths.AUTH + "/**",
                                ApiPaths.USERS + "/register",
                                "/error",
                                "/actuator/health",
                                "/actuator/health/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, ApiPaths.PRODUCTS + "/**", ApiPaths.BRANDS + "/**", ApiPaths.CATEGORIES + "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, ApiPaths.SALES + "/**").hasAnyRole("BUYER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, ApiPaths.PRODUCTS + "/**", ApiPaths.BRANDS + "/**", ApiPaths.CATEGORIES + "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, ApiPaths.PRODUCTS + "/**", ApiPaths.BRANDS + "/**", ApiPaths.CATEGORIES + "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, ApiPaths.PRODUCTS + "/**", ApiPaths.BRANDS + "/**", ApiPaths.CATEGORIES + "/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
