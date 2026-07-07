package com.sushi.registroventas.controller;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Registro de ventas")
@RestController
@RequestMapping("/ventas")
public class VentaController {
    @Autowired
    private VentaService service;
    @Operation(
        summary = "Listar ventas",
        description = "Obtiene la lista completa de ventas registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Venta>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar venta por ID",
        description = "Obtiene un(a) venta a partir de su ID."
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
        description = "Lista los/las ventas que coinciden con el/la sucursal indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Venta>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(service.buscarPorSucursal(idSucursal));
    }
    @Operation(
        summary = "Buscar ventas por canal",
        description = "Lista los/las ventas que coinciden con el/la canal indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/canal/{canal}")
    public ResponseEntity<List<Venta>> buscarPorCanal(@PathVariable String canal) {
        return ResponseEntity.ok(service.buscarPorCanal(canal));
    }
    @Operation(
        summary = "Buscar venta por numero de pedido",
        description = "Obtiene un(a) venta segun el/la numero de pedido indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Venta> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Registrar venta",
        description = "Crea un nuevo registro de venta en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada correctamente"),
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
        description = "Actualiza los datos de un(a) venta existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
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
        description = "Elimina un(a) venta del sistema segun su ID."
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
        summary = "Consultar inventario de un producto",
        description = "Consulta al microservicio de Inventario (a traves de Eureka) el stock disponible de un producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado en el inventario"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado en el inventario"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/inventario/{idProducto}")
    public ResponseEntity<InventarioDTO> consultarInventario(@PathVariable Integer idProducto) {
        return service.consultarInventario(idProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Consultar pago de un pedido",
        description = "Consulta al microservicio de Pago (a traves de Eureka) el estado del pago de un pedido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado para el pedido"),
            @ApiResponse(responseCode = "404", description = "No se encontro pago para el pedido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/pago/{idPedido}")
    public ResponseEntity<PagoDTO> consultarPago(@PathVariable Integer idPedido) {
        return service.consultarPago(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
