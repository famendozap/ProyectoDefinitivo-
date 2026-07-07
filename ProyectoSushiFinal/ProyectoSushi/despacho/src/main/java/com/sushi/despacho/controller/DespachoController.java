package com.sushi.despacho.controller;
import com.sushi.despacho.dto.PagoDTO;
import com.sushi.despacho.model.Despacho;
import com.sushi.despacho.service.DespachoService;
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
@Tag(name = "Despachos", description = "Gestion de despachos y entregas")
@RestController
@RequestMapping("/despachos")
public class DespachoController {
    @Autowired
    private DespachoService service;
    @Operation(
        summary = "Listar despachos",
        description = "Obtiene la lista completa de despachos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despachos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Despacho>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar despacho por ID",
        description = "Obtiene un(a) despacho a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despacho encontrado"),
            @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Despacho> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar despachos por estado",
        description = "Lista los/las despachos que coinciden con el/la estado indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despachos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/estado/{estado}")
    public ResponseEntity<List<Despacho>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @Operation(
        summary = "Buscar despachos por tipo",
        description = "Lista los/las despachos que coinciden con el/la tipo indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despachos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Despacho>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
    @Operation(
        summary = "Buscar despacho por numero de pedido",
        description = "Obtiene un(a) despacho segun el/la numero de pedido indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despacho encontrado"),
            @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Despacho> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Verificar pago de un pedido",
        description = "Consulta al microservicio de Pago (a traves de Eureka) si el pedido tiene el pago completado antes de autorizar el despacho."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago verificado, el pedido puede despacharse"),
            @ApiResponse(responseCode = "402", description = "El pago no esta completado"),
            @ApiResponse(responseCode = "404", description = "No se encontro pago para el pedido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/verificar-pago/{idPedido}")
    public ResponseEntity<?> verificarPago(@PathVariable Integer idPedido) {
        return service.verificarPago(idPedido)
                .map(pago -> {
                    if ("completado".equalsIgnoreCase(pago.getEstado())) {
                        return ResponseEntity.ok("Pago verificado. El pedido #" + idPedido + " puede ser despachado.");
                    }
                    return ResponseEntity.status(402).body("El pedido #" + idPedido + " no tiene pago completado. Estado: " + pago.getEstado());
                })
                .orElse(ResponseEntity.status(404).body("No se encontro pago para el pedido #" + idPedido));
    }
    @Operation(
        summary = "Registrar despacho",
        description = "Crea un nuevo registro de despacho en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Despacho creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Despacho despacho) {
        service.guardar(despacho);
        return ResponseEntity.status(HttpStatus.CREATED).body("Despacho agregado correctamente");
    }
    @Operation(
        summary = "Actualizar despacho",
        description = "Actualiza los datos de un(a) despacho existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despacho actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Despacho despacho) {
        return service.actualizar(id, despacho)
                .map(d -> ResponseEntity.ok("Despacho actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Despacho no encontrado"));
    }
    @Operation(
        summary = "Eliminar despacho",
        description = "Elimina un(a) despacho del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despacho eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Despacho no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Despacho eliminado correctamente");
        return ResponseEntity.status(404).body("Despacho no encontrado");
    }
}
