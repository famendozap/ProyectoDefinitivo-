package com.sushi.certificacion.controller;
import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.service.CertificacionService;
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
@Tag(name = "Certificaciones", description = "Gestion de certificaciones sanitarias y de calidad")
@RestController
@RequestMapping("/certificaciones")
public class CertificacionController {
    @Autowired
    private CertificacionService service;
    @Operation(
        summary = "Listar certificaciones",
        description = "Obtiene la lista completa de certificaciones registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de certificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/listar")
    public ResponseEntity<List<Certificacion>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(
        summary = "Buscar certificacion por ID",
        description = "Obtiene un(a) certificacion a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Certificacion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/id/{id}")
    public ResponseEntity<Certificacion> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Operation(
        summary = "Buscar certificaciones por estado",
        description = "Lista los/las certificaciones que coinciden con el/la estado indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de certificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/estado/{estado}")
    public ResponseEntity<List<Certificacion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @Operation(
        summary = "Buscar certificaciones por sucursal",
        description = "Lista los/las certificaciones que coinciden con el/la sucursal indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de certificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Certificacion>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(service.buscarPorSucursal(idSucursal));
    }
    @Operation(
        summary = "Buscar certificaciones por tipo",
        description = "Lista los/las certificaciones que coinciden con el/la tipo indicado(a)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de certificaciones obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Certificacion>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
    @Operation(
        summary = "Registrar certificacion",
        description = "Crea un nuevo registro de certificacion en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Certificacion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Certificacion cert) {
        service.guardar(cert);
        return ResponseEntity.status(HttpStatus.CREATED).body("Certificacion agregada correctamente");
    }
    @Operation(
        summary = "Actualizar certificacion",
        description = "Actualiza los datos de un(a) certificacion existente segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificacion actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Certificacion no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Certificacion cert) {
        return service.actualizar(id, cert)
                .map(c -> ResponseEntity.ok("Certificacion actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Certificacion no encontrada"));
    }
    @Operation(
        summary = "Eliminar certificacion",
        description = "Elimina un(a) certificacion del sistema segun su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificacion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Certificacion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Certificacion eliminada correctamente");
        return ResponseEntity.status(404).body("Certificacion no encontrada");
    }
}
