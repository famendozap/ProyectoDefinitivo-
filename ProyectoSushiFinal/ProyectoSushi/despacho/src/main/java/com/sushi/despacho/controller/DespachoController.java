package com.sushi.despacho.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.despacho.dto.PagoDTO;
import com.sushi.despacho.model.Despacho;
import com.sushi.despacho.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/despachos")
public class DespachoController {
    @Autowired
    private DespachoService service;

    @Operation(
        summary = "Listar despachos",
        description = "Obtiene una lista con todos los despachos registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Despacho>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(
        summary = "Buscar despacho por ID",
        description = "Obtiene el registro de un despacho en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despacho encontrado"),
        @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Despacho> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Buscar despachos por estado",
        description = "Obtiene una lista de despachos filtrados por su estado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Despacho>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(
        summary = "Buscar despachos por tipo",
        description = "Obtiene una lista de despachos filtrados por tipo de entrega"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Despacho>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    @Operation(
        summary = "Buscar despacho por pedido",
        description = "Obtiene el despacho asociado a un pedido específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despacho encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe despacho para ese pedido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Despacho> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Verificar pago de pedido",
        description = "Consulta al microservicio de pago si un pedido tiene pago completado antes de despacharlo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago verificado, el pedido puede ser despachado"),
        @ApiResponse(responseCode = "402", description = "El pedido no tiene pago completado"),
        @ApiResponse(responseCode = "404", description = "No se encontró pago para ese pedido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/verificar-pago/{idPedido}")
    public ResponseEntity<?> verificarPago(@PathVariable Integer idPedido) {
        return service.verificarPago(idPedido)
                .map(pago -> {
                    if ("completado".equalsIgnoreCase(pago.getEstado())) {
                        return ResponseEntity.ok("Pago verificado. El pedido #" + idPedido + " puede ser despachado.");
                    }
                    return ResponseEntity.status(402).body("El pedido #" + idPedido + " no tiene pago completado. Estado: " + pago.getEstado());
                })
                .orElse(ResponseEntity.status(404).body("No se encontro pago para el pedido #" + idPedido));
    }

    @Operation(
        summary = "Agregar despacho",
        description = "Registra un nuevo despacho en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Despacho agregado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Despacho despacho) {
        service.guardar(despacho);
        return ResponseEntity.status(HttpStatus.CREATED).body("Despacho agregado correctamente");
    }

    @Operation(
        summary = "Actualizar despacho",
        description = "Actualiza los datos de un despacho según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despacho actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Despacho despacho) {
        return service.actualizar(id, despacho)
                .map(d -> ResponseEntity.ok("Despacho actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Despacho no encontrado"));
    }

    @Operation(
        summary = "Eliminar despacho",
        description = "Elimina un registro de despacho según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despacho eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Despacho eliminado correctamente");
        return ResponseEntity.status(404).body("Despacho no encontrado");
    }
}

