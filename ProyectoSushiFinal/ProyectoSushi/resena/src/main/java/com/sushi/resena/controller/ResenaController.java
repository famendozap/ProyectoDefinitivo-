package com.sushi.resena.controller;
import com.sushi.resena.model.Resena;
import com.sushi.resena.service.ResenaService;
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
@Tag(name = "Resenas", description = "Gestion de resenas y calificaciones")
@RestController
@RequestMapping("/resenas")
public class ResenaController {
    @Autowired
    private ResenaService service;
    @Operation(
        summary = "Listar resenas",
        description = "Obtiene la lista completa de resenas registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resenas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Resena>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar resena por ID",
        description = "Obtiene un(a) resena a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resena encontrada"),
            @ApiResponse(responseCode = "404", description = "Resena no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Resena> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar resenas por cliente",
        description = "Lista los/las resenas que coinciden con el/la cliente indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resenas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Resena>> buscarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(idCliente));
    }
    @Operation(
        summary = "Buscar resenas por calificacion",
        description = "Lista los/las resenas que coinciden con el/la calificacion indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resenas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/calificacion/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacion(@PathVariable Integer calificacion) {
        return ResponseEntity.ok(service.buscarPorCalificacion(calificacion));
    }
    @Operation(
        summary = "Buscar resenas por calificacion minima",
        description = "Lista las resenas cuya calificacion es mayor o igual a la indicada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resenas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/calificacion-minima/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacionMinima(@PathVariable Integer calificacion) {
        return ResponseEntity.ok(service.buscarPorCalificacionMinima(calificacion));
    }
    @Operation(
        summary = "Registrar resena",
        description = "Crea un nuevo registro de resena en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resena creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Resena resena) {
        service.guardar(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body("Resena agregada correctamente");
    }
    @Operation(
        summary = "Actualizar resena",
        description = "Actualiza los datos de un(a) resena existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resena actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Resena no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Resena resena) {
        return service.actualizar(id, resena)
                .map(r -> ResponseEntity.ok("Resena actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Resena no encontrada"));
    }
    @Operation(
        summary = "Eliminar resena",
        description = "Elimina un(a) resena del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resena eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Resena no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Resena eliminada correctamente");
        return ResponseEntity.status(404).body("Resena no encontrada");
    }
}
