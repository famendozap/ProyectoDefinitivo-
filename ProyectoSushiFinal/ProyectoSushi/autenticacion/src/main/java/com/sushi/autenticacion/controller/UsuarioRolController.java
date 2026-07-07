package com.sushi.autenticacion.controller;
import com.sushi.autenticacion.model.UsuarioRol;
import com.sushi.autenticacion.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "*")
@Tag(name = "Usuario-Rol", description = "Relacion entre usuarios y roles")
@RestController
@RequestMapping("/usuario-rol")
public class UsuarioRolController {
    @Autowired
    private UsuarioRolService service;
    @Operation(
        summary = "Listar relaciones usuario-rol",
        description = "Obtiene la lista completa de relaciones usuario-rol registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones usuario-rol obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public List<UsuarioRol> listar() {
        return service.listar();
    }
    @Operation(
        summary = "Buscar relacion usuario-rol por ID",
        description = "Obtiene un(a) relacion usuario-rol a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relacion usuario-rol encontrada"),
            @ApiResponse(responseCode = "404", description = "Relacion usuario-rol no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public Optional<UsuarioRol> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
    @Operation(
        summary = "Buscar relaciones usuario-rol por rol",
        description = "Lista los/las relaciones usuario-rol que coinciden con el/la rol indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones usuario-rol obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/rol/{idRol}")
    public List<UsuarioRol> buscarPorRol(@PathVariable Integer idRol) {
        return service.buscarPorRol(idRol);
    }
    @Operation(
        summary = "Buscar relaciones usuario-rol por usuario",
        description = "Lista los roles asociados a un usuario especifico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones usuario-rol obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/usuario/{idUsuario}")
    public List<UsuarioRol> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return service.buscarPorUsuario(idUsuario);
    }
    @Operation(
        summary = "Registrar relacion usuario-rol",
        description = "Crea un nuevo registro de relacion usuario-rol en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relacion usuario-rol creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public UsuarioRol agregar(@RequestBody UsuarioRol usuarioRol) {
        return service.guardar(usuarioRol);
    }
}
