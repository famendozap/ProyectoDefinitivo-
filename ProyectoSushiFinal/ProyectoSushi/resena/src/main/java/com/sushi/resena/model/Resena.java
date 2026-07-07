package com.sushi.resena.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "resena") @Data @NoArgsConstructor @AllArgsConstructor
public class Resena {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del cliente no puede ser nulo")
    @Column(name = "id_cliente", nullable = false)
    @Schema(example = "10")
    private Integer idCliente;
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Column(name = "id_pedido", nullable = false)
    @Schema(example = "55")
    private Integer idPedido;
    @NotNull(message = "La calificacion no puede ser nula")
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacion maxima es 5")
    @Column(nullable = false)
    @Schema(example = "5")
    private Integer calificacion;
    @Size(max = 500, message = "comentario no puede superar los 500 caracteres")
    @Column(length = 500)
    @Schema(example = "Excelente atencion y rapidez en la entrega")
    private String comentario;
    @NotNull(message = "La fecha no puede ser nula")
    @Column(name = "fecha_resena", nullable = false)
    @Schema(example = "2026-06-25T20:00:00")
    private LocalDateTime fechaResena;
}
