package com.sushi.inventario.controller;
import com.sushi.inventario.model.Inventario;
import com.sushi.inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/inventario")
public class InventarioController {
    @Autowired
    private InventarioService service;

    @Operation(
        summary = "Listar inventario",
        description = "Obtiene una lista con todos los productos registrados en el sistema"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay nada que listar"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Inventario>> listar() { return ResponseEntity.ok(service.listar()); }


    @Operation(
        summary = "Buscar por ID",
        description = "Obtiene el registro de un producto en concreto registrado en el sistema"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Inventario> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Buscar por Categoría",
        description = "Obtiene el registros de una categoría concreta registrada en el sistema"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro obtenido correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Inventario>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

    @Operation(
        summary = "Buscar por nombre",
        description = "Obtiene el registro por un nombre en concreto registrado en el sistema"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Inventario> buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Busca productos con bajo stock",
        description = "Obtiene una lista con los productos que tienen un stock bajo según cantidad indicada"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de bajo stock obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/bajo-stock/{cantidad}")
    public ResponseEntity<List<Inventario>> buscarBajoStock(@PathVariable Integer cantidad) {
        return ResponseEntity.ok(service.buscarBajoStock(cantidad));
    }

    @Operation(
        summary = "Agregar producto",
        description = "Agrega un producto al inventario"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto agregado al inventario correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "El producto ya existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Inventario inventario) {
        service.guardar(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado al inventario correctamente");
    }

    @Operation(
        summary = "Actualizar Producto",
        description = "Actualiza un producto según su ID"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Inventario inventario) {
        return service.actualizar(id, inventario)
                .map(i -> ResponseEntity.ok("Producto actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Producto no encontrado"));
    }

    @Operation(
        summary = "Eliminar Producto",
        description = "Elimina un producto según su ID"
    )

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Producto eliminado del inventario correctamente");
        return ResponseEntity.status(404).body("Producto no encontrado");
    }
}


