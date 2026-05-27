package com.sushi.registrosucursal.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
@Entity @Table(name = "sucursal") @Data @NoArgsConstructor @AllArgsConstructor
public class Sucursal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String nombre;
    @NotBlank(message = "La direccion no puede estar vacia")
    @Column(nullable = false, length = 200)
    private String direccion;
    @NotBlank(message = "La ciudad no puede estar vacia")
    @Column(nullable = false, length = 80)
    private String ciudad;
    @NotBlank(message = "El telefono no puede estar vacio")
    private String telefono;
    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false, length = 20)
    private String estado;
}
