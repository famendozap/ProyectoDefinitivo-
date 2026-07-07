package com.sushi.autenticacion.controller;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Gestion de usuarios del sistema")

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private JwtUtil jwtUtil;
    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene la lista completa de usuarios. Requiere header Authorization con token JWT valido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido o invalido"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (!tokenValido(authHeader)) return ResponseEntity.status(401).body("Token requerido. Inicia sesion primero.");
        return ResponseEntity.ok(service.listar());
    }
    @Operation(
        summary = "Buscar usuario por ID",
        description = "Obtiene un usuario a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
    return service.buscarPorId(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));

    }
    @Operation(
        summary = "Buscar usuario por correo",
        description = "Busca un usuario por su correo electronico. Requiere header Authorization."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido o invalido"),
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
        description = "Busca usuarios cuyo nombre coincide (parcial) con el indicado. Requiere header Authorization."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido o invalido"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
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
        description = "Lista los usuarios que tienen asignado un rol especifico. Requiere header Authorization."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido o invalido"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
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
        summary = "Registrar usuario",
        description = "Crea un nuevo usuario. Requiere header Authorization con rol ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido, invalido o sin permisos de ADMIN"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
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
        description = "Actualiza los datos de un usuario existente. Requiere header Authorization con rol ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido, invalido o sin permisos de ADMIN"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
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
        description = "Elimina un usuario del sistema. Requiere header Authorization con rol ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "401", description = "Token de autorizacion requerido, invalido o sin permisos de ADMIN"),
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
