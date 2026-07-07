package com.sushi.autenticacion.service;

import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.model.Usuario;
import com.sushi.autenticacion.model.UsuarioRol;
import com.sushi.autenticacion.repository.UsuarioRolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRolServiceTest {

    @Mock
    private UsuarioRolRepository repository;

    @InjectMocks
    private UsuarioRolService service;

    private Usuario usuarioEjemplo() {
        return new Usuario(1, "Ana", "Soto", "ana@correo.com", "hashSecreto", new Roles(1, "CLIENTE"));
    }

    @Test
    void listar_debeRetornarTodasLasRelaciones() {
        Roles rol = new Roles(1, "ADMIN");
        List<UsuarioRol> esperado = List.of(new UsuarioRol(1, usuarioEjemplo(), rol));
        when(repository.findAll()).thenReturn(esperado);

        assertThat(service.listar()).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorRol_debeDelegarEnElRepository() {
        Roles rol = new Roles(2, "CLIENTE");
        List<UsuarioRol> esperado = List.of(new UsuarioRol(2, usuarioEjemplo(), rol));
        when(repository.findByRolId(2)).thenReturn(esperado);

        assertThat(service.buscarPorRol(2)).isEqualTo(esperado);
    }

    @Test
    void buscarPorUsuario_debeDelegarEnElRepository() {
        Roles rol = new Roles(1, "CLIENTE");
        List<UsuarioRol> esperado = List.of(new UsuarioRol(3, usuarioEjemplo(), rol));
        when(repository.findByUsuarioId(1)).thenReturn(esperado);

        assertThat(service.buscarPorUsuario(1)).isEqualTo(esperado);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThat(service.buscarPorId(99)).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepository() {
        Roles rol = new Roles(1, "ADMIN");
        UsuarioRol relacion = new UsuarioRol(1, usuarioEjemplo(), rol);
        when(repository.save(relacion)).thenReturn(relacion);

        assertThat(service.guardar(relacion)).isEqualTo(relacion);
        verify(repository, times(1)).save(relacion);
    }
}
