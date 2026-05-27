package com.sushi.certificacion.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name = "certificacion") @Data @NoArgsConstructor @AllArgsConstructor
public class Certificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre del certificado no puede estar vacio")
    @Column(nullable = false, length = 150)
    private String nombre;
    @NotBlank(message = "El tipo no puede estar vacio")
    @Column(nullable = false, length = 80)
    private String tipo;
    @NotNull(message = "El id de la sucursal no puede ser nulo")
    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;
    @NotNull(message = "La fecha de emision no puede ser nula")
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;
    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false, length = 20)
    private String estado;
}
