package com.sushi.pago.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name = "pago") @Data @NoArgsConstructor @AllArgsConstructor
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Column(name = "id_pedido", nullable = false)
    @Schema(example = "55")
    private Integer idPedido;
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(example = "15990.00")
    private BigDecimal monto;
    @NotBlank(message = "El metodo de pago no puede estar vacio")
    @Size(max = 50, message = "metodoPago no puede superar los 50 caracteres")
    @Column(name = "metodo_pago", nullable = false, length = 50)
    @Schema(example = "webpay")
    private String metodoPago;
    @NotBlank(message = "El estado no puede estar vacio")
    @Size(max = 20, message = "estado no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(example = "completado")
    private String estado;
    @Column(name = "fecha_pago")
    @Schema(example = "2026-06-25T18:00:00")
    private LocalDateTime fechaPago;
}
