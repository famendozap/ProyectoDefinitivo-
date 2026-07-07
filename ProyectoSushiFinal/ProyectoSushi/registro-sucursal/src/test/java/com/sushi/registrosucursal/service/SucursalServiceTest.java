package com.sushi.registrosucursal.service;

import com.sushi.registrosucursal.model.Sucursal;
import com.sushi.registrosucursal.repository.SucursalRepository;
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
class SucursalServiceTest {

    @Mock
    private SucursalRepository repository;

    @InjectMocks
    private SucursalService service;

    @Test
    void listar_debeRetornarTodosLosRegistros() {

        List<Sucursal> esperado = List.of(new Sucursal(1, "Sushi Centro", "Av. Principal 123", "Santiago", "+56912345678", "activa"), new Sucursal(2, "Sushi Mall", "Mall Plaza 45", "Concepcion", "+56987654321", "activa"));
        when(repository.findAll()).thenReturn(esperado);

        List<Sucursal> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElRegistro() {
        Sucursal entidad = new Sucursal(1, "Sushi Centro", "Av. Principal 123", "Santiago", "+56912345678", "activa");
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Optional<Sucursal> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(entidad);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Sucursal> resultado = service.buscarPorId(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepositoryYRetornarElGuardado() {
        Sucursal entidad = new Sucursal(1, "Sushi Centro", "Av. Principal 123", "Santiago", "+56912345678", "activa");
        when(repository.save(entidad)).thenReturn(entidad);

        Sucursal resultado = service.guardar(entidad);

        assertThat(resultado).isEqualTo(entidad);
        verify(repository, times(1)).save(entidad);
    }

    @Test
    void eliminar_cuandoExiste_debeEliminarYRetornarTrue() {
        when(repository.existsById(1)).thenReturn(true);

        boolean resultado = service.eliminar(1);

        assertThat(resultado).isTrue();
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalseYNoLlamarDeleteById() {
        when(repository.existsById(1)).thenReturn(false);

        boolean resultado = service.eliminar(1);

        assertThat(resultado).isFalse();
        verify(repository, never()).deleteById(any());
    }

    @Test
    void actualizar_sucursal_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Sucursal existente = new Sucursal(1, "Sushi Centro", "Av. Principal 123", "Santiago", "+56912345678", "activa");
        Sucursal datos = new Sucursal(null, "Sushi Centro Editado", "Av. Nueva 456", "Santiago", "+56987654321", "activa");
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Sucursal> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_sucursal_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Sucursal> resultado = service.actualizar(99, new Sucursal());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}