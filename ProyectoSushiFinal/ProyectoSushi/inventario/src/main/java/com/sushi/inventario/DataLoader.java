package com.sushi.inventario;
import com.sushi.inventario.model.Inventario;
import com.sushi.inventario.repository.InventarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(InventarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Inventario(null, "Arroz para sushi", 50, "kg", 10, "Granos"));
                repository.save(new Inventario(null, "Salmon", 20, "kg", 5, "Pescados"));
                repository.save(new Inventario(null, "Alga nori", 100, "unidad", 20, "Algas"));
                repository.save(new Inventario(null, "Aguacate", 30, "unidad", 10, "Verduras"));
                repository.save(new Inventario(null, "Salsa de soya", 15, "litro", 3, "Salsas"));
            }
        };
    }
}
