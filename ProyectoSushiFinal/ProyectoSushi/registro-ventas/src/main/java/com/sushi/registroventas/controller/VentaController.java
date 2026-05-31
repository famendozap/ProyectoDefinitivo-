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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ventas")
public class VentaController {
    @Autowired
    private VentaService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Venta>> buscarPorSucursal(@PathVariable Integer idSucursal) {
        return ResponseEntity.ok(service.buscarPorSucursal(idSucursal));
    }
    @GetMapping("/canal/{canal}")
    public ResponseEntity<List<Venta>> buscarPorCanal(@PathVariable String canal) {
        return ResponseEntity.ok(service.buscarPorCanal(canal));
    }
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Venta> buscarPorPedido(@PathVariable Integer idPedido) {
        return service.buscarPorPedido(idPedido).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Venta venta) {
        service.guardar(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta registrada correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Venta venta) {
        return service.actualizar(id, venta)
                .map(v -> ResponseEntity.ok("Venta actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Venta no encontrada"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Venta eliminada correctamente");
        return ResponseEntity.status(404).body("Venta no encontrada");
    }
    @GetMapping("/inventario/{idProducto}")
    public ResponseEntity<InventarioDTO> consultarInventario(@PathVariable Integer idProducto) {
        return service.consultarInventario(idProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/pago/{idPedido}")
    public ResponseEntity<PagoDTO> consultarPago(@PathVariable Integer idPedido) {
        return service.consultarPago(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

