package com.sushi.registroventas;
import com.sushi.registroventas.model.Venta;
import com.sushi.registroventas.repository.VentaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(VentaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Venta(null, 1, 1, new BigDecimal("15500.00"), LocalDateTime.now().minusDays(1), "local"));
                repository.save(new Venta(null, 2, 1, new BigDecimal("22000.00"), LocalDateTime.now().minusHours(5), "delivery"));
                repository.save(new Venta(null, 3, 2, new BigDecimal("18900.00"), LocalDateTime.now(), "online"));
            }
        };
    }
}
