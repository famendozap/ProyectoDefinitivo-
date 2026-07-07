package com.sushi.notificacion.repository;
import com.sushi.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByIdUsuario(Integer idUsuario);
    List<Notificacion> findByEstadoIgnoreCase(String estado);
    List<Notificacion> findByTipoIgnoreCase(String tipo);
    List<Notificacion> findByCanalIgnoreCase(String canal);
}
