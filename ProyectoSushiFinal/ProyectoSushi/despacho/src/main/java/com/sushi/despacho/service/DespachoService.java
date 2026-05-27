package com.sushi.despacho.service;
import com.sushi.despacho.dto.PagoDTO;
import com.sushi.despacho.model.Despacho;
import com.sushi.despacho.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.*;
@Service
public class DespachoService {
    @Autowired
    private DespachoRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    private static final String PAGO_URL = "http://localhost:8093/pagos/pedido/";
    public List<Despacho> listar() { return repository.findAll(); }
    public Optional<Despacho> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Despacho> buscarPorEstado(String estado) { return repository.findByEstadoIgnoreCase(estado); }
    public List<Despacho> buscarPorTipo(String tipo) { return repository.findByTipoDespachoIgnoreCase(tipo); }
    public Optional<Despacho> buscarPorPedido(Integer idPedido) { return repository.findByIdPedido(idPedido); }
    public Despacho guardar(Despacho despacho) { return repository.save(despacho); }
    
    public Optional<PagoDTO> verificarPago(Integer idPedido) {
        try {
            PagoDTO pago = restTemplate.getForObject(PAGO_URL + idPedido, PagoDTO.class);
            return Optional.ofNullable(pago);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
    public Optional<Despacho> actualizar(Integer id, Despacho datos) {
        return repository.findById(id).map(d -> {
            d.setDireccionEntrega(datos.getDireccionEntrega());
            d.setEstado(datos.getEstado());
            d.setTipoDespacho(datos.getTipoDespacho());
            d.setFechaDespacho(datos.getFechaDespacho());
            d.setFechaEntregaEstimada(datos.getFechaEntregaEstimada());
            return repository.save(d);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
