package com.sushi.autenticacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.autenticacion.model.UsuarioRol;
import com.sushi.autenticacion.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario-rol")
public class UsuarioRolController {
    @Autowired
    private UsuarioRolService service;


    @Operation(
        summary = "Listar roles de usuarios",
        description = "Obtiene una lista con todos los roles asignados a usuarios en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<UsuarioRol> listar() {
        return service.listar();
    }


    @Operation(
        summary = "Buscar rol de usuario por ID",
        description = "Obtiene el registro de un rol de usuario en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/id/{id}")
    public Optional<UsuarioRol> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }


    @Operation(
        summary = "Buscar usuarios por rol",
        description = "Obtiene una lista de usuarios que tienen asignado un rol específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/rol/{idRol}")
    public List<UsuarioRol> buscarPorRol(@PathVariable Integer idRol) {
        return service.buscarPorRol(idRol);
    }


    @Operation(
        summary = "Asignar rol a usuario",
        description = "Registra la asignación de un rol a un usuario en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public UsuarioRol agregar(@RequestBody UsuarioRol usuarioRol) {
        return service.guardar(usuarioRol);
    }
}
