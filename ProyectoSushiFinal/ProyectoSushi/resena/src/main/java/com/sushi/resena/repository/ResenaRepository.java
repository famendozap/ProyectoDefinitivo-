package com.sushi.resena.repository;
import com.sushi.resena.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByIdCliente(Integer idCliente);
    List<Resena> findByCalificacion(Integer calificacion);
    List<Resena> findByCalificacionGreaterThanEqual(Integer calificacion);
}
