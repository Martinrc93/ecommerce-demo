package com.demo.ecommerce.infrastructure.security;

import com.demo.ecommerce.infrastructure.config.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SecurityTestController {

    @GetMapping(ApiPaths.PRODUCTS)
    public ResponseEntity<Void> productsGet() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiPaths.PRODUCTS)
    public ResponseEntity<Void> productsPost() {
        return ResponseEntity.ok().build();
    }

    @PutMapping(ApiPaths.PRODUCTS)
    public ResponseEntity<Void> productsPut() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(ApiPaths.PRODUCTS)
    public ResponseEntity<Void> productsDelete() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiPaths.SALES)
    public ResponseEntity<Void> salesPost() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiPaths.USERS + "/register")
    public ResponseEntity<Void> registerPost() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/secure/ping")
    public ResponseEntity<Void> securePing() {
        return ResponseEntity.ok().build();
    }
}
