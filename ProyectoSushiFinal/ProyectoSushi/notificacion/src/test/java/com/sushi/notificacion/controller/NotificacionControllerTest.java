package com.sushi.notificacion.controller;

import com.sushi.notificacion.model.Notificacion;
import com.sushi.notificacion.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService service;

    @Test
    void listarNotificacions() throws Exception {
        when(service.listar()).thenReturn(List.of(new Notificacion()));

        mockMvc.perform(get("/notificaciones/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarNotificacion() throws Exception {
        String json = """
                {
                  "idUsuario": 10,
                  "tipo": "pedido",
                  "mensaje": "Tu pedido fue confirmado",
                  "canal": "email",
                  "estado": "enviada",
                  "fechaEnvio": "2026-06-26T18:00:00"
                }
                """;

        when(service.guardar(any(Notificacion.class))).thenReturn(new Notificacion());

        mockMvc.perform(post("/notificaciones/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
