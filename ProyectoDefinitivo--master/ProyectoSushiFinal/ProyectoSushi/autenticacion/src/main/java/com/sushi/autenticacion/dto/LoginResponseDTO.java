package com.sushi.autenticacion.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String mensaje;
    private String mail;
    private String rol;
    private String token;
}
