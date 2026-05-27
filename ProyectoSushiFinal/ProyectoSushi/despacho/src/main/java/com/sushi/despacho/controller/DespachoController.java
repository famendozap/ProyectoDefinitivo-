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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/despachos")
public class DespachoController {
    @Autowired
    private DespachoService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Despacho>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Despacho> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Despacho>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Despacho>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Despacho> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
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
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Despacho despacho) {
        service.guardar(despacho);
        return ResponseEntity.status(HttpStatus.CREATED).body("Despacho agregado correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Despacho despacho) {
        return service.actualizar(id, despacho)
                .map(d -> ResponseEntity.ok("Despacho actualizado correctamente"))
                .orElse(ResponseEntity.status(404).body("Despacho no encontrado"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Despacho eliminado correctamente");
        return ResponseEntity.status(404).body("Despacho no encontrado");
    }
}
