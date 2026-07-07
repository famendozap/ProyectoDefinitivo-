package com.sushi.resena.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "resena") @Data @NoArgsConstructor @AllArgsConstructor
public class Resena {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del cliente no puede ser nulo")
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;
    @NotNull(message = "La calificacion no puede ser nula")
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacion maxima es 5")
    @Column(nullable = false)
    private Integer calificacion;
    @Column(length = 500)
    private String comentario;
    @NotNull(message = "La fecha no puede ser nula")
    @Column(name = "fecha_resena", nullable = false)
    private LocalDateTime fechaResena;
}
