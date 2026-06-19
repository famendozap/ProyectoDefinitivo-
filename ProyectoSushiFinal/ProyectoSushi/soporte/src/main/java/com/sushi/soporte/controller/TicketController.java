package com.sushi.soporte.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.soporte.model.Ticket;
import com.sushi.soporte.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Ticket>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Ticket> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Ticket>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.buscarPorUsuario(idUsuario));
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ticket>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<Ticket>> buscarPorPrioridad(@PathVariable String prioridad) {
        return ResponseEntity.ok(service.buscarPorPrioridad(prioridad));
    }
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Ticket>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Ticket ticket) {
        service.guardar(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ticket de soporte creado correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Ticket ticket) {
        return service.actualizar(id, ticket)
                .map(t -> ResponseEntity.ok("Ticket de soporte actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Ticket no encontrado"));
    }

    @Operation(
        summary = "Eliminar ticket de soporte",
        description = "Elimina un ticket de soporte según su ID"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Ticket de soporte eliminado correctamente");
        return ResponseEntity.status(404).body("Ticket no encontrado");
    }
}

