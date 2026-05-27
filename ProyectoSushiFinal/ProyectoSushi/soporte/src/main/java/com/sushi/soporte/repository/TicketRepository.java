package com.sushi.soporte.repository;
import com.sushi.soporte.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByIdUsuario(Integer idUsuario);
    List<Ticket> findByEstadoIgnoreCase(String estado);
    List<Ticket> findByPrioridadIgnoreCase(String prioridad);
    List<Ticket> findByCategoriaIgnoreCase(String categoria);
}
