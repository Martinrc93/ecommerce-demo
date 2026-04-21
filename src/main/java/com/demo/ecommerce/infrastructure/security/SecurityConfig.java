package com.demo.ecommerce.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivado para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/users/register", "/products/**", "/error", "/sales/**").permitAll() // TODO despues sacar error y poner manejo de excepciones
                        .anyRequest().authenticated()             // Todo lo demás requiere login
                )
                .httpBasic(Customizer.withDefaults()); // Autenticación básica para pruebas

        return http.build();
    }
}
