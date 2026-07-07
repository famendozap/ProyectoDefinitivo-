package com.sushi.notificacion.model;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "notificacion") @Data @NoArgsConstructor @AllArgsConstructor
public class Notificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del usuario no puede ser nulo")
    @Column(name = "id_usuario", nullable = false)
    @Schema(example = "10")
    private Integer idUsuario;
    @NotBlank(message = "El tipo no puede estar vacio")
    @Size(max = 50, message = "tipo no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    @Schema(example = "pedido")
    private String tipo;
    @NotBlank(message = "El mensaje no puede estar vacio")
    @Size(max = 500, message = "mensaje no puede superar los 500 caracteres")
    @Column(nullable = false, length = 500)
    @Schema(example = "Tu pedido fue confirmado y esta en preparacion")
    private String mensaje;
    @NotBlank(message = "El canal no puede estar vacio")
    @Size(max = 30, message = "canal no puede superar los 30 caracteres")
    @Column(nullable = false, length = 30)
    @Schema(example = "email")
    private String canal;
    @NotBlank(message = "El estado no puede estar vacio")
    @Size(max = 20, message = "estado no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(example = "enviada")
    private String estado;
    @NotNull(message = "La fecha no puede ser nula")
    @Column(name = "fecha_envio", nullable = false)
    @Schema(example = "2026-06-25T18:05:00")
    private LocalDateTime fechaEnvio;
}
