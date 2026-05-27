package com.sushi.notificacion;
import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.repository.NotificacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(NotificacionRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Notificacion(null, 1, "pedido", "Tu pedido #1 ha sido confirmado.", "email", "enviada", LocalDateTime.now().minusHours(3)));
                repository.save(new Notificacion(null, 1, "despacho", "Tu pedido #1 esta en camino.", "push", "enviada", LocalDateTime.now().minusHours(2)));
                repository.save(new Notificacion(null, 2, "pago", "Pago de $22000 recibido correctamente.", "sms", "enviada", LocalDateTime.now().minusHours(1)));
                repository.save(new Notificacion(null, 3, "promocion", "10% de descuento en tu proximo pedido!", "email", "pendiente", LocalDateTime.now()));
            }
        };
    }
}
