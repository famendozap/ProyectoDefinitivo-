package com.sushi.pago.controller;

import com.sushi.pago.model.Pago;
import com.sushi.pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Pago>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<Pago>> buscarPorMetodo(@PathVariable String metodoPago) {
        return ResponseEntity.ok(service.buscarPorMetodo(metodoPago));
    }
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Pago> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Pago pago) {
        service.guardar(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pago registrado correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Pago pago) {
        return service.actualizar(id, pago)
                .map(p -> ResponseEntity.ok("Pago actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Pago no encontrado"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Pago eliminado correctamente");
        return ResponseEntity.status(404).body("Pago no encontrado");
    }
}

