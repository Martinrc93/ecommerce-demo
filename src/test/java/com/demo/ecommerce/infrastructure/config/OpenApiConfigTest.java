package com.demo.ecommerce.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final OpenApiConfig config = new OpenApiConfig();

    @Test
    void shouldBuildExpectedOpenApiInfo() {
        OpenAPI openAPI = config.customOpenAPI();

        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("API E-commerce Demo");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("0.1");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("DEMO E-commerce API documentation");
    }
}
