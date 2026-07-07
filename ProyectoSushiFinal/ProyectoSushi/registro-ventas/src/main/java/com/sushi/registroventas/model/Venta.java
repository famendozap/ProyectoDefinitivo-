package com.sushi.registroventas.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name = "venta") @Data @NoArgsConstructor @AllArgsConstructor
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Column(name = "id_pedido", nullable = false)
    @Schema(example = "55")
    private Integer idPedido;
    @NotNull(message = "El id de la sucursal no puede ser nulo")
    @Column(name = "id_sucursal", nullable = false)
    @Schema(example = "1")
    private Integer idSucursal;
    @NotNull(message = "El total no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(example = "24990.00")
    private BigDecimal total;
    @NotNull(message = "La fecha de venta no puede ser nula")
    @Column(name = "fecha_venta", nullable = false)
    @Schema(example = "2026-06-25T18:00:00")
    private LocalDateTime fechaVenta;
    @NotBlank(message = "El canal de venta no puede estar vacio")
    @Size(max = 30, message = "canalVenta no puede superar los 30 caracteres")
    @Column(name = "canal_venta", nullable = false, length = 30)
    @Schema(example = "app")
    private String canalVenta;
}
