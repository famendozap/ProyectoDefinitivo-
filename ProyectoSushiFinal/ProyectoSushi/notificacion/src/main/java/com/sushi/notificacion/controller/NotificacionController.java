package com.sushi.notificacion.controller;
import com.sushi.notificacion.dto.UsuarioDTO;
import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "*")
@Tag(name = "Notificaciones", description = "Gestion de notificaciones a usuarios")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService service;
    @Operation(
        summary = "Listar notificaciones",
        description = "Obtiene la lista completa de notificaciones registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Notificacion>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar notificacion por ID",
        description = "Obtiene un(a) notificacion a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar notificaciones por usuario",
        description = "Lista los/las notificaciones que coinciden con el/la usuario indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.buscarPorUsuario(idUsuario));
    }
    @Operation(
        summary = "Buscar notificaciones por estado",
        description = "Lista los/las notificaciones que coinciden con el/la estado indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @Operation(
        summary = "Buscar notificaciones por tipo",
        description = "Lista los/las notificaciones que coinciden con el/la tipo indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
    @Operation(
        summary = "Buscar notificaciones por canal",
        description = "Lista los/las notificaciones que coinciden con el/la canal indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/canal/{canal}")
    public ResponseEntity<List<Notificacion>> buscarPorCanal(@PathVariable String canal) {
        return ResponseEntity.ok(service.buscarPorCanal(canal));
    }
    @Operation(
        summary = "Consultar datos del usuario",
        description = "Consulta al microservicio de Autenticacion (a traves de Eureka) los datos del usuario asociado a la notificacion."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
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
        summary = "Registrar notificacion",
        description = "Crea un nuevo registro de notificacion en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Notificacion notif) {
        service.guardar(notif);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notificacion enviada correctamente");
    }
    @Operation(
        summary = "Actualizar notificacion",
        description = "Actualiza los datos de un(a) notificacion existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Notificacion notif) {
        return service.actualizar(id, notif)
                .map(n -> ResponseEntity.ok("Notificacion actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Notificacion no encontrada"));
    }
    @Operation(
        summary = "Eliminar notificacion",
        description = "Elimina un(a) notificacion del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Notificacion eliminada correctamente");
        return ResponseEntity.status(404).body("Notificacion no encontrada");
    }
}
