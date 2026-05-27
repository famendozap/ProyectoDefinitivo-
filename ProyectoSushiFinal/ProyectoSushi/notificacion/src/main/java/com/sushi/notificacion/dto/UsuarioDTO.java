package com.sushi.notificacion.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String mail;
    private String rol;
}
