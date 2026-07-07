package com.sushi.despacho.repository;
import com.sushi.despacho.model.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Integer> {
    List<Despacho> findByEstadoIgnoreCase(String estado);
    List<Despacho> findByTipoDespachoIgnoreCase(String tipoDespacho);
    Optional<Despacho> findByIdPedido(Integer idPedido);
}
