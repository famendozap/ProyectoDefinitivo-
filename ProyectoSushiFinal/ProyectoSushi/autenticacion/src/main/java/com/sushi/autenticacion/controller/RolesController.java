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


    @Operation(
        summary = "Listar roles",
        description = "Obtiene una lista con todos los roles registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Roles>> listar() { return ResponseEntity.ok(service.listar()); }


    @Operation(
        summary = "Buscar rol por ID",
        description = "Obtiene el registro de un rol en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Roles> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @Operation(
        summary = "Buscar rol por tipo",
        description = "Obtiene el registro de un rol según su tipo (ADMIN, USER, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/tipo/{tipoDERol}")
    public ResponseEntity<Roles> buscarPorTipo(@PathVariable String tipoDERol) {
        return service.buscarPorTipo(tipoDERol).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @Operation(
        summary = "Agregar rol",
        description = "Registra un nuevo rol en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rol agregado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Roles roles) {
        service.guardar(roles);
        return ResponseEntity.status(HttpStatus.CREATED).body("Rol agregado correctamente");
    }


    @Operation(
        summary = "Actualizar rol",
        description = "Actualiza los datos de un rol según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Roles roles) {
        return service.actualizar(id, roles)
                .map(r -> ResponseEntity.ok("Rol actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Rol no encontrado"));
    }


    @Operation(
        summary = "Eliminar rol",
        description = "Elimina un rol según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Rol eliminado correctamente");
        return ResponseEntity.status(404).body("Rol no encontrado");
    }
}
