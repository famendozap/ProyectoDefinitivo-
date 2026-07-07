package com.sushi.soporte.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "ticket") @Data @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del usuario no puede ser nulo")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
    @NotBlank(message = "El asunto no puede estar vacio")
    @Column(nullable = false, length = 200)
    private String asunto;
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Column(nullable = false, length = 1000)
    private String descripcion;
    @NotBlank(message = "La categoria no puede estar vacia")
    @Column(nullable = false, length = 50)
    private String categoria;
    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false, length = 20)
    private String estado;
    @NotBlank(message = "La prioridad no puede estar vacia")
    @Column(nullable = false, length = 20)
    private String prioridad;
    @NotNull(message = "La fecha de apertura no puede ser nula")
    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;
    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;
}
