package com.sushi.pago.service;
import com.sushi.pago.model.Pago;
import com.sushi.pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class PagoService {
    @Autowired private PagoRepository repository;
    public List<Pago> listar() { return repository.findAll(); }
    public Optional<Pago> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Pago> buscarPorEstado(String estado) { return repository.findByEstadoIgnoreCase(estado); }
    public List<Pago> buscarPorMetodo(String metodoPago) { return repository.findByMetodoPagoIgnoreCase(metodoPago); }
    public Optional<Pago> buscarPorPedido(Integer idPedido) { return repository.findByIdPedido(idPedido); }
    public Pago guardar(Pago pago) { return repository.save(pago); }
    public Optional<Pago> actualizar(Integer id, Pago datos) {
        return repository.findById(id).map(p -> {
            p.setMonto(datos.getMonto());
            p.setMetodoPago(datos.getMetodoPago());
            p.setEstado(datos.getEstado());
            p.setFechaPago(datos.getFechaPago());
            return repository.save(p);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
