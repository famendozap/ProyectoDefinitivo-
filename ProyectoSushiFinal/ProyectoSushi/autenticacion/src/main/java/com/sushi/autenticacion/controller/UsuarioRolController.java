package com.sushi.autenticacion.controller;
import com.sushi.autenticacion.model.UsuarioRol;
import com.sushi.autenticacion.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario-rol")
public class UsuarioRolController {
    @Autowired
    private UsuarioRolService service;
    @GetMapping("/listar")
    public List<UsuarioRol> listar() {
        return service.listar();
    }
    @GetMapping("/id/{id}")
    public Optional<UsuarioRol> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
    @GetMapping("/rol/{idRol}")
    public List<UsuarioRol> buscarPorRol(@PathVariable Integer idRol) {
        return service.buscarPorRol(idRol);
    }
    @PostMapping("/agregar")
    public UsuarioRol agregar(@RequestBody UsuarioRol usuarioRol) {
        return service.guardar(usuarioRol);
    }
}
