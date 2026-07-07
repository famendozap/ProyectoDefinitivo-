package com.sushi.autenticacion.service;
import com.sushi.autenticacion.dto.UsuarioDTO;
import com.sushi.autenticacion.model.Roles;
import com.sushi.autenticacion.model.Usuario;
import com.sushi.autenticacion.repository.RolesRepository;
import com.sushi.autenticacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNombre(), u.getApellido(), u.getMail(), u.getRoles().getTipoDERol());
    }
    public List<UsuarioDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    public Optional<UsuarioDTO> buscarPorId(Integer id) {
        return repository.findById(id).map(this::toDTO);
    }
    public Optional<UsuarioDTO> buscarPorMail(String mail) {
        return repository.findByMail(mail).map(this::toDTO);
    }
    public List<UsuarioDTO> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<UsuarioDTO> buscarPorRol(Integer idRoles) {
        return repository.findByRolesId(idRoles).stream().map(this::toDTO).collect(Collectors.toList());
    }
    public Usuario guardar(Usuario usuario) {
        usuario.setPass(passwordEncoder.encode(usuario.getPass()));
        return repository.save(usuario);
    }
    public Optional<Usuario> actualizar(Integer id, Usuario datosNuevos) {
        return repository.findById(id).map(u -> {
            u.setNombre(datosNuevos.getNombre());
            u.setApellido(datosNuevos.getApellido());
            u.setMail(datosNuevos.getMail());
            if (datosNuevos.getPass() != null && !datosNuevos.getPass().isBlank()) {
                u.setPass(passwordEncoder.encode(datosNuevos.getPass()));
            }
            if (datosNuevos.getRoles() != null) {
                u.setRoles(datosNuevos.getRoles());
            }
            return repository.save(u);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
