package com.sushi.registrosucursal.controller;
import com.sushi.registrosucursal.model.Sucursal;
import com.sushi.registrosucursal.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "*")
@Tag(name = "Sucursales", description = "Gestion de sucursales")
@RestController
@RequestMapping("/sucursales")
public class SucursalController {
    @Autowired
    private SucursalService service;
    @Operation(
        summary = "Listar sucursales",
        description = "Obtiene la lista completa de sucursales registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Sucursal>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar sucursal por ID",
        description = "Obtiene un(a) sucursal a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Sucursal> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar sucursales por estado",
        description = "Lista los/las sucursales que coinciden con el/la estado indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/estado/{estado}")
    public ResponseEntity<List<Sucursal>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @Operation(
        summary = "Buscar sucursales por ciudad",
        description = "Lista los/las sucursales que coinciden con el/la ciudad indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Sucursal>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(service.buscarPorCiudad(ciudad));
    }
    @Operation(
        summary = "Buscar sucursal por nombre",
        description = "Obtiene un(a) sucursal segun el/la nombre indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/nombre/{nombre}")
    public ResponseEntity<Sucursal> buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Registrar sucursal",
        description = "Crea un nuevo registro de sucursal en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Sucursal sucursal) {
        service.guardar(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucursal registrada correctamente");
    }
    @Operation(
        summary = "Actualizar sucursal",
        description = "Actualiza los datos de un(a) sucursal existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Sucursal sucursal) {
        return service.actualizar(id, sucursal)
                .map(s -> ResponseEntity.ok("Sucursal actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Sucursal no encontrada"));
    }
    @Operation(
        summary = "Eliminar sucursal",
        description = "Elimina un(a) sucursal del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Sucursal eliminada correctamente");
        return ResponseEntity.status(404).body("Sucursal no encontrada");
    }
}
