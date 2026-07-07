package com.sushi.registrosucursal.controller;

import com.sushi.registrosucursal.model.Sucursal;
import com.sushi.registrosucursal.service.SucursalService;
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

@WebMvcTest(SucursalController.class)
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService service;

    @Test
    void listarSucursals() throws Exception {
        when(service.listar()).thenReturn(List.of(new Sucursal()));

        mockMvc.perform(get("/sucursales/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarSucursal() throws Exception {
        String json = """
                {
                  "nombre": "Sushi Norte",
                  "direccion": "Av. Test 456",
                  "ciudad": "Santiago",
                  "telefono": "+56911112222",
                  "estado": "activa"
                }
                """;

        when(service.guardar(any(Sucursal.class))).thenReturn(new Sucursal());

        mockMvc.perform(post("/sucursales/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
