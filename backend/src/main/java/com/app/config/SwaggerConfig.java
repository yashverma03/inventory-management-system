package com.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .openapi("3.1.0")
        .info(new Info()
            .title("Inventory Management System API")
            .version("1.0.0")
            .description("API documentation for the Inventory Management System"));
  }
}
