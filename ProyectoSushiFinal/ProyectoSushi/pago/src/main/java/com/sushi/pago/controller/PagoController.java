package com.sushi.pago.controller;
import com.sushi.pago.model.Pago;
import com.sushi.pago.service.PagoService;
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
@Tag(name = "Pagos", description = "Registro y consulta de pagos")
@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService service;
    @Operation(
        summary = "Listar pagos",
        description = "Obtiene la lista completa de pagos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Pago>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar pago por ID",
        description = "Obtiene un(a) pago a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar pagos por estado",
        description = "Lista los/las pagos que coinciden con el/la estado indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @Operation(
        summary = "Buscar pagos por metodo de pago",
        description = "Lista los/las pagos que coinciden con el/la metodo de pago indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<Pago>> buscarPorMetodo(@PathVariable String metodoPago) {
        return ResponseEntity.ok(service.buscarPorMetodo(metodoPago));
    }
    @Operation(
        summary = "Buscar pago por numero de pedido",
        description = "Obtiene un(a) pago segun el/la numero de pedido indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Pago> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Registrar pago",
        description = "Crea un nuevo registro de pago en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Pago pago) {
        service.guardar(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pago registrado correctamente");
    }
    @Operation(
        summary = "Actualizar pago",
        description = "Actualiza los datos de un(a) pago existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Pago pago) {
        return service.actualizar(id, pago)
                .map(p -> ResponseEntity.ok("Pago actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Pago no encontrado"));
    }
    @Operation(
        summary = "Eliminar pago",
        description = "Elimina un(a) pago del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Pago eliminado correctamente");
        return ResponseEntity.status(404).body("Pago no encontrado");
    }
}
