package com.sushi.inventario.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
            .info(new Info()
            .title("API de Inventario")
            .description("API REST para la gestion de Inventario")
            .version("1.0")
            .contact(new Contact()
                .name("SushiFriend's")
                .email("soporte@sushi.com")
            )
        );
    }
}
