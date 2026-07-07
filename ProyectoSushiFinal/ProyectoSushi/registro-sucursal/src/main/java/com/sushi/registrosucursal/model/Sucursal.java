package com.sushi.registrosucursal.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
@Entity @Table(name = "sucursal") @Data @NoArgsConstructor @AllArgsConstructor
public class Sucursal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 100, message = "nombre no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    @Schema(example = "Sushi Centro")
    private String nombre;
    @NotBlank(message = "La direccion no puede estar vacia")
    @Size(max = 200, message = "direccion no puede superar los 200 caracteres")
    @Column(nullable = false, length = 200)
    @Schema(example = "Av. Principal 123")
    private String direccion;
    @NotBlank(message = "La ciudad no puede estar vacia")
    @Size(max = 80, message = "ciudad no puede superar los 80 caracteres")
    @Column(nullable = false, length = 80)
    @Schema(example = "Santiago")
    private String ciudad;
    @NotBlank(message = "El telefono no puede estar vacio")
    @Schema(example = "+56912345678")
    private String telefono;
    @NotBlank(message = "El estado no puede estar vacio")
    @Size(max = 20, message = "estado no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(example = "activa")
    private String estado;
}
