package com.sushi.registroventas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.sushi.registroventas.dto.InventarioDTO;
import com.sushi.registroventas.dto.PagoDTO;
import com.sushi.registroventas.model.Venta;
import com.sushi.registroventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ventas")
public class VentaController {
    @Autowired
    private VentaService service;

    @Operation(
        summary = "Listar ventas",
        description = "Obtiene una lista con todas las ventas registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(
        summary = "Buscar venta por ID",
        description = "Obtiene el registro de una venta en concreto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

@Operation(
        summary = "Buscar ventas por sucursal",
        description = "Obtiene una lista de ventas realizadas en una sucursal específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Venta>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(service.buscarPorSucursal(idSucursal));
    }

    @Operation(
        summary = "Buscar ventas por canal",
        description = "Obtiene una lista de ventas filtradas por canal de venta (presencial, online, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/canal/{canal}")
    public ResponseEntity<List<Venta>> buscarPorCanal(@PathVariable String canal) {
        return ResponseEntity.ok(service.buscarPorCanal(canal));
    }

    @Operation(
        summary = "Buscar venta por pedido",
        description = "Obtiene la venta asociada a un pedido específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "No existe venta para ese pedido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Venta> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Registrar venta",
        description = "Registra una nueva venta en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venta registrada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Venta venta) {
        service.guardar(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta registrada correctamente");
    }

    @Operation(
        summary = "Actualizar venta",
        description = "Actualiza los datos de una venta según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Venta venta) {
        return service.actualizar(id, venta)
                .map(v -> ResponseEntity.ok("Venta actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Venta no encontrada"));
    }

    @Operation(
        summary = "Eliminar venta",
        description = "Elimina un registro de venta según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Venta eliminada correctamente");
        return ResponseEntity.status(404).body("Venta no encontrada");
    }

    @Operation(
        summary = "Consultar inventario de producto",
        description = "Consulta al microservicio de inventario el stock de un producto específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado en inventario"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado en inventario"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/inventario/{idProducto}")
    public ResponseEntity<InventarioDTO> consultarInventario(@PathVariable Integer idProducto) {
        return service.consultarInventario(idProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Consultar pago de pedido",
        description = "Consulta al microservicio de pago el estado del pago de un pedido específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe pago para ese pedido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/pago/{idPedido}")
    public ResponseEntity<PagoDTO> consultarPago(@PathVariable Integer idPedido) {
        return service.consultarPago(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

