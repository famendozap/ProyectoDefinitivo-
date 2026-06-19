package com.sushi.autenticacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.autenticacion.dto.UsuarioDTO;
import com.sushi.autenticacion.model.Usuario;
import com.sushi.autenticacion.service.UsuarioService;
import com.sushi.autenticacion.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido. Inicia sesion primero.");
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
    @GetMapping("/mail/{mail}")
    public ResponseEntity<?> buscarPorMail(
            @PathVariable String mail,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return service.buscarPorMail(mail)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(
            @PathVariable String nombre,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }
    @GetMapping("/rol/{idRoles}")
    public ResponseEntity<?> buscarPorRol(
            @PathVariable Integer idRoles,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return ResponseEntity.ok(service.buscarPorRol(idRoles));
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(
            @Valid @RequestBody Usuario usuario,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido. Inicia sesion primero.");
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado. Solo ADMIN puede agregar usuarios.");
        service.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario agregado correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Usuario usuario,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado. Solo ADMIN puede actualizar usuarios.");
        return service.actualizar(id, usuario)
                .map(u -> ResponseEntity.ok("Usuario actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado. Solo ADMIN puede eliminar usuarios.");
        if (service.eliminar(id)) return ResponseEntity.ok("Usuario eliminado correctamente");
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
    private boolean tokenValido(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
        String token = authHeader.substring(7);
        return jwtUtil.validarToken(token);
    }
    private boolean esAdmin(String authHeader) {
        String token = authHeader.substring(7);
        return "ADMIN".equalsIgnoreCase(jwtUtil.extraerRol(token));
    }
}


