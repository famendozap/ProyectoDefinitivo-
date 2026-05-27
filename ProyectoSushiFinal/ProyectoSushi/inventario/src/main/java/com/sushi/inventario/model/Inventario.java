package com.sushi.inventario.model;
import jakarta.persistence.*;
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
    @Column(nullable = false, length = 150)
    private String nombreProducto;
    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer cantidad;
    @NotBlank(message = "La unidad de medida no puede estar vacia")
    @Column(nullable = false, length = 30)
    private String unidadMedida;
    @NotNull(message = "El stock minimo no puede ser nulo")
    @Min(value = 0, message = "El stock minimo no puede ser negativo")
    private Integer stockMinimo;
    @NotBlank(message = "La categoria no puede estar vacia")
    @Column(nullable = false, length = 80)
    private String categoria;
}
