package com.sushi.certificacion.controller;

import com.sushi.certificacion.model.Certificacion;
import com.sushi.certificacion.service.CertificacionService;
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

@WebMvcTest(CertificacionController.class)
public class CertificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificacionService service;

    @Test
    void listarCertificacions() throws Exception {
        when(service.listar()).thenReturn(List.of(new Certificacion()));

        mockMvc.perform(get("/certificaciones/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarCertificacion() throws Exception {
        String json = """
                {
                  "nombre": "Resolucion Sanitaria",
                  "tipo": "sanitaria",
                  "idSucursal": 1,
                  "fechaEmision": "2026-01-15",
                  "fechaVencimiento": "2027-01-15",
                  "estado": "vigente"
                }
                """;

        when(service.guardar(any(Certificacion.class))).thenReturn(new Certificacion());

        mockMvc.perform(post("/certificaciones/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
