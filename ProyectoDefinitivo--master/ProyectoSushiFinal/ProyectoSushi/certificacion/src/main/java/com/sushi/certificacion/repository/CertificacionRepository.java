package com.sushi.certificacion.repository;
import com.sushi.certificacion.model.Certificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface CertificacionRepository extends JpaRepository<Certificacion, Integer> {
    List<Certificacion> findByEstadoIgnoreCase(String estado);
    List<Certificacion> findByIdSucursal(Integer idSucursal);
    List<Certificacion> findByTipoIgnoreCase(String tipo);
}
