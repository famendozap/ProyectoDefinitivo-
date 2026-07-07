package com.sushi.resena.service;

import com.sushi.resena.model.Resena;
import com.sushi.resena.repository.ResenaRepository;
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
class ResenaServiceTest {

    @Mock
    private ResenaRepository repository;

    @InjectMocks
    private ResenaService service;

    @Test
    void listar_debeRetornarTodosLosRegistros() {

        List<Resena> esperado = List.of(new Resena(1, 10, 55, 5, "Excelente servicio", java.time.LocalDateTime.now()), new Resena(2, 11, 56, 3, "Demoraron mucho", java.time.LocalDateTime.now()));
        when(repository.findAll()).thenReturn(esperado);

        List<Resena> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElRegistro() {
        Resena entidad = new Resena(1, 10, 55, 5, "Excelente servicio", java.time.LocalDateTime.now());
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Optional<Resena> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(entidad);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Resena> resultado = service.buscarPorId(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepositoryYRetornarElGuardado() {
        Resena entidad = new Resena(1, 10, 55, 5, "Excelente servicio", java.time.LocalDateTime.now());
        when(repository.save(entidad)).thenReturn(entidad);

        Resena resultado = service.guardar(entidad);

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
    void actualizar_resena_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Resena existente = new Resena(1, 10, 55, 5, "Excelente servicio", java.time.LocalDateTime.now());
        Resena datos = new Resena(null, 10, 55, 3, "Servicio regular", java.time.LocalDateTime.now());
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Resena> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_resena_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Resena> resultado = service.actualizar(99, new Resena());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}