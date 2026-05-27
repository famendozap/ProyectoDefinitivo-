package com.sushi.notificacion.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name = "notificacion") @Data @NoArgsConstructor @AllArgsConstructor
public class Notificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El id del usuario no puede ser nulo")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
    @NotBlank(message = "El tipo no puede estar vacio")
    @Column(nullable = false, length = 50)
    private String tipo;
    @NotBlank(message = "El mensaje no puede estar vacio")
    @Column(nullable = false, length = 500)
    private String mensaje;
    @NotBlank(message = "El canal no puede estar vacio")
    @Column(nullable = false, length = 30)
    private String canal;
    @NotBlank(message = "El estado no puede estar vacio")
    @Column(nullable = false, length = 20)
    private String estado;
    @NotNull(message = "La fecha no puede ser nula")
    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;
}
