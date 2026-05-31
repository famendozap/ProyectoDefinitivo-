package com.sushi.certificacion.controller;
import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.service.CertificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/certificaciones")
public class CertificacionController {
    @Autowired
    private CertificacionService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Certificacion>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Certificacion> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Certificacion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Certificacion>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(service.buscarPorSucursal(idSucursal));
    }
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Certificacion>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Certificacion cert) {
        service.guardar(cert);
        return ResponseEntity.status(HttpStatus.CREATED).body("Certificacion agregada correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Certificacion cert) {
        return service.actualizar(id, cert)
                .map(c -> ResponseEntity.ok("Certificacion actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Certificacion no encontrada"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Certificacion eliminada correctamente");
        return ResponseEntity.status(404).body("Certificacion no encontrada");
    }
}


