package com.sushi.autenticacion.model;
import jakarta.persistence.*;
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
    @Column(nullable = false, length = 100)
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String apellido;
    @NotBlank(message = "El mail no puede estar vacio")
    @Email(message = "El mail debe tener un formato valido")
    @Column(nullable = false, unique = true)
    private String mail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "La contrasena no puede estar vacia")
    @Column(nullable = false)
    private String pass;
    @ManyToOne
    @JoinColumn(name = "id_roles", nullable = false)
    private Roles roles;
}
