package com.sushi.inventario.service;
import com.sushi.inventario.model.Inventario;
import com.sushi.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class InventarioService {
    @Autowired
    private InventarioRepository repository;
    public List<Inventario> listar() { return repository.findAll(); }
    public Optional<Inventario> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Inventario> buscarPorCategoria(String categoria) { return repository.findByCategoriaIgnoreCase(categoria); }
    public Optional<Inventario> buscarPorNombre(String nombre) { return repository.findByNombreProductoIgnoreCase(nombre); }
    public List<Inventario> buscarBajoStock(Integer cantidad) { return repository.findByCantidadLessThan(cantidad); }
    public Inventario guardar(Inventario inventario) { return repository.save(inventario); }
    public Optional<Inventario> actualizar(Integer id, Inventario datos) {
        return repository.findById(id).map(inv -> {
            inv.setNombreProducto(datos.getNombreProducto());
            inv.setCantidad(datos.getCantidad());
            inv.setUnidadMedida(datos.getUnidadMedida());
            inv.setStockMinimo(datos.getStockMinimo());
            inv.setCategoria(datos.getCategoria());
            return repository.save(inv);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
