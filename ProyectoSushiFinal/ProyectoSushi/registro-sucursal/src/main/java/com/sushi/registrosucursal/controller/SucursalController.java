package com.sushi.registrosucursal.controller;
import com.sushi.registrosucursal.model.Sucursal;
import com.sushi.registrosucursal.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sucursales")
public class SucursalController {
    @Autowired
    private SucursalService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Sucursal>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Sucursal> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Sucursal>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Sucursal>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(service.buscarPorCiudad(ciudad));
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Sucursal> buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Sucursal sucursal) {
        service.guardar(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucursal registrada correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Sucursal sucursal) {
        return service.actualizar(id, sucursal)
                .map(s -> ResponseEntity.ok("Sucursal actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Sucursal no encontrada"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Sucursal eliminada correctamente");
        return ResponseEntity.status(404).body("Sucursal no encontrada");
    }
}
