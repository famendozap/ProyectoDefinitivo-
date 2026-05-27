package com.sushi.autenticacion.controller;
import com.sushi.autenticacion.dto.LoginRequestDTO;
import com.sushi.autenticacion.dto.LoginResponseDTO;
import com.sushi.autenticacion.model.Usuario;
import com.sushi.autenticacion.repository.UsuarioRepository;
import com.sushi.autenticacion.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByMail(request.getMail());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(request.getPass(), usuario.getPass())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }
        String token = jwtUtil.generarToken(usuario.getMail(), usuario.getRoles().getTipoDERol());
        return ResponseEntity.ok(new LoginResponseDTO(
                "Login exitoso",
                usuario.getMail(),
                usuario.getRoles().getTipoDERol(),
                token
        ));
    }
    @PostMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestParam String token) {
        if (jwtUtil.validarToken(token)) {
            return ResponseEntity.ok("Token válido para: " + jwtUtil.extraerMail(token) + " | Rol: " + jwtUtil.extraerRol(token));
        }
        return ResponseEntity.status(401).body("Token inválido o expirado");
    }
}
