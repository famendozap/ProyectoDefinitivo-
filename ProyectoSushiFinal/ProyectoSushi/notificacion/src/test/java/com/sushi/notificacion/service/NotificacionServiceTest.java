package com.sushi.notificacion.service;

import com.sushi.notificacion.dto.UsuarioDTO;
import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.repository.NotificacionRepository;
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
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NotificacionService service;

    private Notificacion notificacionEjemplo() {
        return new Notificacion(1, 10, "pedido", "Tu pedido fue confirmado", "email",
                "enviada", LocalDateTime.now());
    }

    @Test
    void listar_debeRetornarTodasLasNotificaciones() {
        List<Notificacion> esperado = List.of(notificacionEjemplo());
        when(repository.findAll()).thenReturn(esperado);

        assertThat(service.listar()).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThat(service.buscarPorId(99)).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepository() {
        Notificacion notif = notificacionEjemplo();
        when(repository.save(notif)).thenReturn(notif);

        assertThat(service.guardar(notif)).isEqualTo(notif);
        verify(repository, times(1)).save(notif);
    }

    @Test
    void eliminar_cuandoExiste_debeRetornarTrue() {
        when(repository.existsById(1)).thenReturn(true);

        assertThat(service.eliminar(1)).isTrue();
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void consultarUsuario_cuandoAutenticacionRespondeOk_debeRetornarElUsuarioDTO() {
        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(10);
        usuarioSimulado.setNombre("Ana");
        when(restTemplate.getForObject(eq("http://AUTENTICACION/usuarios/id/10"), eq(UsuarioDTO.class)))
                .thenReturn(usuarioSimulado);

        Optional<UsuarioDTO> resultado = service.consultarUsuario(10);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Ana");
    }

    @Test
    void consultarUsuario_cuandoAutenticacionRespondeNotFound_debeRetornarOptionalVacio() {
        when(restTemplate.getForObject(eq("http://AUTENTICACION/usuarios/id/404"), eq(UsuarioDTO.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        Optional<UsuarioDTO> resultado = service.consultarUsuario(404);

        assertThat(resultado).isEmpty();
    }

    @Test
    void actualizar_notificacion_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Notificacion existente = new Notificacion(1, 10, "pedido", "Tu pedido fue confirmado", "email", "enviada", java.time.LocalDateTime.now());
        Notificacion datos = new Notificacion(null, 10, "pedido", "Tu pedido fue actualizado", "push", "enviada", java.time.LocalDateTime.now());
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Notificacion> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_notificacion_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Notificacion> resultado = service.actualizar(99, new Notificacion());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}