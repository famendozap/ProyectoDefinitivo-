package com.sushi.autenticacion.repository;
import com.sushi.autenticacion.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Integer> {
    List<UsuarioRol> findByRolId(Integer idRol);
}
