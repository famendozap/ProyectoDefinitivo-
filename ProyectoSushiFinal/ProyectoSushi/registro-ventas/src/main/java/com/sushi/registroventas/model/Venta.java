package com.sushi.registroventas.model;
import jakarta.persistence.*;
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
    private Integer idPedido;
    @NotNull(message = "El id de la sucursal no puede ser nulo")
    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;
    @NotNull(message = "El total no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @NotNull(message = "La fecha de venta no puede ser nula")
    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;
    @NotBlank(message = "El canal de venta no puede estar vacio")
    @Column(name = "canal_venta", nullable = false, length = 30)
    private String canalVenta;
}
