package com.sushi.autenticacion.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 100, message = "nombre no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    @Schema(example = "Ana")
    private String nombre; 
    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(max = 100, message = "apellido no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    @Schema(example = "Soto")
    private String apellido;
    @NotBlank(message = "El mail no puede estar vacio")
    @Email(message = "El mail debe tener un formato valido")
    @Column(nullable = false, unique = true)
    @Schema(example = "ana.soto@sushi.com")
    private String mail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "La contrasena no puede estar vacia")
    @Column(nullable = false)
    @Schema(example = "MiClaveSegura123")
    private String pass;
    @ManyToOne
    @JoinColumn(name = "id_roles", nullable = false)
    private Roles roles;
}
