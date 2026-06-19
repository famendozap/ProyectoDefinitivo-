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
    @GetMapping("/listar")
    public ResponseEntity<List<Resena>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Resena> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Resena>> buscarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(idCliente));
    }
    @GetMapping("/calificacion/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacion(@PathVariable Integer calificacion) {
        return ResponseEntity.ok(service.buscarPorCalificacion(calificacion));
    }
    @GetMapping("/calificacion-minima/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacionMinima(@PathVariable Integer calificacion) {
        return ResponseEntity.ok(service.buscarPorCalificacionMinima(calificacion));
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Resena resena) {
        service.guardar(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body("Resena agregada correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Resena resena) {
        return service.actualizar(id, resena)
                .map(r -> ResponseEntity.ok("Resena actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Resena no encontrada"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Resena eliminada correctamente");
        return ResponseEntity.status(404).body("Resena no encontrada");
    }
}

