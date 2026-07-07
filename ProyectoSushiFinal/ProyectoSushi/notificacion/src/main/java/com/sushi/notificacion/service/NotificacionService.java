package com.sushi.notificacion.service;
import com.sushi.notificacion.dto.UsuarioDTO;
import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.*;
@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    private static final String USUARIO_URL = "http://AUTENTICACION/usuarios/id/";
    public List<Notificacion> listar() { return repository.findAll(); }
    public Optional<Notificacion> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Notificacion> buscarPorUsuario(Integer idUsuario) { return repository.findByIdUsuario(idUsuario); }
    public List<Notificacion> buscarPorEstado(String estado) { return repository.findByEstadoIgnoreCase(estado); }
    public List<Notificacion> buscarPorTipo(String tipo) { return repository.findByTipoIgnoreCase(tipo); }
    public List<Notificacion> buscarPorCanal(String canal) { return repository.findByCanalIgnoreCase(canal); }
    public Notificacion guardar(Notificacion notif) { return repository.save(notif); }

    public Optional<UsuarioDTO> consultarUsuario(Integer idUsuario) {
        try {
            UsuarioDTO usuario = restTemplate.getForObject(USUARIO_URL + idUsuario, UsuarioDTO.class);
            return Optional.ofNullable(usuario);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
    public Optional<Notificacion> actualizar(Integer id, Notificacion datos) {
        return repository.findById(id).map(n -> {
            n.setTipo(datos.getTipo());
            n.setMensaje(datos.getMensaje());
            n.setCanal(datos.getCanal());
            n.setEstado(datos.getEstado());
            return repository.save(n);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
