package com.sushi.autenticacion.service;

import com.sushi.autenticacion.dto.UsuarioDTO;
import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.model.Usuario;
import com.sushi.autenticacion.repository.RolesRepository;
import com.sushi.autenticacion.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuarioEjemplo() {
        Roles rol = new Roles(1, "CLIENTE");
        return new Usuario(1, "Ana", "Soto", "ana@correo.com", "hashSecreto", rol);
    }

    @Test
    void listar_debeRetornarUsuariosMapeadosADTO() {
        when(repository.findAll()).thenReturn(List.of(usuarioEjemplo()));

        List<UsuarioDTO> resultado = service.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMail()).isEqualTo("ana@correo.com");
        assertThat(resultado.get(0).getRol()).isEqualTo("CLIENTE");

        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarUsuarioDTO() {
        when(repository.findById(1)).thenReturn(Optional.of(usuarioEjemplo()));

        Optional<UsuarioDTO> resultado = service.buscarPorId(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Ana");
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThat(service.buscarPorId(99)).isEmpty();
    }

    @Test
    void guardar_debeCodificarLaContrasenaAntesDeGuardar() {
        Usuario nuevo = usuarioEjemplo();
        nuevo.setPass("claveSinCifrar");
        when(passwordEncoder.encode("claveSinCifrar")).thenReturn("claveCifrada");
        when(repository.save(nuevo)).thenReturn(nuevo);

        Usuario resultado = service.guardar(nuevo);

        assertThat(resultado.getPass()).isEqualTo("claveCifrada");
        verify(passwordEncoder, times(1)).encode("claveSinCifrar");
        verify(repository, times(1)).save(nuevo);
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
}
