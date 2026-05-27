package com.sushi.soporte.service;
import com.sushi.soporte.model.Ticket;
import com.sushi.soporte.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class TicketService {
    @Autowired private TicketRepository repository;
    public List<Ticket> listar() { return repository.findAll(); }
    public Optional<Ticket> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Ticket> buscarPorUsuario(Integer idUsuario) { return repository.findByIdUsuario(idUsuario); }
    public List<Ticket> buscarPorEstado(String estado) { return repository.findByEstadoIgnoreCase(estado); }
    public List<Ticket> buscarPorPrioridad(String prioridad) { return repository.findByPrioridadIgnoreCase(prioridad); }
    public List<Ticket> buscarPorCategoria(String categoria) { return repository.findByCategoriaIgnoreCase(categoria); }
    public Ticket guardar(Ticket ticket) { return repository.save(ticket); }
    public Optional<Ticket> actualizar(Integer id, Ticket datos) {
        return repository.findById(id).map(t -> {
            t.setAsunto(datos.getAsunto());
            t.setDescripcion(datos.getDescripcion());
            t.setCategoria(datos.getCategoria());
            t.setEstado(datos.getEstado());
            t.setPrioridad(datos.getPrioridad());
            t.setFechaCierre(datos.getFechaCierre());
            return repository.save(t);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
