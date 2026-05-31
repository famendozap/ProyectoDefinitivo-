package com.sushi.notificacion.controller;
import com.sushi.notificacion.dto.UsuarioDTO;
import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService service;
    @GetMapping("/listar")
    public ResponseEntity<List<Notificacion>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/id/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.buscarPorUsuario(idUsuario));
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
    @GetMapping("/canal/{canal}")
    public ResponseEntity<List<Notificacion>> buscarPorCanal(@PathVariable String canal) {
        return ResponseEntity.ok(service.buscarPorCanal(canal));
    }
    @GetMapping("/datos-usuario/{idUsuario}")
public ResponseEntity<?> consultarUsuario(@PathVariable Integer idUsuario) {
    Optional<UsuarioDTO> usuario = service.consultarUsuario(idUsuario);
    if (usuario.isPresent()) {
        return ResponseEntity.ok(usuario.get());
    }
    return ResponseEntity.status(404).body("Usuario no encontrado en el sistema");
}
    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@Valid @RequestBody Notificacion notif) {
        service.guardar(notif);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notificacion enviada correctamente");
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody Notificacion notif) {
        return service.actualizar(id, notif)
                .map(n -> ResponseEntity.ok("Notificacion actualizada correctamente"))
                .orElse(ResponseEntity.status(404).body("Notificacion no encontrada"));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) return ResponseEntity.ok("Notificacion eliminada correctamente");
        return ResponseEntity.status(404).body("Notificacion no encontrada");
    }
}

