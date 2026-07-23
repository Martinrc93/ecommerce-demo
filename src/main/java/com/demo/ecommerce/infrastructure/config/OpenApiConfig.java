package com.demo.ecommerce.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API E-commerce Demo")
                        .version("0.1")
                        .description("DEMO E-commerce API documentation"));
    }

    @Bean
    public OpenApiCustomizer openApiTagOrderCustomizer() {
        return openApi -> openApi.setTags(List.of(
                new Tag().name("00 Seed").description("Quick actions to populate demo data"),
                new Tag().name("Authentication").description("Endpoints for login, logout, and token refresh operations"),
                new Tag().name("Users").description("Operations related to user management"),
                new Tag().name("Products").description("Operations related to product management"),
                new Tag().name("Categories").description("Operations related to category management"),
                new Tag().name("Brands").description("Operations related to brand management"),
                new Tag().name("Sales").description("Operations related to creating and querying sales")
        ));
    }

    @Bean
    public OpenApiCustomizer salesDateExamplesCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null || openApi.getPaths().get("/api/v1/sales") == null || openApi.getPaths().get("/api/v1/sales").getGet() == null) {
                return;
            }

            LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);
            LocalDate startDate = currentDate.minusDays(7);

            List<Parameter> parameters = openApi.getPaths().get("/api/v1/sales").getGet().getParameters();
            if (parameters == null) {
                return;
            }

            for (Parameter parameter : parameters) {
                if ("startDate".equals(parameter.getName())) {
                    parameter.setExample(startDate.toString());
                    parameter.setDescription("Start date for the search (format: YYYY-MM-DD)");
                    parameter.setSchema(new DateSchema().example(startDate.toString()));
                }
                if ("endDate".equals(parameter.getName())) {
                    parameter.setExample(currentDate.toString());
                    parameter.setDescription("End date for the search (format: YYYY-MM-DD)");
                    parameter.setSchema(new DateSchema().example(currentDate.toString()));
                }
            }
        };
    }
}
