package com.sushi.autenticacion.service;

import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.repository.RolesRepository;
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
class RolesServiceTest {

    @Mock
    private RolesRepository repository;

    @InjectMocks
    private RolesService service;

    @Test
    void listar_debeRetornarTodosLosRoles() {
        List<Roles> esperado = List.of(new Roles(1, "ADMIN"), new Roles(2, "CLIENTE"));
        when(repository.findAll()).thenReturn(esperado);

        assertThat(service.listar()).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorTipo_cuandoExiste_debeRetornarElRol() {
        Roles admin = new Roles(1, "ADMIN");
        when(repository.findByTipoDERolIgnoreCase("admin")).thenReturn(Optional.of(admin));

        Optional<Roles> resultado = service.buscarPorTipo("admin");

        assertThat(resultado).contains(admin);
    }

    @Test
    void guardar_debeDelegarEnElRepository() {
        Roles rol = new Roles(1, "ADMIN");
        when(repository.save(rol)).thenReturn(rol);

        assertThat(service.guardar(rol)).isEqualTo(rol);
        verify(repository, times(1)).save(rol);
    }

    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        when(repository.existsById(5)).thenReturn(false);

        assertThat(service.eliminar(5)).isFalse();
        verify(repository, never()).deleteById(any());
    }
}
