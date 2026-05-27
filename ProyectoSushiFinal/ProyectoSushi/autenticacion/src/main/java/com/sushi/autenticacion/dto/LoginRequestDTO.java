package com.sushi.autenticacion.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginRequestDTO {
    @NotBlank
    @Email
    private String mail;
    @NotBlank
    private String pass;
}
