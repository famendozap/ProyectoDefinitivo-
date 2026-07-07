package com.sushi.pago.controller;

import com.sushi.pago.model.Pago;
import com.sushi.pago.service.PagoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService service;

    @Test
    void listarPagos() throws Exception {
        List<Pago> pagos = List.of(
                new Pago(1, 55, new BigDecimal("15990"), "webpay", "completado", LocalDateTime.now())
        );

        when(service.listar()).thenReturn(pagos);

        mockMvc.perform(get("/pagos/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarPago() throws Exception {
        String pagoJson = """
                {
                  "idPedido": 60,
                  "monto": 8990,
                  "metodoPago": "efectivo",
                  "estado": "pendiente"
                }
                """;

        when(service.guardar(any(Pago.class))).thenReturn(new Pago());

        mockMvc.perform(post("/pagos/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(pagoJson))
                .andExpect(status().isCreated());
    }
}
