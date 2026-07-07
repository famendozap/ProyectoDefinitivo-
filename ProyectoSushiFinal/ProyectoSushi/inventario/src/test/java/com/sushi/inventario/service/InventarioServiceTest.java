package com.sushi.inventario.service;

import com.sushi.inventario.model.Inventario;
import com.sushi.inventario.repository.InventarioRepository;
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
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioService service;

    @Test
    void listar_debeRetornarTodosLosRegistros() {

        List<Inventario> esperado = List.of(new Inventario(1, "Salmon fresco", 50, "kg", 10, "pescado"), new Inventario(2, "Arroz para sushi", 200, "kg", 30, "abarrotes"));
        when(repository.findAll()).thenReturn(esperado);

        List<Inventario> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElRegistro() {
        Inventario entidad = new Inventario(1, "Salmon fresco", 50, "kg", 10, "pescado");
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Optional<Inventario> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(entidad);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Inventario> resultado = service.buscarPorId(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepositoryYRetornarElGuardado() {
        Inventario entidad = new Inventario(1, "Salmon fresco", 50, "kg", 10, "pescado");
        when(repository.save(entidad)).thenReturn(entidad);

        Inventario resultado = service.guardar(entidad);

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
    void actualizar_inventario_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Inventario existente = new Inventario(1, "Salmon fresco", 50, "kg", 10, "pescado");
        Inventario datos = new Inventario(null, "Salmon ahumado", 30, "kg", 5, "pescado");
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Inventario> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_inventario_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Inventario> resultado = service.actualizar(99, new Inventario());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}