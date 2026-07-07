package com.sushi.pago.service;

import com.sushi.pago.model.Pago;
import com.sushi.pago.repository.PagoRepository;
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
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @InjectMocks
    private PagoService service;

    @Test
    void listar_debeRetornarTodosLosRegistros() {

        List<Pago> esperado = List.of(new Pago(1, 100, new java.math.BigDecimal("15990"), "webpay", "completado", java.time.LocalDateTime.now()), new Pago(2, 101, new java.math.BigDecimal("8990"), "efectivo", "pendiente", java.time.LocalDateTime.now()));
        when(repository.findAll()).thenReturn(esperado);

        List<Pago> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElRegistro() {
        Pago entidad = new Pago(1, 100, new java.math.BigDecimal("15990"), "webpay", "completado", java.time.LocalDateTime.now());
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Optional<Pago> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(entidad);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Pago> resultado = service.buscarPorId(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepositoryYRetornarElGuardado() {
        Pago entidad = new Pago(1, 100, new java.math.BigDecimal("15990"), "webpay", "completado", java.time.LocalDateTime.now());
        when(repository.save(entidad)).thenReturn(entidad);

        Pago resultado = service.guardar(entidad);

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
    void actualizar_pago_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Pago existente = new Pago(1, 55, new java.math.BigDecimal("15990"), "webpay", "completado", java.time.LocalDateTime.now());
        Pago datos = new Pago(null, 55, new java.math.BigDecimal("9990"), "efectivo", "pendiente", java.time.LocalDateTime.now());
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Pago> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_pago_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Pago> resultado = service.actualizar(99, new Pago());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}