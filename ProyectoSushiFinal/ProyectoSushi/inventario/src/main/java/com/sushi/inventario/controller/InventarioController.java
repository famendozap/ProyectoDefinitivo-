package com.sushi.inventario.controller;
import com.sushi.inventario.model.Inventario;
import com.sushi.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "*")
@Tag(name = "Inventario", description = "Gestion del stock de productos")
@RestController
@RequestMapping("/inventario")
public class InventarioController {
    @Autowired
    private InventarioService service;
    @Operation(
        summary = "Listar productos",
        description = "Obtiene la lista completa de productos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Inventario>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar producto por ID",
        description = "Obtiene un(a) producto a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Inventario> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar productos por categoria",
        description = "Lista los/las productos que coinciden con el/la categoria indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Inventario>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }
    @Operation(
        summary = "Buscar producto por nombre",
        description = "Obtiene un(a) producto segun el/la nombre indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/nombre/{nombre}")
    public ResponseEntity<Inventario> buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar productos bajo stock minimo",
        description = "Lista los productos de inventario cuya cantidad es menor a la cantidad indicada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/bajo-stock/{cantidad}")
    public ResponseEntity<List<Inventario>> buscarBajoStock(@PathVariable Integer cantidad) {
        return ResponseEntity.ok(service.buscarBajoStock(cantidad));
    }
    @Operation(
        summary = "Registrar producto",
        description = "Crea un nuevo registro de producto en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Inventario inventario) {
        service.guardar(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado al inventario correctamente");
    }
    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza los datos de un(a) producto existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Inventario inventario) {
        return service.actualizar(id, inventario)
                .map(i -> ResponseEntity.ok("Producto actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Producto no encontrado"));
    }
    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un(a) producto del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Producto eliminado del inventario correctamente");
        return ResponseEntity.status(404).body("Producto no encontrado");
    }
}
