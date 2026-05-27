package com.sushi.autenticacion;
import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.model.Usuario;
import com.sushi.autenticacion.model.UsuarioRol;
import com.sushi.autenticacion.repository.RolesRepository;
import com.sushi.autenticacion.repository.UsuarioRepository;
import com.sushi.autenticacion.repository.UsuarioRolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(RolesRepository rolesRepo, UsuarioRepository usuarioRepo,
                           UsuarioRolRepository usuarioRolRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (rolesRepo.count() == 0) {
                Roles admin = rolesRepo.save(new Roles(null, "ADMIN"));
                Roles empleado = rolesRepo.save(new Roles(null, "EMPLEADO"));
                Roles cliente = rolesRepo.save(new Roles(null, "CLIENTE"));
                usuarioRepo.save(new Usuario(null, "Juan", "Perez", "juan.perez@sushi.com", passwordEncoder.encode("pass123"), admin));
                usuarioRepo.save(new Usuario(null, "Maria", "Gonzalez", "maria.gonzalez@sushi.com", passwordEncoder.encode("pass456"), empleado));
                usuarioRepo.save(new Usuario(null, "Carlos", "Ramirez", "carlos.ramirez@sushi.com", passwordEncoder.encode("pass789"), cliente));
                usuarioRolRepo.save(new UsuarioRol(null, admin));
                usuarioRolRepo.save(new UsuarioRol(null, empleado));
                usuarioRolRepo.save(new UsuarioRol(null, cliente));
            }
        };
    }
}
