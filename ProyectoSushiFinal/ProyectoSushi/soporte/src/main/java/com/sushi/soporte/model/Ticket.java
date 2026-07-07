package com.sushi.soporte.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "ticket") @Data @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del usuario no puede ser nulo")
    @Column(name = "id_usuario", nullable = false)
    @Schema(example = "10")
    private Integer idUsuario;
    @NotBlank(message = "El asunto no puede estar vacio")
    @Size(max = 200, message = "asunto no puede superar los 200 caracteres")
    @Column(nullable = false, length = 200)
    @Schema(example = "Pedido no llego")
    private String asunto;
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(max = 1000, message = "descripcion no puede superar los 1000 caracteres")
    @Column(nullable = false, length = 1000)
    @Schema(example = "El pedido numero 55 nunca llego a destino")
    private String descripcion;
    @NotBlank(message = "La categoria no puede estar vacia")
    @Size(max = 50, message = "categoria no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    @Schema(example = "entrega")
    private String categoria;
    @NotBlank(message = "El estado no puede estar vacio")
    @Size(max = 20, message = "estado no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(example = "abierto")
    private String estado;
    @NotBlank(message = "La prioridad no puede estar vacia")
    @Size(max = 20, message = "prioridad no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(example = "alta")
    private String prioridad;
    @NotNull(message = "La fecha de apertura no puede ser nula")
    @Column(name = "fecha_apertura", nullable = false)
    @Schema(example = "2026-06-25T09:00:00")
    private LocalDateTime fechaApertura;
    @Column(name = "fecha_cierre")
    @Schema(example = "2026-06-25T17:00:00")
    private LocalDateTime fechaCierre;
}
