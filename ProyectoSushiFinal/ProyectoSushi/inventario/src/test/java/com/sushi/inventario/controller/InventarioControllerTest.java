package com.sushi.inventario.controller;

import com.sushi.inventario.model.Inventario;
import com.sushi.inventario.service.InventarioService;
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

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService service;

    @Test
    void listarInventarios() throws Exception {
        when(service.listar()).thenReturn(List.of(new Inventario()));

        mockMvc.perform(get("/inventario/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarInventario() throws Exception {
        String json = """
                {
                  "nombreProducto": "Atun fresco",
                  "cantidad": 20,
                  "unidadMedida": "kg",
                  "stockMinimo": 5,
                  "categoria": "Pescados"
                }
                """;

        when(service.guardar(any(Inventario.class))).thenReturn(new Inventario());

        mockMvc.perform(post("/inventario/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
