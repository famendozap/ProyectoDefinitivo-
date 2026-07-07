package com.sushi.registroventas.service;

import com.sushi.registroventas.dto.InventarioDTO;
import com.sushi.registroventas.dto.PagoDTO;
import com.sushi.registroventas.model.Venta;
import com.sushi.registroventas.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VentaService service;

    private Venta ventaEjemplo() {
        return new Venta(1, 55, 2, new BigDecimal("24990"), LocalDateTime.now(), "app");
    }

    @Test
    void listar_debeRetornarTodasLasVentas() {
        List<Venta> esperado = List.of(ventaEjemplo());
        when(repository.findAll()).thenReturn(esperado);

        assertThat(service.listar()).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarLaVenta() {
        Venta venta = ventaEjemplo();
        when(repository.findById(1)).thenReturn(Optional.of(venta));

        assertThat(service.buscarPorId(1)).contains(venta);
    }

    @Test
    void guardar_debeDelegarEnElRepository() {
        Venta venta = ventaEjemplo();
        when(repository.save(venta)).thenReturn(venta);

        assertThat(service.guardar(venta)).isEqualTo(venta);
        verify(repository, times(1)).save(venta);
    }

    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        when(repository.existsById(1)).thenReturn(false);

        assertThat(service.eliminar(1)).isFalse();
        verify(repository, never()).deleteById(any());
    }

    @Test
    void consultarInventario_cuandoInventarioRespondeOk_debeRetornarElInventarioDTO() {
        InventarioDTO inventarioSimulado = new InventarioDTO();
        inventarioSimulado.setId(7);
        inventarioSimulado.setCantidad(50);
        when(restTemplate.getForObject(eq("http://INVENTARIO/inventario/id/7"), eq(InventarioDTO.class)))
                .thenReturn(inventarioSimulado);

        Optional<InventarioDTO> resultado = service.consultarInventario(7);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCantidad()).isEqualTo(50);
    }

    @Test
    void consultarPago_cuandoPagoRespondeNotFound_debeRetornarOptionalVacio() {
        when(restTemplate.getForObject(eq("http://PAGO/pagos/pedido/999"), eq(PagoDTO.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        Optional<PagoDTO> resultado = service.consultarPago(999);

        assertThat(resultado).isEmpty();
    }

    @Test
    void actualizar_venta_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Venta existente = new Venta(1, 55, 2, new java.math.BigDecimal("24990"), java.time.LocalDateTime.now(), "app");
        Venta datos = new Venta(null, 55, 2, new java.math.BigDecimal("19990"), java.time.LocalDateTime.now(), "web");
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Venta> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_venta_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Venta> resultado = service.actualizar(99, new Venta());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}