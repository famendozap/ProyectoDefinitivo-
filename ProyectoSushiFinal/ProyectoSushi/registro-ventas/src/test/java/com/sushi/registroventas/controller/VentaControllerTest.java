package com.sushi.registroventas.controller;

import com.sushi.registroventas.model.Venta;
import com.sushi.registroventas.service.VentaService;
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

@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService service;

    @Test
    void listarVentas() throws Exception {
        when(service.listar()).thenReturn(List.of(new Venta()));

        mockMvc.perform(get("/ventas/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarVenta() throws Exception {
        String json = """
                {
                  "idPedido": 61,
                  "idSucursal": 1,
                  "total": 12990.00,
                  "fechaVenta": "2026-06-26T18:00:00",
                  "canalVenta": "web"
                }
                """;

        when(service.guardar(any(Venta.class))).thenReturn(new Venta());

        mockMvc.perform(post("/ventas/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
