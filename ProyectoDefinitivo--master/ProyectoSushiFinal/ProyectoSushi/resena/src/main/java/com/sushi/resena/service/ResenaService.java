package com.sushi.resena.service;
import com.sushi.resena.model.Resena;
import com.sushi.resena.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ResenaService {
    @Autowired private ResenaRepository repository;
    public List<Resena> listar() { return repository.findAll(); }
    public Optional<Resena> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Resena> buscarPorCliente(Integer idCliente) { return repository.findByIdCliente(idCliente); }
    public List<Resena> buscarPorCalificacion(Integer calificacion) { return repository.findByCalificacion(calificacion); }
    public List<Resena> buscarPorCalificacionMinima(Integer calificacion) { return repository.findByCalificacionGreaterThanEqual(calificacion); }
    public Resena guardar(Resena resena) { return repository.save(resena); }
    public Optional<Resena> actualizar(Integer id, Resena datos) {
        return repository.findById(id).map(r -> {
            r.setCalificacion(datos.getCalificacion());
            r.setComentario(datos.getComentario());
            return repository.save(r);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
