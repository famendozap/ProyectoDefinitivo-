package com.sushi.autenticacion.service;
import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class RolesService {
    @Autowired
    private RolesRepository repository;
    public List<Roles> listar() { return repository.findAll(); }
    public Optional<Roles> buscarPorId(Integer id) { return repository.findById(id); }
    public Optional<Roles> buscarPorTipo(String tipoDERol) { return repository.findByTipoDERolIgnoreCase(tipoDERol); }
    public Roles guardar(Roles roles) { return repository.save(roles); }
    public Optional<Roles> actualizar(Integer id, Roles datos) {
        return repository.findById(id).map(r -> {
            r.setTipoDERol(datos.getTipoDERol());
            return repository.save(r);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
