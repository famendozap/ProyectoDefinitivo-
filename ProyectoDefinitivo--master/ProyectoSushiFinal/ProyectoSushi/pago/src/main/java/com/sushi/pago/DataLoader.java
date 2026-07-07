package com.sushi.pago;
import com.sushi.pago.model.Pago;
import com.sushi.pago.repository.PagoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(PagoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Pago(null, 1, new BigDecimal("15500.00"), "tarjeta", "completado", LocalDateTime.now()));
                repository.save(new Pago(null, 2, new BigDecimal("22000.00"), "efectivo", "completado", LocalDateTime.now()));
                repository.save(new Pago(null, 3, new BigDecimal("18900.00"), "transferencia", "pendiente", null));
            }
        };
    }
}
