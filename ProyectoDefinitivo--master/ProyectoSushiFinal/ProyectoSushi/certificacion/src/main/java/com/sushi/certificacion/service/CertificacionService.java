package com.sushi.certificacion.service;
import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.repository.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CertificacionService {
    @Autowired private CertificacionRepository repository;
    public List<Certificacion> listar() { return repository.findAll(); }
    public Optional<Certificacion> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Certificacion> buscarPorEstado(String estado) { return repository.findByEstadoIgnoreCase(estado); }
    public List<Certificacion> buscarPorSucursal(Integer idSucursal) { return repository.findByIdSucursal(idSucursal); }
    public List<Certificacion> buscarPorTipo(String tipo) { return repository.findByTipoIgnoreCase(tipo); }
    public Certificacion guardar(Certificacion cert) { return repository.save(cert); }
    public Optional<Certificacion> actualizar(Integer id, Certificacion datos) {
        return repository.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setTipo(datos.getTipo());
            c.setIdSucursal(datos.getIdSucursal());
            c.setFechaEmision(datos.getFechaEmision());
            c.setFechaVencimiento(datos.getFechaVencimiento());
            c.setEstado(datos.getEstado());
            return repository.save(c);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
