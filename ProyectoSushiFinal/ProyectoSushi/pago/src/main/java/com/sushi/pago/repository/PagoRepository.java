package com.sushi.pago.repository;
import com.sushi.pago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByEstadoIgnoreCase(String estado);
    List<Pago> findByMetodoPagoIgnoreCase(String metodoPago);
    Optional<Pago> findByIdPedido(Integer idPedido);
}
