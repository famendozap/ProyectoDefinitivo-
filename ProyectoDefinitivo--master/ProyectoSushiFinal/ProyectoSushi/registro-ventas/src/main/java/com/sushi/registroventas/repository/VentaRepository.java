package com.sushi.registroventas.repository;
import com.sushi.registroventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByIdSucursal(Integer idSucursal);
    List<Venta> findByCanalVentaIgnoreCase(String canalVenta);
    Optional<Venta> findByIdPedido(Integer idPedido);
}
