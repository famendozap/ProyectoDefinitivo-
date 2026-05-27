package com.sushi.registrosucursal.repository;
import com.sushi.registrosucursal.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    List<Sucursal> findByEstadoIgnoreCase(String estado);
    List<Sucursal> findByCiudadIgnoreCase(String ciudad);
    Optional<Sucursal> findByNombreIgnoreCase(String nombre);
}
