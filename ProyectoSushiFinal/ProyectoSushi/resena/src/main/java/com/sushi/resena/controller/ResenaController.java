package com.sushi.resena.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.resena.model.Resena;
import com.sushi.resena.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/resenas")
public class ResenaController {
    @Autowired
    private ResenaService service;

    @Operation(
        summary = "Listar reseñas",
        description = "Obtiene una lista con todas las reseñas registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Resena>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(
        summary = "Buscar reseña por ID",
        description = "Obtiene el registro de una reseña en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Resena> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Buscar reseñas por cliente",
        description = "Obtiene una lista de reseñas realizadas por un cliente específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Resena>> buscarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(idCliente));
    }

    @Operation(
        summary = "Buscar reseñas por calificación exacta",
        description = "Obtiene una lista de reseñas que tienen exactamente la calificación indicada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/calificacion/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacion(@PathVariable Integer calificacion) {
        return ResponseEntity.ok(service.buscarPorCalificacion(calificacion));
    }

    @Operation(
        summary = "Buscar reseñas por calificación mínima",
        description = "Obtiene una lista de reseñas con calificación igual o mayor a la indicada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/calificacion-minima/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacionMinima(@PathVariable Integer calificacion) {
        return ResponseEntity.ok(service.buscarPorCalificacionMinima(calificacion));
    }

    @Operation(
        summary = "Agregar reseña",
        description = "Registra una nueva reseña en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña agregada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Resena resena) {
        service.guardar(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body("Resena agregada correctamente");
    }

    @Operation(
        summary = "Actualizar reseña",
        description = "Actualiza los datos de una reseña según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Resena resena) {
        return service.actualizar(id, resena)
                .map(r -> ResponseEntity.ok("Resena actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Resena no encontrada"));
    }

    @Operation(
        summary = "Eliminar reseña",
        description = "Elimina una reseña según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Resena eliminada correctamente");
        return ResponseEntity.status(404).body("Resena no encontrada");
    }
}

