package com.sushi.autenticacion.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Size(max = 50, message = "tipoDERol no puede superar los 50 caracteres")
    @Column(name = "tipo_de_rol", nullable = false, unique = true, length = 50)
    @Schema(example = "ADMIN")
    private String tipoDERol;
}
