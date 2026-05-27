package com.sushi.resena;
import com.sushi.resena.model.Resena;
import com.sushi.resena.repository.ResenaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(ResenaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Resena(null, 1, 1, 5, "Excelente sushi, muy fresco y bien presentado!", LocalDateTime.now().minusDays(2)));
                repository.save(new Resena(null, 2, 2, 4, "Muy buena experiencia, el delivery fue rapido.", LocalDateTime.now().minusDays(1)));
                repository.save(new Resena(null, 3, 3, 3, "Bueno pero el tiempo de espera fue largo.", LocalDateTime.now()));
            }
        };
    }
}
