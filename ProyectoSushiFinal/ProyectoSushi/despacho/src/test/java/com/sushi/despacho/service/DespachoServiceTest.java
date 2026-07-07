package com.sushi.despacho.service;

import com.sushi.despacho.dto.PagoDTO;
import com.sushi.despacho.model.Despacho;
import com.sushi.despacho.repository.DespachoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DespachoServiceTest {

    @Mock
    private DespachoRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DespachoService service;

    private Despacho despachoEjemplo() {
        return new Despacho(1, 55, "Av. Siempre Viva 742", "pendiente", "domicilio",
                null, LocalDateTime.now().plusHours(1));
    }

    @Test
    void listar_debeRetornarTodosLosDespachos() {
        List<Despacho> esperado = List.of(despachoEjemplo());
        when(repository.findAll()).thenReturn(esperado);

        List<Despacho> resultado = service.listar();

        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElDespacho() {
        Despacho despacho = despachoEjemplo();
        when(repository.findById(1)).thenReturn(Optional.of(despacho));

        Optional<Despacho> resultado = service.buscarPorId(1);

        assertThat(resultado).contains(despacho);
    }

    @Test
    void guardar_debeDelegarEnElRepository() {
        Despacho despacho = despachoEjemplo();
        when(repository.save(despacho)).thenReturn(despacho);

        Despacho resultado = service.guardar(despacho);

        assertThat(resultado).isEqualTo(despacho);
        verify(repository, times(1)).save(despacho);
    }

    @Test
    void eliminar_cuandoExiste_debeRetornarTrue() {
        when(repository.existsById(1)).thenReturn(true);

        assertThat(service.eliminar(1)).isTrue();
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        when(repository.existsById(1)).thenReturn(false);

        assertThat(service.eliminar(1)).isFalse();
        verify(repository, never()).deleteById(any());
    }

    @Test
    void verificarPago_cuandoPagoServiceRespondeOk_debeRetornarElPagoDTO() {

        PagoDTO pagoSimulado = new PagoDTO();
        pagoSimulado.setEstado("completado");
        when(restTemplate.getForObject(eq("http://PAGO/pagos/pedido/55"), eq(PagoDTO.class)))
                .thenReturn(pagoSimulado);

        Optional<PagoDTO> resultado = service.verificarPago(55);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getEstado()).isEqualTo("completado");
    }

    @Test
    void verificarPago_cuandoPagoServiceRespondeNotFound_debeRetornarOptionalVacio() {

        when(restTemplate.getForObject(eq("http://PAGO/pagos/pedido/999"), eq(PagoDTO.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        Optional<PagoDTO> resultado = service.verificarPago(999);

        assertThat(resultado).isEmpty();
    }
}
