package com.sushi.autenticacion.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;
    @NotBlank(message = "El mail no puede estar vacio")
    @Email(message = "El mail debe tener un formato valido")
    private String mail;
    @NotBlank(message = "La contrasena no puede estar vacia")
    private String pass;
    private Integer idRol;
}
