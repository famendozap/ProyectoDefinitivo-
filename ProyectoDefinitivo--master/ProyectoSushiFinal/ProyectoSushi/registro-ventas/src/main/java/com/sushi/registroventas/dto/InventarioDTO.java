package com.sushi.registroventas.dto;
import lombok.Data;
@Data
public class InventarioDTO {
    private Integer id;
    private String nombreProducto;
    private Integer cantidad;
    private String unidadMedida;
    private Integer stockMinimo;
    private String categoria;
}
