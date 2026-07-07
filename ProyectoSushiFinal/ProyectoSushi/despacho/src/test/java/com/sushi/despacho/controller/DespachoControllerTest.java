package com.sushi.despacho.controller;

import com.sushi.despacho.model.Despacho;
import com.sushi.despacho.service.DespachoService;
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

@WebMvcTest(DespachoController.class)
public class DespachoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DespachoService service;

    @Test
    void listarDespachos() throws Exception {
        when(service.listar()).thenReturn(List.of(new Despacho()));

        mockMvc.perform(get("/despachos/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarDespacho() throws Exception {
        String json = """
                {
                  "idPedido": 60,
                  "direccionEntrega": "Av. Test 123, Santiago",
                  "estado": "pendiente",
                  "tipoDespacho": "domicilio",
                  "fechaEntregaEstimada": "2026-06-26T19:30:00"
                }
                """;

        when(service.guardar(any(Despacho.class))).thenReturn(new Despacho());

        mockMvc.perform(post("/despachos/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
