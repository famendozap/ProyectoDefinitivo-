package com.sushi.certificacion.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name = "certificacion") @Data @NoArgsConstructor @AllArgsConstructor
public class Certificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre del certificado no puede estar vacio")
    @Size(max = 150, message = "nombre no puede superar los 150 caracteres")
    @Column(nullable = false, length = 150)
    @Schema(example = "Resolucion Sanitaria")
    private String nombre;
    @NotBlank(message = "El tipo no puede estar vacio")
    @Size(max = 80, message = "tipo no puede superar los 80 caracteres")
    @Column(nullable = false, length = 80)
    @Schema(example = "sanitaria")
    private String tipo;
    @NotNull(message = "El id de la sucursal no puede ser nulo")
    @Column(name = "id_sucursal", nullable = false)
    @Schema(example = "1")
    private Integer idSucursal;
    @NotNull(message = "La fecha de emision no puede ser nula")
    @Column(name = "fecha_emision", nullable = false)
    @Schema(example = "2026-01-15")
    private LocalDate fechaEmision;
    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    @Column(name = "fecha_vencimiento", nullable = false)
    @Schema(example = "2027-01-15")
    private LocalDate fechaVencimiento;
    @NotBlank(message = "El estado no puede estar vacio")
    @Size(max = 20, message = "estado no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(example = "vigente")
    private String estado;
}
