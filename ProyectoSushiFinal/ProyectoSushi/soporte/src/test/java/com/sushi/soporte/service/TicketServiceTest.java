package com.sushi.soporte.service;

import com.sushi.soporte.model.Ticket;
import com.sushi.soporte.repository.TicketRepository;
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
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketService service;

    @Test
    void listar_debeRetornarTodosLosRegistros() {

        List<Ticket> esperado = List.of(new Ticket(1, 10, "Pedido no llego", "El pedido #55 nunca llego a destino", "entrega", "abierto", "alta", java.time.LocalDateTime.now(), null), new Ticket(2, 11, "Producto en mal estado", "El salmon llego en mal estado", "calidad", "abierto", "media", java.time.LocalDateTime.now(), null));
        when(repository.findAll()).thenReturn(esperado);

        List<Ticket> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElRegistro() {
        Ticket entidad = new Ticket(1, 10, "Pedido no llego", "El pedido #55 nunca llego a destino", "entrega", "abierto", "alta", java.time.LocalDateTime.now(), null);
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Optional<Ticket> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(entidad);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Ticket> resultado = service.buscarPorId(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepositoryYRetornarElGuardado() {
        Ticket entidad = new Ticket(1, 10, "Pedido no llego", "El pedido #55 nunca llego a destino", "entrega", "abierto", "alta", java.time.LocalDateTime.now(), null);
        when(repository.save(entidad)).thenReturn(entidad);

        Ticket resultado = service.guardar(entidad);

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
    void actualizar_ticket_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Ticket existente = new Ticket(1, 10, "Pedido no llego", "El pedido nunca llego", "entrega", "abierto", "alta", java.time.LocalDateTime.now(), null);
        Ticket datos = new Ticket(null, 10, "Pedido no llego", "Actualizado: el pedido llego tarde", "entrega", "cerrado", "baja", java.time.LocalDateTime.now(), java.time.LocalDateTime.now());
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Ticket> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_ticket_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Ticket> resultado = service.actualizar(99, new Ticket());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}