package com.sushi.despacho;
import com.sushi.despacho.model.Despacho;
import com.sushi.despacho.repository.DespachoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(DespachoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Despacho(null, 1, "Av. Las Condes 1234, Santiago", "entregado", "delivery", LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1)));
                repository.save(new Despacho(null, 2, "Calle Nunoa 567, Santiago", "en_camino", "delivery", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45)));
                repository.save(new Despacho(null, 3, "Sucursal Central", "preparando", "retiro_en_tienda", LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
            }
        };
    }
}
