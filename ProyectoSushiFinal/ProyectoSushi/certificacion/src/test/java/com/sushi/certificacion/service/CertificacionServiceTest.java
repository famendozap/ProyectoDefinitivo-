package com.sushi.certificacion.service;

import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.repository.CertificacionRepository;
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
class CertificacionServiceTest {

    @Mock
    private CertificacionRepository repository;

    @InjectMocks
    private CertificacionService service;

    @Test
    void listar_debeRetornarTodosLosRegistros() {

        List<Certificacion> esperado = List.of(new Certificacion(1, "Resolucion Sanitaria", "sanitaria", 1, java.time.LocalDate.now(), java.time.LocalDate.now().plusYears(1), "vigente"), new Certificacion(2, "ISO 22000", "calidad", 2, java.time.LocalDate.now(), java.time.LocalDate.now().plusYears(2), "vigente"));
        when(repository.findAll()).thenReturn(esperado);

        List<Certificacion> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).isEqualTo(esperado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarElRegistro() {
        Certificacion entidad = new Certificacion(1, "Resolucion Sanitaria", "sanitaria", 1, java.time.LocalDate.now(), java.time.LocalDate.now().plusYears(1), "vigente");
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Optional<Certificacion> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(entidad);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Certificacion> resultado = service.buscarPorId(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeDelegarEnElRepositoryYRetornarElGuardado() {
        Certificacion entidad = new Certificacion(1, "Resolucion Sanitaria", "sanitaria", 1, java.time.LocalDate.now(), java.time.LocalDate.now().plusYears(1), "vigente");
        when(repository.save(entidad)).thenReturn(entidad);

        Certificacion resultado = service.guardar(entidad);

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
    void actualizar_certificacion_cuandoExiste_debeActualizarYRetornarElRegistro() {
        // Given
        Certificacion existente = new Certificacion(1, "Resolucion Sanitaria", "sanitaria", 1, java.time.LocalDate.now(), java.time.LocalDate.now().plusYears(1), "vigente");
        Certificacion datos = new Certificacion(null, "ISO 22000", "calidad", 1, java.time.LocalDate.now(), java.time.LocalDate.now().plusYears(2), "vigente");
        when(repository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        // When
        java.util.Optional<Certificacion> resultado = service.actualizar(1, datos);

        // Then
        assertThat(resultado).isPresent();
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizar_certificacion_cuandoNoExiste_debeRetornarOptionalVacio() {
        // Given
        when(repository.findById(99)).thenReturn(java.util.Optional.empty());

        // When
        java.util.Optional<Certificacion> resultado = service.actualizar(99, new Certificacion());

        // Then
        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }

}