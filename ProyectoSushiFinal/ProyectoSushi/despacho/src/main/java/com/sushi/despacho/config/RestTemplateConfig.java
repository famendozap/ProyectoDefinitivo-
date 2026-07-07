package com.sushi.despacho.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
@Configuration
public class RestTemplateConfig {

    // Timeouts explicitos: si el microservicio remoto no responde, la llamada
    // falla rapido (con ResourceAccessException, manejado en ManejadorErrores)
    // en vez de dejar la peticion colgada indefinidamente.
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }
}
