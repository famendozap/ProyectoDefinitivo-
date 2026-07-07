package com.sushi.inventario.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Size(max = 150, message = "nombreProducto no puede superar los 150 caracteres")
    @Column(nullable = false, length = 150)
    @Schema(example = "Salmon fresco")
    private String nombreProducto;
    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    @Schema(example = "50")
    private Integer cantidad;
    @NotBlank(message = "La unidad de medida no puede estar vacia")
    @Size(max = 30, message = "unidadMedida no puede superar los 30 caracteres")
    @Column(nullable = false, length = 30)
    @Schema(example = "kg")
    private String unidadMedida;
    @NotNull(message = "El stock minimo no puede ser nulo")
    @Min(value = 0, message = "El stock minimo no puede ser negativo")
    @Schema(example = "10")
    private Integer stockMinimo;
    @NotBlank(message = "La categoria no puede estar vacia")
    @Size(max = 80, message = "categoria no puede superar los 80 caracteres")
    @Column(nullable = false, length = 80)
    @Schema(example = "Pescados")
    private String categoria;
}
