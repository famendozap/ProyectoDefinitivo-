package com.sushi.autenticacion.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El tipo de rol no puede estar vacio")
    @Column(name = "tipo_de_rol", nullable = false, unique = true, length = 50)
    private String tipoDERol;
}
