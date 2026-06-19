package com.sushi.autenticacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Roles>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Roles> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/tipo/{tipoDERol}")
    public ResponseEntity<Roles> buscarPorTipo(@PathVariable String tipoDERol) {
        return service.buscarPorTipo(tipoDERol).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Roles roles) {
        service.guardar(roles);
        return ResponseEntity.status(HttpStatus.CREATED).body("Rol agregado correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Roles roles) {
        return service.actualizar(id, roles)
                .map(r -> ResponseEntity.ok("Rol actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Rol no encontrado"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Rol eliminado correctamente");
        return ResponseEntity.status(404).body("Rol no encontrado");
    }
}
