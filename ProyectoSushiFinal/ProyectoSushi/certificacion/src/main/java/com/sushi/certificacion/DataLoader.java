package com.sushi.certificacion;
import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.repository.CertificacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(CertificacionRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Certificacion(null, "Certificado Sanitario SEREMI", "sanitario", 1, LocalDate.of(2024, 1, 15), LocalDate.of(2025, 1, 15), "renovacion"));
                repository.save(new Certificacion(null, "ISO 22000 Inocuidad Alimentaria", "calidad", 1, LocalDate.of(2023, 6, 1), LocalDate.of(2026, 6, 1), "vigente"));
                repository.save(new Certificacion(null, "Certificado Ambiental Municipal", "ambiental", 2, LocalDate.of(2024, 3, 10), LocalDate.of(2026, 3, 10), "vigente"));
            }
        };
    }
}
