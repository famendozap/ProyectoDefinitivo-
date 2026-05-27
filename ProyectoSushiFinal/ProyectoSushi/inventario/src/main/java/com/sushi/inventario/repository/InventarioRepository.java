package com.sushi.inventario.repository;
import com.sushi.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findByCategoriaIgnoreCase(String categoria);
    Optional<Inventario> findByNombreProductoIgnoreCase(String nombreProducto);
    List<Inventario> findByCantidadLessThan(Integer cantidad);
}
