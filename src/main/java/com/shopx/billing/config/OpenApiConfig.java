package com.shopx.billing.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Shopx - Payment Service", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Shopx - Payment MicroService")
                        .description(
                                        "This Document include payment endpoints."+
                                        "\n \t-Tech stack : Code Java (Streams, Exceptions, Builder, Generics), JWT Authentication, Spring Boot(MVC, Security, JPA/Hibernate), PostgreSQL, AWS EC2, Github Actions (CI/CD)"
                        )
                )
                .servers(List.of(
                        new io.swagger.v3.oas.models.servers.Server().url("/")
                ));
    }

}
