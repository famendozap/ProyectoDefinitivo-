package com.sushi.registrosucursal;
import com.sushi.registrosucursal.model.Sucursal;
import com.sushi.registrosucursal.repository.SucursalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(SucursalRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Sucursal(null, "Sushi Central", "Av. Providencia 1234", "Santiago", "+56222345678", "activa"));
                repository.save(new Sucursal(null, "Sushi Norte", "Calle Recoleta 456", "Santiago", "+56222678901", "activa"));
                repository.save(new Sucursal(null, "Sushi Sur", "Av. Vicuna Mackenna 789", "Santiago", "+56222112233", "inactiva"));
            }
        };
    }
}
