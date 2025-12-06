package com.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("Enter JWT token");

    Components components = new Components()
        .addSecuritySchemes("Bearer Authentication", securityScheme);

    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("Bearer Authentication");

    return new OpenAPI()
        .openapi("3.1.0")
        .info(new Info()
            .title("Inventory Management System API")
            .version("1.0.0")
            .description("API documentation for the Inventory Management System"))
        .components(components)
        .addSecurityItem(securityRequirement);
  }
}
