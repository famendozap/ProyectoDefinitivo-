package com.sushi.registroventas.service;
import com.sushi.registroventas.dto.InventarioDTO;
import com.sushi.registroventas.dto.PagoDTO;
import com.sushi.registroventas.model.Venta;
import com.sushi.registroventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import java.util.Optional;
@Service
public class VentaService {
    @Autowired
    private VentaRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    private static final String INVENTARIO_URL = "http://localhost:8091/inventario/id/";
    private static final String PAGO_URL = "http://localhost:8093/pagos/pedido/";
    public List<Venta> listar() { return repository.findAll(); }
    public Optional<Venta> buscarPorId(Integer id) { return repository.findById(id); }
    public List<Venta> buscarPorSucursal(Integer idSucursal) { return repository.findByIdSucursal(idSucursal); }
    public List<Venta> buscarPorCanal(String canal) { return repository.findByCanalVentaIgnoreCase(canal); }
    public Optional<Venta> buscarPorPedido(Integer idPedido) { return repository.findByIdPedido(idPedido); }
    public Venta guardar(Venta venta) { return repository.save(venta); }
    
    public Optional<InventarioDTO> consultarInventario(Integer idProducto) {
        try {
            InventarioDTO inventario = restTemplate.getForObject(INVENTARIO_URL + idProducto, InventarioDTO.class);
            return Optional.ofNullable(inventario);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
    
    public Optional<PagoDTO> consultarPago(Integer idPedido) {
        try {
            PagoDTO pago = restTemplate.getForObject(PAGO_URL + idPedido, PagoDTO.class);
            return Optional.ofNullable(pago);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
    public Optional<Venta> actualizar(Integer id, Venta datos) {
        return repository.findById(id).map(v -> {
            v.setTotal(datos.getTotal());
            v.setCanalVenta(datos.getCanalVenta());
            v.setFechaVenta(datos.getFechaVenta());
            return repository.save(v);
        });
    }
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
