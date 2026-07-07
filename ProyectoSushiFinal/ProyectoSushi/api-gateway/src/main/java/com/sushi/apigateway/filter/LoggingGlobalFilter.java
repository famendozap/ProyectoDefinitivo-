package com.sushi.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Filtro global: se aplica a TODAS las rutas del Gateway, sin necesidad de
 * declararlo en cada una (a diferencia de los filtros de application.yml,
 * que son por ruta o por defecto, este es un GlobalFilter en codigo Java).
 *
 * Registra en el log cada peticion que entra al Gateway y la respuesta que
 * sale, junto con cuanto demoro el microservicio destino en responder.
 * Esto es util para depurar problemas de interoperabilidad entre servicios:
 * permite ver en un solo lugar (el log del Gateway) que ruta se uso, a que
 * microservicio se redirigio, y si la respuesta fue lenta o con error.
 */
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        long inicio = System.currentTimeMillis();

        log.info(">>> Entrando al Gateway: {} {}", request.getMethod(), request.getURI());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duracionMs = System.currentTimeMillis() - inicio;
            log.info("<<< Respuesta del Gateway: {} {} -> {} ({} ms)",
                    request.getMethod(),
                    request.getURI(),
                    exchange.getResponse().getStatusCode(),
                    duracionMs);
        }));
    }

    @Override
    public int getOrder() {
        // Numero bajo = se ejecuta primero entre los filtros globales
        return -1;
    }
}
