package com.sushi.despacho.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "despacho") @Data @NoArgsConstructor @AllArgsConstructor
public class Despacho {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Column(name = "id_pedido", nullable = false)
    @Schema(example = "55")
    private Integer idPedido;
    @NotBlank(message = "La direccion de entrega no puede estar vacia")
    @Size(max = 200, message = "direccionEntrega no puede superar los 200 caracteres")
    @Column(name = "direccion_entrega", nullable = false, length = 200)
    @Schema(example = "Av. Providencia 1234, Santiago")
    private String direccionEntrega;
    @NotBlank(message = "El estado no puede estar vacio")
    @Size(max = 30, message = "estado no puede superar los 30 caracteres")
    @Column(nullable = false, length = 30)
    @Schema(example = "pendiente")
    private String estado;
    @NotBlank(message = "El tipo de despacho no puede estar vacio")
    @Size(max = 30, message = "tipoDespacho no puede superar los 30 caracteres")
    @Column(name = "tipo_despacho", nullable = false, length = 30)
    @Schema(example = "domicilio")
    private String tipoDespacho;
    @Column(name = "fecha_despacho")
    @Schema(example = "2026-06-25T18:30:00")
    private LocalDateTime fechaDespacho;
    @Column(name = "fecha_entrega_estimada")
    @Schema(example = "2026-06-25T19:30:00")
    private LocalDateTime fechaEntregaEstimada;
}
