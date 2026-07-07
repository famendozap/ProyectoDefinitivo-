package com.sushi.soporte.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
        name = "bearerAuth",
        type = io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI soporteOpenAPI() {
        return new OpenAPI()
                .info(new Info()

                        .title("API de Soporte")

                        .description("Gestión de tickets de soporte al cliente")

                        .version("1.0")

                        .contact(new Contact()
                                .name("Sushi Corp")
                                .email("soporte@sushi.com")
                        )
                );
    }
}
