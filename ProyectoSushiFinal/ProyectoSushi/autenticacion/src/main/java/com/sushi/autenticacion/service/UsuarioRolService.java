package com.sushi.autenticacion.service;
import com.sushi.autenticacion.model.UsuarioRol;
import com.sushi.autenticacion.repository.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class UsuarioRolService {
    @Autowired
    private UsuarioRolRepository repository;
    public List<UsuarioRol> listar() {
        return repository.findAll();
    }
    public Optional<UsuarioRol> buscarPorId(Integer id) {
        return repository.findById(id);
    }
    public List<UsuarioRol> buscarPorRol(Integer idRol) {
        return repository.findByRolId(idRol);
    }
    public List<UsuarioRol> buscarPorUsuario(Integer idUsuario) {
        return repository.findByUsuarioId(idUsuario);
    }
    public UsuarioRol guardar(UsuarioRol usuarioRol) {
        return repository.save(usuarioRol);
    }
}
