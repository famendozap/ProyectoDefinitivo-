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


    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene una lista con todos los usuarios registrados. Requiere token válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido. Inicia sesion primero.");
        return ResponseEntity.ok(service.listar());
    }


    @Operation(
        summary = "Buscar usuario por ID",
        description = "Obtiene el registro de un usuario en concreto según su ID. Requiere token válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }


    @Operation(
        summary = "Buscar usuario por mail",
        description = "Obtiene el registro de un usuario según su correo electrónico. Requiere token válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/mail/{mail}")
    public ResponseEntity<?> buscarPorMail(
            @PathVariable String mail,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return service.buscarPorMail(mail)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }


    @Operation(
        summary = "Buscar usuarios por nombre",
        description = "Obtiene una lista de usuarios que coincidan con el nombre indicado. Requiere token válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(
            @PathVariable String nombre,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }


    @Operation(
        summary = "Buscar usuarios por rol",
        description = "Obtiene una lista de usuarios que tengan el rol indicado. Requiere token válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/rol/{idRoles}")
    public ResponseEntity<?> buscarPorRol(
            @PathVariable Integer idRoles,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido.");
        return ResponseEntity.ok(service.buscarPorRol(idRoles));
    }


    @Operation(
        summary = "Agregar usuario",
        description = "Registra un nuevo usuario en el sistema. Requiere token válido y rol ADMIN."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario agregado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(
            @Valid @RequestBody Usuario usuario,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido. Inicia sesion primero.");
        if (!esAdmin(authHeader)) return ResponseEntity.status(403).body("Acceso denegado. Solo ADMIN puede agregar usuarios.");
        service.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario agregado correctamente");
    }


    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario según su ID. Requiere token válido y rol ADMIN."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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


    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario según su ID. Requiere token válido y rol ADMIN."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "401", description = "Token requerido o inválido"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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


