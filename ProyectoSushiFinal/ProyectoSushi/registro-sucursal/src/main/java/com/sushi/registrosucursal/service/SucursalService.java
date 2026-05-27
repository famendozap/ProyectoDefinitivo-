package com.sushi.registrosucursal.service;
import com.sushi.registrosucursal.model.Sucursal;
import com.sushi.registrosucursal.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class SucursalService {
    @Autowired private SucursalRepository repository;
    public List<Sucursal> listar() { return repository.findAll(); }
    public Optional<Sucursal> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Sucursal> buscarPorEstado(String estado) { return repository.findByEstadoIgnoreCase(estado); }
    public List<Sucursal> buscarPorCiudad(String ciudad) { return repository.findByCiudadIgnoreCase(ciudad); }
    public Optional<Sucursal> buscarPorNombre(String nombre) { return repository.findByNombreIgnoreCase(nombre); }
    public Sucursal guardar(Sucursal sucursal) { return repository.save(sucursal); }
    public Optional<Sucursal> actualizar(Integer id, Sucursal datos) {
        return repository.findById(id).map(s -> {
            s.setNombre(datos.getNombre());
            s.setDireccion(datos.getDireccion());
            s.setCiudad(datos.getCiudad());
            s.setTelefono(datos.getTelefono());
            s.setEstado(datos.getEstado());
            return repository.save(s);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
