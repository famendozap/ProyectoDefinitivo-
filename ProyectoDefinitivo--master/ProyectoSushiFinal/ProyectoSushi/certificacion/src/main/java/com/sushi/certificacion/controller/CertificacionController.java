package com.sushi.certificacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.service.CertificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/certificaciones")
public class CertificacionController {
    @Autowired
    private CertificacionService service;

    @Operation(
        summary = "Listar certificaciones",
        description = "Obtiene una lista con todas las certificaciones registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Certificacion>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(
        summary = "Buscar certificación por ID",
        description = "Obtiene el registro de una certificación en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Certificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Certificacion> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Buscar certificaciones por estado",
        description = "Obtiene una lista de certificaciones filtradas por su estado (vigente, vencida, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Certificacion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(
        summary = "Buscar certificaciones por sucursal",
        description = "Obtiene una lista de certificaciones asociadas a una sucursal específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Certificacion>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(service.buscarPorSucursal(idSucursal));
    }

    @Operation(
        summary = "Buscar certificaciones por tipo",
        description = "Obtiene una lista de certificaciones filtradas por su tipo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Certificacion>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    @Operation(
        summary = "Agregar certificación",
        description = "Registra una nueva certificación en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Certificación agregada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Certificacion cert) {
        service.guardar(cert);
        return ResponseEntity.status(HttpStatus.CREATED).body("Certificacion agregada correctamente");
    }

    @Operation(
        summary = "Actualizar certificación",
        description = "Actualiza los datos de una certificación según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificación actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Certificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Certificacion cert) {
        return service.actualizar(id, cert)
                .map(c -> ResponseEntity.ok("Certificacion actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Certificacion no encontrada"));
    }

    @Operation(
        summary = "Eliminar certificación",
        description = "Elimina el registro de una certificación según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Certificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Certificacion eliminada correctamente");
        return ResponseEntity.status(404).body("Certificacion no encontrada");
    }
}


