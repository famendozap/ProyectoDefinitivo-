package com.sushi.resena.controller;

import com.sushi.resena.model.Resena;
import com.sushi.resena.service.ResenaService;
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

@WebMvcTest(ResenaController.class)
public class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResenaService service;

    @Test
    void listarResenas() throws Exception {
        when(service.listar()).thenReturn(List.of(new Resena()));

        mockMvc.perform(get("/resenas/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarResena() throws Exception {
        String json = """
                {
                  "idCliente": 12,
                  "idPedido": 61,
                  "calificacion": 4,
                  "comentario": "Buen servicio, rapido",
                  "fechaResena": "2026-06-26T18:00:00"
                }
                """;

        when(service.guardar(any(Resena.class))).thenReturn(new Resena());

        mockMvc.perform(post("/resenas/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
