package com.sushi.despacho.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "despacho") @Data @NoArgsConstructor @AllArgsConstructor
public class Despacho {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;
    @NotBlank(message = "La direccion de entrega no puede estar vacia")
    @Column(name = "direccion_entrega", nullable = false, length = 200)
    private String direccionEntrega;
    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false, length = 30)
    private String estado;
    @NotBlank(message = "El tipo de despacho no puede estar vacio")
    @Column(name = "tipo_despacho", nullable = false, length = 30)
    private String tipoDespacho;
    @Column(name = "fecha_despacho")
    private LocalDateTime fechaDespacho;
    @Column(name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;
}
