package com.sushi.soporte;
import com.sushi.soporte.model.Ticket;
import com.sushi.soporte.repository.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(TicketRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Ticket(null, 1, "Pedido llegó incompleto", "Me faltó una pieza del pedido #5.", "pedido", "abierto", "alta", LocalDateTime.now().minusHours(4), null));
                repository.save(new Ticket(null, 2, "Problema con el pago", "Se cobró dos veces mi tarjeta.", "pago", "en_proceso", "alta", LocalDateTime.now().minusHours(2), null));
                repository.save(new Ticket(null, 3, "Consulta sobre menu", "Quiero saber los ingredientes del Dragon Roll.", "producto", "cerrado", "baja", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(20)));
            }
        };
    }
}
