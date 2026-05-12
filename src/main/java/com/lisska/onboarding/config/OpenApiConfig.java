package com.lisska.onboarding.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Сообщаем Сваггеру про авторизацию по токену
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
    @Bean
    public OpenAPI onboardingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Onboarding API").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }
}