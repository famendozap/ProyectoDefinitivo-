package com.sushi.pago.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.pago.model.Pago;
import com.sushi.pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService service;

    @Operation(
        summary = "Listar pagos",
        description = "Obtiene una lista con todos los pagos registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Pago>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(
        summary = "Buscar pago por ID",
        description = "Obtiene el registro de un pago en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Buscar pagos por estado",
        description = "Obtiene una lista de pagos filtrados por su estado (pendiente, completado, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(
        summary = "Buscar pagos por método de pago",
        description = "Obtiene una lista de pagos filtrados por el método utilizado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<Pago>> buscarPorMetodo(@PathVariable String metodoPago) {
        return ResponseEntity.ok(service.buscarPorMetodo(metodoPago));
    }

    @Operation(
        summary = "Buscar pago por pedido",
        description = "Obtiene el pago asociado a un pedido específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe pago para ese pedido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Pago> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Registrar pago",
        description = "Registra un nuevo pago en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Pago pago) {
        service.guardar(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pago registrado correctamente");
    }

    @Operation(
        summary = "Actualizar pago",
        description = "Actualiza los datos de un pago según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Pago pago) {
        return service.actualizar(id, pago)
                .map(p -> ResponseEntity.ok("Pago actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Pago no encontrado"));
    }

    @Operation(
        summary = "Eliminar pago",
        description = "Elimina un registro de pago según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Pago eliminado correctamente");
        return ResponseEntity.status(404).body("Pago no encontrado");
    }
}

