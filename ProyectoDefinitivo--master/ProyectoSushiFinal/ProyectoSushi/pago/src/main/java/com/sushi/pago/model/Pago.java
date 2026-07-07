package com.sushi.pago.model;
import jakarta.persistence.*;
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
    private Integer idPedido;
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @NotBlank(message = "El metodo de pago no puede estar vacio")
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;
    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false, length = 20)
    private String estado;
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
}
