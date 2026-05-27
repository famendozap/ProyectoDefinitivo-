package com.sushi.despacho.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PagoDTO {
    private Integer id;
    private Integer idPedido;
    private Double monto;
    private String metodoPago;
    private String estado;
}
