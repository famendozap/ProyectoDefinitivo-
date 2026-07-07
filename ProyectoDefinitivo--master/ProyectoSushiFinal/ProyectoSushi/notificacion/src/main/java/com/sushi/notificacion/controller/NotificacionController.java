package com.sushi.notificacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.notificacion.dto.UsuarioDTO;
import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService service;

    @Operation(
        summary = "Listar notificaciones",
        description = "Obtiene una lista con todas las notificaciones registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Notificacion>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(
        summary = "Buscar notificación por ID",
        description = "Obtiene el registro de una notificación en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Buscar notificaciones por usuario",
        description = "Obtiene una lista de notificaciones asociadas a un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.buscarPorUsuario(idUsuario));
    }

    @Operation(
        summary = "Buscar notificaciones por estado",
        description = "Obtiene una lista de notificaciones filtradas por su estado (leída, no leída, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(
        summary = "Buscar notificaciones por tipo",
        description = "Obtiene una lista de notificaciones filtradas por su tipo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    @Operation(
        summary = "Buscar notificaciones por canal",
        description = "Obtiene una lista de notificaciones filtradas por canal de envío (email, SMS, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/canal/{canal}")
    public ResponseEntity<List<Notificacion>> buscarPorCanal(@PathVariable String canal) {
        return ResponseEntity.ok(service.buscarPorCanal(canal));
    }

    @Operation(
        summary = "Consultar datos de usuario",
        description = "Consulta al microservicio de autenticación los datos de un usuario por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado en el sistema"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/datos-usuario/{idUsuario}")
public ResponseEntity<?> consultarUsuario(@PathVariable Integer idUsuario) {
    Optional<UsuarioDTO> usuario = service.consultarUsuario(idUsuario);
    if (usuario.isPresent()) {
        return ResponseEntity.ok(usuario.get());
    }
    return ResponseEntity.status(404).body("Usuario no encontrado en el sistema");
}

    @Operation(
        summary = "Enviar notificación",
        description = "Registra y envía una nueva notificación en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificación enviada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Notificacion notif) {
        service.guardar(notif);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notificacion enviada correctamente");
    }

    @Operation(
        summary = "Actualizar notificación",
        description = "Actualiza los datos de una notificación según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Notificacion notif) {
        return service.actualizar(id, notif)
                .map(n -> ResponseEntity.ok("Notificacion actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Notificacion no encontrada"));
    }

    @Operation(
        summary = "Eliminar notificación",
        description = "Elimina una notificación según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Notificacion eliminada correctamente");
        return ResponseEntity.status(404).body("Notificacion no encontrada");
    }
}

