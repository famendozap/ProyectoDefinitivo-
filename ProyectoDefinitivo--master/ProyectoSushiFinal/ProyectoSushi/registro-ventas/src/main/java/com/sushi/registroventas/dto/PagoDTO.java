package com.sushi.registroventas.dto;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class PagoDTO {
    private Integer id;
    private Integer idPedido;
    private BigDecimal monto;
    private String metodoPago;
    private String estado;
}
